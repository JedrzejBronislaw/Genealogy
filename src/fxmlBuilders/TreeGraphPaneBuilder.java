package fxmlBuilders;

import fxmlControllers.TreeGraphPaneController;

public class TreeGraphPaneBuilder extends PaneFXMLBuilder<TreeGraphPaneController> {

	@Override
	public String getFxmlFileName() {
		return "TreeGraphPane.fxml";
	}

	@Override
	public void afterBuild() {}
}
