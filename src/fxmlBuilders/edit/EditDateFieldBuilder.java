package fxmlBuilders.edit;

import fxmlControllers.edit.EditDateFieldController;
import javafx.scene.Node;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditDateFieldBuilder {

	@Getter
	private Node node;
	@Getter
	private EditDateFieldController controller;
	
	public void build(){
		MyFXMLLoader<EditDateFieldController> loader = new MyFXMLLoader<>();
		NodeAndController<EditDateFieldController> nac = loader.create("EditDateField.fxml");
		
		controller = nac.getController();
		node = nac.getNode();
	}
}
