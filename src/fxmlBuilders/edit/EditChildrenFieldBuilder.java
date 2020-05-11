package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditChildrenFieldController;

public class EditChildrenFieldBuilder extends RegionFXMLBuilder<EditChildrenFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditChildrenField.fxml";
	}

	@Override
	public void afterBuild() {}
}
