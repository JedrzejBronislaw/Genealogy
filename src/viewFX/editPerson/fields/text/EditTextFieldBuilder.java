package viewFX.editPerson.fields.text;

import viewFX.builders.RegionFXMLBuilder;

public class EditTextFieldBuilder extends RegionFXMLBuilder<EditTextFieldController> {

	@Override
	protected String getFxmlFileName() {
		return "EditTextField.fxml";
	}

	@Override
	protected void afterBuild() {}
}
