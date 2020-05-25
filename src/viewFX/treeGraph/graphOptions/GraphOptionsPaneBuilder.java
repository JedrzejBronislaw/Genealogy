package viewFX.treeGraph.graphOptions;

import java.util.function.Consumer;

import lombok.Setter;
import session.Session;
import treeGraphs.TreeGraphParameters;
import viewFX.builders.PaneFXMLBuilder;

public class GraphOptionsPaneBuilder extends PaneFXMLBuilder<GraphOptionsPaneController> {

	@Setter
	private Session session;
	
	@Setter
	private Consumer<TreeGraphParameters> drawAction;
	
	@Override
	protected String getFxmlFileName() {
		return "GraphOptionsPane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setSession(session);
		controller.setDrawAction(drawAction);
	}

}
