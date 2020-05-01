package fxmlBuilders;

import fxmlControllers.PersonDetailsPaneController;

public class PersonDetailsPaneBuilder extends PaneFXMLBuilder<PersonDetailsPaneController> {

	@Override
	public String getFxmlFileName() {
		return "PersonDetailsPane.fxml";
	}

	@Override
	public void afterBuild() {
	}

}
