package viewFX.treeGraph;

import viewFX.builders.PaneFXMLBuilder;

public class TreeGraphPaneBuilder extends PaneFXMLBuilder<TreeGraphPaneController> {

	@Override
	public String getFxmlFileName() {
		return "TreeGraphPane.fxml";
	}

	@Override
	public void afterBuild() {}
}
