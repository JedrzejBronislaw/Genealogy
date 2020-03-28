package fxmlBuilders;

import java.io.File;
import java.util.Arrays;
import java.util.function.Function;

import fxmlBuilders.session.Session;
import fxmlControllers.MainWindowController;
import fxmlControllers.MainWindowController.Views;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import nameDisplaying.SimpleNameDisplaying;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;
import treeGraphs.DrawingDescendantTreeGraph;

public class MainWindowBuilder {

	@Getter
	private Pane pane;
	private MainWindowController controller;

	@Setter
	private Session session;

	@Setter
	private Function<File, Boolean> loadTree;
	
	
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
		builder.build();
		
		return builder.getPane();
	}
	
	private Pane generateFileChoosePane() {
		FileChoosePaneBuilder builder = new FileChoosePaneBuilder();
//		builder.setOpenFileAction(file -> System.out.println("Open tree file (" + file.getName() + ")"));
		builder.setOpenFileAction(file -> {
			if(loadTree != null)
				if (loadTree.apply(file)) {
					controller.showView(Views.Tree);
					System.out.println("git");
				} else
				System.out.println("error");
		});
		builder.setLastOpenFiles(Arrays.asList("D:\\trees\\tree1.pgl","D:\\newtrees\\tree1.pgl","D:\\trees\\Nowak.pgl","D:\\trees\\Smith.pgl"));
		builder.build();
		
		return builder.getPane();
	}
	
	private Pane generateTreeGrapfPane() {
		TreeGraphPaneBuilder builder = new TreeGraphPaneBuilder();
		builder.build();
		
		DrawingDescendantTreeGraph graph = new DrawingDescendantTreeGraph();
		graph.setWyswietlacz(new SimpleNameDisplaying());
		
		
		builder.getController().setGraph(graph);
//		builder.getController().setPerson(session.getTree().getOsoba("100"));
		
		return builder.getPane();
	}
	
	private Pane generateCardPane() {
		CardPaneBuilder builder = new CardPaneBuilder();
		builder.build();
		
//		builder.getController().setPerson(tree.getOsoba("34"));
//		builder.getController().setPerson(session.getTree().getOsoba("9"));
		
		return builder.getPane();
	}
}
