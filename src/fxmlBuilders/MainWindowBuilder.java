package fxmlBuilders;

import java.util.Arrays;

import fxmlBuilders.session.Session;
import fxmlControllers.MainWindowController;
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

	@Setter
	private Session session;
	
	
	public void build() {
		MyFXMLLoader<MainWindowController> loader = new MyFXMLLoader<>();
		NodeAndController<MainWindowController> nac = loader.create("MainWindow.fxml");
		
		pane = (Pane) nac.getNode();
		
		nac.getController().addNode(generateCardPane());
		nac.getController().addNode(generateTreeGrapfPane());
		nac.getController().addNode(generateFileChoosePane());
		nac.getController().addNode(generateTreePane());
	}


	private Pane generateTreePane() {
		TreePaneBuilder builder = new TreePaneBuilder();
		builder.setSession(session);
		builder.build();
		
		return builder.getPane();
	}
	
	private Pane generateFileChoosePane() {
		FileChoosePaneBuilder builder = new FileChoosePaneBuilder();
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
		builder.getController().setPerson(session.getTree().getOsoba("100"));
		
		return builder.getPane();
	}
	
	private Pane generateCardPane() {
		CardPaneBuilder builder = new CardPaneBuilder();
		builder.build();
		
//		builder.getController().setPerson(tree.getOsoba("34"));
		builder.getController().setPerson(session.getTree().getOsoba("9"));
		
		return builder.getPane();
	}
}
