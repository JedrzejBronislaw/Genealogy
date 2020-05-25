package viewFX.editPerson.fields.enumField;

import lombok.Setter;
import viewFX.builders.RegionFXMLBuilder;

public class EditEnumFieldBuilder extends RegionFXMLBuilder<EditEnumFieldController> {
	
	@Setter
	private String[] options;

	@Override
	protected String getFxmlFileName() {
		return "EditEnumField.fxml";
	}

	@Override
	protected void afterBuild() {
		for (String name : options) controller.addOption(name);
	}
}
