package fxmlBuilders.edit;

import fxmlControllers.edit.EditTextFieldController;
import javafx.scene.Node;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditTextFieldBuilder {

	@Getter
	private Node node;
	@Getter
	private EditTextFieldController controller;
	
	public void build(){
		MyFXMLLoader<EditTextFieldController> loader = new MyFXMLLoader<>();
		NodeAndController<EditTextFieldController> nac = loader.create("EditTextField.fxml");
		
		controller = nac.getController();
		node = nac.getNode();
	}
}
