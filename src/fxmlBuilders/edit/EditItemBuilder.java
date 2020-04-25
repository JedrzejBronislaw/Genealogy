package fxmlBuilders.edit;

import fxmlBuilders.PaneFXMLBuilder;
import fxmlControllers.edit.EditItemController;
import fxmlControllers.edit.EditItemController.EditField;
import lombok.Setter;

public class EditItemBuilder extends PaneFXMLBuilder<EditItemController> {
	
	@Setter
	private EditField editField;
	
	@Override
	public String getFxmlFileName() {
		return "EditItem.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setEditField(editField);
	}
}
