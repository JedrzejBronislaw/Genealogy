package fxmlBuilders;

import fxmlControllers.EditItemController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditItemBuilder {

	@Getter
	private Pane pane;
	@Getter
	private EditItemController controller;
	
	public void build(){
		MyFXMLLoader<EditItemController> loader = new MyFXMLLoader<>();
		NodeAndController<EditItemController> nac = loader.create("EditItem.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();	
	}
}
