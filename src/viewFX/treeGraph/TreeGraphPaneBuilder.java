package viewFX.treeGraph;

import viewFX.builders.PaneFXMLBuilder;

public class TreeGraphPaneBuilder extends PaneFXMLBuilder<TreeGraphPaneController> {

	@Override
	protected String getFxmlFileName() {
		return "TreeGraphPane.fxml";
	}

	@Override
	protected void afterBuild() {}
}
