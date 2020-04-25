package fxmlBuilders.edit;

import fxmlBuilders.FXMLBuilder;
import fxmlControllers.edit.EditDateFieldController;

public class EditDateFieldBuilder extends FXMLBuilder<EditDateFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditDateField.fxml";
	}

	@Override
	public void afterBuild() {}
}
