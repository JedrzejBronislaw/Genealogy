package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditDateFieldController;

public class EditDateFieldBuilder extends RegionFXMLBuilder<EditDateFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditDateField.fxml";
	}

	@Override
	public void afterBuild() {}
}
