package fxmlBuilders.edit;

import fxmlBuilders.FXMLBuilder;
import fxmlControllers.edit.EditDateFieldController;

public class EditTextFieldBuilder extends FXMLBuilder<EditDateFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditTextField.fxml";
	}

	@Override
	public void afterBuild() {}
}
