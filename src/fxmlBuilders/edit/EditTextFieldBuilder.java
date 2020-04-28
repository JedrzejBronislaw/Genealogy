package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditDateFieldController;

public class EditTextFieldBuilder extends RegionFXMLBuilder<EditDateFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditTextField.fxml";
	}

	@Override
	public void afterBuild() {}
}
