package viewFX.editPerson.fields.person;

import viewFX.builders.RegionFXMLBuilder;

public class EditPersonFieldBuilder extends RegionFXMLBuilder<EditPersonFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditPersonField.fxml";
	}

	@Override
	public void afterBuild() {}
}
