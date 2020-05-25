package viewFX.editPerson.fields.person;

import viewFX.builders.RegionFXMLBuilder;

public class EditPersonFieldBuilder extends RegionFXMLBuilder<EditPersonFieldController> {

	@Override
	protected String getFxmlFileName() {
		return "EditPersonField.fxml";
	}

	@Override
	protected void afterBuild() {}
}
