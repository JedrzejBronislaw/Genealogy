package viewFX.openError;

import viewFX.builders.PaneFXMLBuilder;

public class OpenErrorPaneBuilder extends PaneFXMLBuilder<OpenErrorPaneController> {

	@Override
	protected String getFxmlFileName() {
		return "OpenErrorPane.fxml";
	}

	@Override
	protected void afterBuild() {
	}
}
