package viewFX.editPerson.fields;

import lombok.Setter;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.editPerson.fields.EditItemController.EditField;

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
