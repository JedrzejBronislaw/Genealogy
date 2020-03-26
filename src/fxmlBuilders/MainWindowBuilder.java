package fxmlBuilders;

import java.awt.Dimension;
import java.util.Arrays;

import fxmlControllers.MainWindowController;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Tree;
import nameDisplaying.SimpleNameDisplaying;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;
import treeGraphs.DrawingDescendantTreeGraph;
import windows.SearchScreen;

public class MainWindowBuilder {

	@Getter
	private Pane pane;

	@Setter
	private Tree tree;
	
	
	public void build() {
		MyFXMLLoader<MainWindowController> loader = new MyFXMLLoader<>();
		NodeAndController<MainWindowController> nac = loader.create("MainWindow.fxml");
		
		pane = (Pane) nac.getNode();
		
		
		SearchScreen search = new SearchScreen();
		search.setDrzewo(tree);
		search.setPreferredSize(new Dimension(330, 200));
		SwingNode fxmlSearch = new SwingNode();
		fxmlSearch.setContent(search);
		
		nac.getController().addNode(generateCardPane());
		nac.getController().addNode(generateTreeGrapfPane());
		nac.getController().addNode(generateFileChoosePane());
		nac.getController().addNode(generateTreeDetailsPane());
		nac.getController().addNode(generateCommonNamePane());
		nac.getController().addNode(fxmlSearch);
		
	}


	private Pane generateTreeDetailsPane() {
		TreeDetailsPaneBuilder builder = new TreeDetailsPaneBuilder();
		builder.setTree(tree);
		builder.build();
		
		return builder.getPane();
	}


	private Pane generateCommonNamePane() {
		CommonSurnamesPaneBuilder builder = new CommonSurnamesPaneBuilder();
		builder.setSurnames(Arrays.asList(tree.getGlowneNazwiska()));
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
		builder.getController().setPerson(tree.getOsoba("100"));
		
		return builder.getPane();
	}
	
	private Pane generateCardPane() {
		CardPaneBuilder builder = new CardPaneBuilder();
		builder.build();
		
//		builder.getController().setPerson(tree.getOsoba("34"));
		builder.getController().setPerson(tree.getOsoba("9"));
		
		return builder.getPane();
	}
}
