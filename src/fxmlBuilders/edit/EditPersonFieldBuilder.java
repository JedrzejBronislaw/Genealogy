package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditPersonFieldController;

public class EditPersonFieldBuilder extends RegionFXMLBuilder<EditPersonFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditPersonField.fxml";
	}

	@Override
	public void afterBuild() {}
}
