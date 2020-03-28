package fxmlBuilders;

import java.io.File;
import java.util.Arrays;
import java.util.function.Function;

import fxmlBuilders.session.Session;
import fxmlControllers.CardPaneController;
import fxmlControllers.MainWindowController;
import fxmlControllers.MainWindowController.Views;
import fxmlControllers.TreeGraphPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import nameDisplaying.SimpleNameDisplaying;
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
	
	private CardPaneController cardController;
	private TreeGraphPaneController treeGraphController;
	
	public void build() {
		MyFXMLLoader<MainWindowController> loader = new MyFXMLLoader<>();
		NodeAndController<MainWindowController> nac = loader.create("MainWindow.fxml");
		
		pane = (Pane) nac.getNode();
		controller = nac.getController();
				
		controller.setCardPane(generateCardPane());
		controller.setGraphPane(generateTreeGrapfPane());
		controller.setChooseFilePane(generateFileChoosePane());
		controller.setTreePane(generateTreePane());
		
		controller.showView(Views.ChooseFile);
	}


	private Pane generateTreePane() {
		TreePaneBuilder builder = new TreePaneBuilder();
		builder.setSession(session);
		builder.setSelectPerson(person -> {
			cardController.setPerson(person);
			controller.showView(Views.Card);
		});
		builder.build();
		
		return builder.getPane();
	}
	
	private Pane generateFileChoosePane() {
		FileChoosePaneBuilder builder = new FileChoosePaneBuilder();
		builder.setOpenFileAction(file -> {
			if(loadTree != null && loadTree.apply(file))
					controller.showView(Views.Tree);
		});
		builder.setLastOpenFiles(Arrays.asList("D:\\trees\\tree1.pgl","D:\\newtrees\\tree1.pgl","D:\\trees\\Nowak.pgl","D:\\trees\\Smith.pgl"));
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
		builder.build();
		
		cardController = builder.getController();
		
		return builder.getPane();
	}
	
	private void showGraph(TreeGraph graph, Person person) {
		graph.setWyswietlacz(new SimpleNameDisplaying());
		treeGraphController.setGraph(graph);
		treeGraphController.setPerson(person);
		controller.showView(Views.Graph);
	}
}
