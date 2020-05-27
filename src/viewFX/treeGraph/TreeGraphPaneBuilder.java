package viewFX.treeGraph;

import java.util.function.Consumer;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import session.Session;
import treeGraphs.TreeGraphParameters;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.treeGraph.graphOptions.GraphOptionsPaneBuilder;
import viewFX.treeGraph.graphOptions.GraphOptionsPaneController;
import viewFX.treeGraph.personDetails.PersonDetailsPaneBuilder;
import viewFX.treeGraph.personDetails.PersonDetailsPaneController;

public class TreeGraphPaneBuilder extends PaneFXMLBuilder<TreeGraphPaneController> {

	@Setter
	private Session session;
	
	@Setter
	private Consumer<TreeGraphParameters> drawGraphAction;
	@Setter
	private Consumer<Person> personClick;
	
	@Getter
	private Pane personDetailsPane;
	@Getter
	private Pane graphOptionsPane;
	private PersonDetailsPaneController personDetailsController;
	private GraphOptionsPaneController graphOptionsController;
	
	@Override
	protected String getFxmlFileName() {
		return "TreeGraphPane.fxml";
	}

	@Override
	protected void afterBuild() {
		buildPersonDetails();
		buildGraphOptions();
		
		controller.setOnPersonSingleClick(person -> personDetailsController.setPerson(person));
		controller.setOnPersonDoubleClick(personClick);
		controller.setOnParametersChange(graphOptionsController::setState);
	}

	private void buildPersonDetails() {
		PersonDetailsPaneBuilder personDetailsBuilder = new PersonDetailsPaneBuilder();
		personDetailsBuilder.setPersonClick(personClick);
		personDetailsBuilder.build();
		
		personDetailsController = personDetailsBuilder.getController();
		personDetailsPane = personDetailsBuilder.getPane();
	}

	private void buildGraphOptions() {
		GraphOptionsPaneBuilder graphOptionsBuilder = new GraphOptionsPaneBuilder();
		graphOptionsBuilder.setSession(session);
		graphOptionsBuilder.setDrawAction(drawGraphAction);
		graphOptionsBuilder.build();
		
		graphOptionsController = graphOptionsBuilder.getController();
		graphOptionsPane = graphOptionsBuilder.getPane();
	}
	
	public void clearPersonDetailsFields() {
		if (personDetailsController != null)
			personDetailsController.clearFields();
	}
}
