package fxmlBuilders.edit;

import fxmlControllers.edit.EditItemController;
import fxmlControllers.edit.EditItemController.EditField;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditItemBuilder {

	public enum Type {Text, Date};
	
	@Getter
	private Pane pane;
	@Getter
	private EditItemController controller;
	
	@Setter
	private Type fieldType = null;
	
	public void build(){
		MyFXMLLoader<EditItemController> loader = new MyFXMLLoader<>();
		NodeAndController<EditItemController> nac = loader.create("EditItem.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
		if (fieldType != null)
			controller.setEditField(createEditField());
	}

	private EditField createEditField() {
		Node node = null;

		if (fieldType == Type.Text) {
			EditTextFieldBuilder builder = new EditTextFieldBuilder();
			builder.build();
			node = builder.getNode();
			return new EditField(node, builder.getController());
		} else
		if (fieldType == Type.Date) {
			EditDateFieldBuilder builder = new EditDateFieldBuilder();
			builder.build();
			node = builder.getNode();
			return new EditField(node, builder.getController());
		}
		
		return null;
	}
}
