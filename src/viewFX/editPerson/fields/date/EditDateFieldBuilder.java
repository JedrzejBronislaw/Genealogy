package viewFX.editPerson.fields.date;

import viewFX.builders.RegionFXMLBuilder;

public class EditDateFieldBuilder extends RegionFXMLBuilder<EditDateFieldController> {

	@Override
	protected String getFxmlFileName() {
		return "EditDateField.fxml";
	}

	@Override
	protected void afterBuild() {}
}
