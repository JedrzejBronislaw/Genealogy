package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditTextFieldController;

public class EditTextFieldBuilder extends RegionFXMLBuilder<EditTextFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditTextField.fxml";
	}

	@Override
	public void afterBuild() {}
}
