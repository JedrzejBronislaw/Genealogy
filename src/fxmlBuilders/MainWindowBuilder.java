package fxmlBuilders;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import fxmlControllers.CardPaneController;
import fxmlControllers.MainWindowController;
import fxmlControllers.MainWindowController.Views;
import fxmlControllers.TreeGraphPaneController;
import javafx.scene.layout.Pane;
import lang.Languages;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import nameDisplaying.SimpleNameDisplaying;
import session.Session;
import settings.Settings;
import tools.Injection;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;
import treeGraphs.DrawingDescendantTreeGraph;
import treeGraphs.StdAncestorsTreeGraph;
import treeGraphs.StdDescendantsTreeGraph;
import treeGraphs.TreeGraph;

public class MainWindowBuilder {

	@Getter
	private Pane pane;
	private MainWindowController controller;

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
	
	public void build() {
		MyFXMLLoader<MainWindowController> loader = new MyFXMLLoader<>();
		NodeAndController<MainWindowController> nac = loader.create("MainWindow.fxml");
		
		pane = (Pane) nac.getNode();
		controller = nac.getController();

		controller.setLanguagePane(generateLanguagePane());
		controller.setFullScreenPane(generateFullScreenPane());

		controller.setCardPane(generateCardPane());
		controller.setGraphPane(generateTreeGrapfPane());
		controller.setChooseFilePane(generateFileChoosePane());
		controller.setTreePane(generateTreePane());
		
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

	private Pane generateTreePane() {
		TreePaneBuilder builder = new TreePaneBuilder();
		builder.setSession(session);
		builder.setSelectPerson(person -> {
			cardController.setPerson(person);
			controller.showView(Views.Card);
		});
		builder.setCloseTree(() -> {
			Injection.run(closeTree);
			controller.showView(Views.ChooseFile);
		});
		builder.build();
		
		return builder.getPane();
	}
	
	private Pane generateFileChoosePane() {
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
		
		return builder.getPane();
	}
	
	private Pane generateTreeGrapfPane() {
		TreeGraphPaneBuilder builder = new TreeGraphPaneBuilder();
		builder.build();
		treeGraphController = builder.getController();
		
		return builder.getPane();
	}
	
	private Pane generateCardPane() {
		CardPaneBuilder builder = new CardPaneBuilder();
		builder.setShowAncestorsTree(person -> {
			TreeGraph graph = new StdAncestorsTreeGraph();
			showGraph(graph, person);
		});
		builder.setShowDescendantsTree(person -> {
			TreeGraph graph = new StdDescendantsTreeGraph();
			showGraph(graph, person);
		});
		builder.setShowDrawingTree(person -> {
			TreeGraph graph = new DrawingDescendantTreeGraph();
			showGraph(graph, person);
		});
		builder.setGraphClickAction(selectedPerson -> {
			cardController.setPerson(selectedPerson);
			controller.showView(Views.Card);
		});
		builder.build();
		
		cardController = builder.getController();
		
		return builder.getPane();
	}
	
	private void showGraph(TreeGraph graph, Person person) {
		graph.setNameDisplay(new SimpleNameDisplaying());
		graph.setPersonClickAction(selectedPerson -> {
			cardController.setPerson(selectedPerson);
			controller.showView(Views.Card);
		});
		treeGraphController.setGraph(graph);
		treeGraphController.setPerson(person);
		controller.showView(Views.Graph);
	}

}
