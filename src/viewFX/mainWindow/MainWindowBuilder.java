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
import session.Session;
import settings.Settings;
import tools.Injection;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import treeGraphs.painter.PainterServiceType;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;
import treeGraphs.painter.service.PainterService;
import treeGraphs.painter.service.PainterServiceBuilder;
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
import viewFX.treeGraph.TreeGraphPaneBuilder;
import viewFX.treeGraph.TreeGraphPaneController;
import viewFX.treeGraph.graphOptions.GraphOptionsPaneBuilder;
import viewFX.treeGraph.personDetails.PersonDetailsPaneBuilder;
import viewFX.treeGraph.personDetails.PersonDetailsPaneController;
import viewFX.treePane.TreePaneBuilder;

public class MainWindowBuilder extends PaneFXMLBuilder<MainWindowController> {

	@Setter
	private Session session;

	@Setter
	private Function<File, Boolean> loadTree;
	@Setter
	private Consumer<Languages> changeLanguage;
	@Setter
	private Consumer<Boolean> fullScreenAction;
	@Setter
	private Supplier<Boolean> isFullScreen;
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

		controller.setCardPane(generateCardPane());
		controller.setGraphPane(generateTreeGrapfPane());
		controller.setChooseFilePane(generateFileChoosePane());
		controller.setTreePane(generateTreePane());
		controller.setEditPersonPane(generateEditPersonPane());
		
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
		builder.build();
		
		return new ViewPane(builder.getPane(), builder.getSettingSearchFocus());
	}
	
	private ViewPane generateFileChoosePane() {
		Settings settings = session.getSettings();
		FileChoosePaneBuilder builder = new FileChoosePaneBuilder();
		
		builder.setOpenFileAction(file -> {
			if(loadTree != null && loadTree.apply(file)) {
					controller.showView(Views.Tree);
					settings.getRecentFiles().add(file);
					settings.save();
			}
		});
		builder.setLastOpenFiles(settings.getRecentFiles().copyList());
		builder.build();
		
		return new ViewPane(builder.getPane());
	}
	
	private ViewPane generateTreeGrapfPane() {
		TreeGraphPaneBuilder builder = new TreeGraphPaneBuilder();
		builder.build();
		treeGraphController = builder.getController();
		
		PersonDetailsPaneBuilder personDetailsBuilder = new PersonDetailsPaneBuilder();
		personDetailsBuilder.setPersonClick(person -> {
			cardController.setPerson(person);
			controller.showView(Views.Card);
		});
		personDetailsBuilder.build();
		PersonDetailsPaneController personDetails = personDetailsBuilder.getController();
		
		GraphOptionsPaneBuilder graphOptionsBuilder = new GraphOptionsPaneBuilder();
		graphOptionsBuilder.setSession(session);
		graphOptionsBuilder.setDrawAction(this::showGraph);
		graphOptionsBuilder.build();
		
		treeGraphController.setOnPersonSingleClick(person -> personDetails.setPerson(person));
		treeGraphController.setOnPersonDoubleClick(clickedPerson -> {
			cardController.setPerson(clickedPerson);
			controller.showView(Views.Card);
		});
		treeGraphController.setOnParametersChange(graphOptionsBuilder.getController()::setState);
		
		ViewPane viewPane = new ViewPane(builder.getPane(), () -> personDetails.clearFields());
		viewPane.setLeftPane(personDetailsBuilder.getPane());
		viewPane.setTopPane(graphOptionsBuilder.getPane());
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
		
		showGraphView(new PainterServiceBuilder().setParameters(parameters).build());
		
		return true;
	}
	
	private void showGraph(TreeGraphType graph, Person person) {
		showGraph(graph, person, NameDisplayerType.onlyName, PainterServiceType.FX);
	}
	
	private void showGraph(TreeGraphType graph, Person person, NameDisplayerType nameDisplayer, PainterServiceType painterService) {

		PainterServiceBuilder builder = new PainterServiceBuilder();
		builder.setPerson(person);
		builder.setPainterServiceType(painterService);
		builder.setGraphType(graph);
		builder.setNameDisplayerType(nameDisplayer);
		
		showGraphView(builder.build());
	}


	private void showGraphView(PainterService painterService) {
		treeGraphController.setPainterService(painterService);
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



}
