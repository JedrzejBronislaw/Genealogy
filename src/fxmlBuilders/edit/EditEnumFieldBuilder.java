package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditEnumFieldController;
import lombok.Setter;

public class EditEnumFieldBuilder extends RegionFXMLBuilder<EditEnumFieldController> {
	
	@Setter
	private String[] options;

	@Override
	public String getFxmlFileName() {
		return "EditEnumField.fxml";
	}

	@Override
	public void afterBuild() {
		for (String name : options) controller.addOption(name);
	}
}
