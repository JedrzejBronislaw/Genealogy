package fxmlBuilders;

import java.awt.Dimension;
import java.util.Arrays;

import fxmlBuilders.session.Session;
import fxmlControllers.TreePaneController;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;
import windows.SearchScreen;

public class TreePaneBuilder {

	@Getter
	private Pane pane;
	private TreePaneController controller;
	
	@Setter
	private Session session;
	
	public void build() {
		MyFXMLLoader<TreePaneController> loader = new MyFXMLLoader<>();
		NodeAndController<TreePaneController> nac = loader.create("TreePane.fxml");
		
		pane = (Pane) nac.getNode();
		controller = nac.getController();

		controller.addNode(generateTreeDetailsPane());
		controller.addNode(generateCommonNamePane());
		controller.addNode(generateSearchPane());
	}
	
	private Pane generateTreeDetailsPane() {
		TreeDetailsPaneBuilder builder = new TreeDetailsPaneBuilder();
		builder.setTree(session.getTree());
		builder.build();
		
		return builder.getPane();
	}


	private Pane generateCommonNamePane() {
		CommonSurnamesPaneBuilder builder = new CommonSurnamesPaneBuilder();
		builder.setSurnames(Arrays.asList(session.getTree().getGlowneNazwiska()));
		builder.build();
		
		return builder.getPane();
	}
	
	private Node generateSearchPane() {
		SearchScreen search = new SearchScreen();
		search.setDrzewo(session.getTree());
		search.setPreferredSize(new Dimension(330, 200));
		SwingNode fxmlSearch = new SwingNode();
		fxmlSearch.setContent(search);
		
		return fxmlSearch;
	}
}
