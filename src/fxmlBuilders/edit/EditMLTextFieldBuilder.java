package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditMLTextFieldController;

public class EditMLTextFieldBuilder extends RegionFXMLBuilder<EditMLTextFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditMLTextField.fxml";
	}

	@Override
	public void afterBuild() {}
}
