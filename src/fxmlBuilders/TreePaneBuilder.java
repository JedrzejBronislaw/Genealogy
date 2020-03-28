package fxmlBuilders;

import java.awt.Dimension;

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
		builder.setSession(session);
		builder.build();
		
		return builder.getPane();
	}


	private Pane generateCommonNamePane() {
		CommonSurnamesPaneBuilder builder = new CommonSurnamesPaneBuilder();
		builder.setSession(session);
		builder.build();
		
		return builder.getPane();
	}
	
	private Node generateSearchPane() {
		SearchScreen search = new SearchScreen();
		search.setPreferredSize(new Dimension(330, 200));
		SwingNode fxmlSearch = new SwingNode();
		fxmlSearch.setContent(search);
		
		session.addNewTreeListener(tree -> search.setDrzewo(tree));
		
		return fxmlSearch;
	}
}
