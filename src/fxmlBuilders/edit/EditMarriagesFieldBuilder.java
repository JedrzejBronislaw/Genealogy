package fxmlBuilders.edit;

import fxmlBuilders.RegionFXMLBuilder;
import fxmlControllers.edit.EditMarriagesFieldController;

public class EditMarriagesFieldBuilder extends RegionFXMLBuilder<EditMarriagesFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditMarriagesField.fxml";
	}

	@Override
	public void afterBuild() {}
}
