package fxmlBuilders.edit;

import fxmlControllers.edit.EditItemController;
import fxmlControllers.edit.EditItemController.EditField;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditItemBuilder {
	
	@Getter
	private Pane pane;
	@Getter
	private EditItemController controller;
	
	@Setter
	private EditField editField;
	
	public void build(){
		MyFXMLLoader<EditItemController> loader = new MyFXMLLoader<>();
		NodeAndController<EditItemController> nac = loader.create("EditItem.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
		controller.setEditField(editField);
	}
}
