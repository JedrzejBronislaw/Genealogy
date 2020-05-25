package viewFX.editPerson.fields;

import lombok.Setter;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditItemBuilder extends PaneFXMLBuilder<EditItemController> {
	
	@Setter
	private EditField editField;
	
	@Override
	protected String getFxmlFileName() {
		return "EditItem.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setEditField(editField);
	}
}
