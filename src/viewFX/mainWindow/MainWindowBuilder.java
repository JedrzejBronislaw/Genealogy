package viewFX.mainWindow;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.scene.layout.Pane;
import lang.Languages;
import lombok.Setter;
import model.Person;
import model.Tree;
import model.TreeEditor;
import model.pgl.comparator.PGLDiffReport;
import session.Session;
import settings.Settings;
import tools.Injection;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import treeGraphs.painter.PainterServiceType;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.card.CardPaneBuilder;
import viewFX.card.CardPaneController;
import viewFX.editPerson.EditPersonPaneBuilder;
import viewFX.editPerson.EditPersonPaneController;
import viewFX.fileChoose.FileChoosePaneBuilder;
import viewFX.mainWindow.MainWindowController.ViewPane;
import viewFX.mainWindow.MainWindowController.Views;
import viewFX.mainWindow.fullScreen.FullScreenPaneBuilder;
import viewFX.mainWindow.language.LanguagePaneBuilder;
import viewFX.openError.OpenErrorPaneBuilder;
import viewFX.treeGraph.TreeGraphPaneBuilder;
import viewFX.treeGraph.TreeGraphPaneController;
import viewFX.treePane.TreePaneBuilder;

public class MainWindowBuilder extends PaneFXMLBuilder<MainWindowController> {

	@Setter
	private Session session;

	@Setter
	private Function<File, PGLDiffReport> loadTree;
	@Setter
	private Consumer<Languages> changeLanguage;
	@Setter
	private Consumer<Boolean> fullScreenAction;
	@Setter
	private Supplier<Boolean> isFullScreen;

	@Setter
	private Runnable saveTree;
	@Setter
	private Consumer<File> saveTreeAs;
	@Setter
	private Runnable closeTree;
	
	private CardPaneController cardController;
	private TreeGraphPaneController treeGraphController;
	private EditPersonPaneController editPersonController;

	@Override
	protected String getFxmlFileName() {
		return "MainWindow.fxml";
	}


	@Override
	protected void afterBuild() {
		controller.setLanguagePane(generateLanguagePane());
		controller.setFullScreenPane(generateFullScreenPane());

		controller.addPane(Views.Card, generateCardPane());
		controller.addPane(Views.Graph, generateTreeGraphPane());
		controller.addPane(Views.ChooseFile, generateFileChoosePane());
		controller.addPane(Views.Tree, generateTreePane());
		controller.addPane(Views.EditPerson, generateEditPersonPane());
		controller.addPane(Views.OpenError, generateOpenErrorPane());

		controller.showView(Views.ChooseFile);
	}


	private Pane generateLanguagePane() {
		LanguagePaneBuilder builder = new LanguagePaneBuilder();
		builder.setChangeLanguage(changeLanguage);
		builder.build();
		
		return builder.getPane();
	}

	private Pane generateFullScreenPane() {
		FullScreenPaneBuilder builder = new FullScreenPaneBuilder();
		builder.setFullScreenAction(fullScreenAction);
		builder.setIsFullScreen(isFullScreen);
		builder.build();
		
		return builder.getPane();
	}

	private ViewPane generateTreePane() {
		TreePaneBuilder builder = new TreePaneBuilder();
		builder.setSession(session);
		builder.setSelectPerson(person -> {
			cardController.setPerson(person);
			controller.showView(Views.Card);
		});
		builder.setCreateNewPerson(() -> {
			editPersonController.setPerson(new Person());
			editPersonController.setAddToTreeWhenSaving(true);
			controller.showView(Views.EditPerson);
		});
		builder.setCloseTree(() -> {
			Injection.run(closeTree);
			controller.showView(Views.ChooseFile);
		});
		builder.setSaveTree(() -> {
			Injection.run(saveTree);
		});
		builder.setSaveTreeAs(file -> {
			Injection.run(saveTreeAs, file);
		});
		builder.build();
		
		return new ViewPane(builder.getPane(), builder.getSettingSearchFocus());
	}
	
	private ViewPane generateFileChoosePane() {
		Settings settings = session.getSettings();
		FileChoosePaneBuilder builder = new FileChoosePaneBuilder();

		builder.setNewTreeEvent(() -> {
			session.setTree(new Tree());
			controller.showView(Views.Tree);
		});
		builder.setOpenFileAction(file -> {
			if(loadTree != null && loadTree.apply(file).isPermissionToOpen()) {
					controller.showView(Views.Tree);
					settings.getRecentFiles().add(file);
					settings.save();
			} else
				controller.showView(Views.OpenError);
		});
		builder.setLastOpenFiles(settings.getRecentFiles().copyList());
		builder.build();
		
		return new ViewPane(builder.getPane());
	}
	
	private ViewPane generateTreeGraphPane() {
		TreeGraphPaneBuilder builder = new TreeGraphPaneBuilder();
		builder.setSession(session);
		builder.setPersonClick(person -> {
			cardController.setPerson(person);
			controller.showView(Views.Card);
		});
		builder.setDrawGraphAction(this::showGraph);
		builder.build();

		treeGraphController = builder.getController();
		
		ViewPane viewPane = new ViewPane(builder.getPane(), () -> builder.clearPersonDetailsFields());
		viewPane.setLeftPane(builder.getPersonDetailsPane());
		viewPane.setTopPane(builder.getGraphOptionsPane());
		return viewPane;
	}
	
	private ViewPane generateCardPane() {
		CardPaneBuilder builder = new CardPaneBuilder();
		builder.setShowAncestorsTree(person -> {
			showGraph(TreeGraphType.ancestors, person);
		});
		builder.setShowDescendantsTree(person -> {
			showGraph(TreeGraphType.descendants, person);
		});
		builder.setShowDrawingTree(person -> {
			showGraph(TreeGraphType.drawnig, person);
		});
		builder.setGraphClickAction(selectedPerson -> {
			cardController.setPerson(selectedPerson);
			controller.showView(Views.Card);
		});

		builder.setEditAction(person -> {
			editPersonController.setPerson(person);
			controller.showView(Views.EditPerson);
		});
		
		builder.build();
		
		cardController = builder.getController();
		
		return new ViewPane(builder.getPane());
	}
	
	private boolean showGraph(TreeGraphParameters parameters) {
		if ( parameters == null ||
			!parameters.isReady()) return false;
		
		showGraphView(parameters);
		
		return true;
	}
	
	private void showGraph(TreeGraphType graph, Person person) {
		TreeGraphParameters parameters = TreeGraphParameters.builder()
				.person(person)
				.graphType(graph)
				.painterType(PainterServiceType.FX)
				.nameDisplayerType(NameDisplayerType.onlyName)
				.markers(true)
				.build();
		
		showGraphView(parameters);
	}

	private void showGraphView(TreeGraphParameters parameters) {
		treeGraphController.setPainterService(parameters);
		treeGraphController.refreshGraph();
		
		controller.showView(Views.Graph);
	}
	
	private ViewPane generateEditPersonPane() {
		EditPersonPaneBuilder builder = new EditPersonPaneBuilder();
		builder.setChangeEvent(person -> {
			session.reportPersonEdit(person);
		});
		builder.setClosePane(person -> {
			cardController.setPerson(person);
			controller.showView(Views.Card);
		});
		builder.setAddToTree(person -> {
			Tree tree = session.getTree();
			if (tree == null) return;
			
			TreeEditor editor = new TreeEditor(tree);
			editor.addIfIsOutside(person);
		});
		builder.setSession(session);
		builder.build();
		editPersonController = builder.getController();
		
		return new ViewPane(builder.getPane());
	}
	
	private ViewPane generateOpenErrorPane() {
		OpenErrorPaneBuilder builder = new OpenErrorPaneBuilder();
		builder.build();
		
		return new ViewPane(builder.getPane());
	}
}
