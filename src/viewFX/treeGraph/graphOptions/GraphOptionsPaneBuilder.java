package viewFX.treeGraph.graphOptions;

import java.util.function.Consumer;

import lombok.Setter;
import model.Person;
import session.Session;
import viewFX.builders.PaneFXMLBuilder;

public class GraphOptionsPaneBuilder extends PaneFXMLBuilder<GraphOptionsPaneController> {

	@Setter
	private Session session;
	
	@Setter
	private Consumer<Person> drawAction;
	
	@Override
	public String getFxmlFileName() {
		return "GraphOptionsPane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setSession(session);
		controller.setDrawAction(drawAction);
	}

}
