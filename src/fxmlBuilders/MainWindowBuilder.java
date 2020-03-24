package fxmlBuilders;

import java.awt.Dimension;
import java.util.Arrays;

import fxmlControllers.MainWindowController;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Tree;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;
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
		builder.setSurnames(Arrays.asList(tree.getGlowneNazwiska()));//"Nowak", "Kowalski", "Smith", "Malinowski"));
		builder.build();
		
		return builder.getPane();
	}
}
