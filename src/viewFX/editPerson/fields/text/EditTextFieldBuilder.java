package viewFX.editPerson.fields.text;

import viewFX.builders.RegionFXMLBuilder;

public class EditTextFieldBuilder extends RegionFXMLBuilder<EditTextFieldController> {

	@Override
	public String getFxmlFileName() {
		return "EditTextField.fxml";
	}

	@Override
	public void afterBuild() {}
}
