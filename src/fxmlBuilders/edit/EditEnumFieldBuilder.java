package fxmlBuilders.edit;

import fxmlControllers.edit.EditEnumFieldController;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditEnumFieldBuilder {

	@Getter
	private Node node;
	@Getter
	private EditEnumFieldController controller;
	
	@Setter
	private String[] options;
	
	public void build(){
		MyFXMLLoader<EditEnumFieldController> loader = new MyFXMLLoader<>();
		NodeAndController<EditEnumFieldController> nac = loader.create("EditEnumField.fxml");
		
		controller = nac.getController();
		node = nac.getNode();
		
		for (String name : options) controller.addOption(name);
	}
}
