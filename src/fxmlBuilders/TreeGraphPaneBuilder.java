package fxmlBuilders;

import fxmlControllers.TreeGraphPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class TreeGraphPaneBuilder {

	@Getter
	private Pane pane;
	@Getter
	private TreeGraphPaneController controller;
	
	public void build() {
		MyFXMLLoader<TreeGraphPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<TreeGraphPaneController> nac = loader.create("TreeGraphPane.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
		
	}
}
