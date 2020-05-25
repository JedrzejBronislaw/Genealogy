package viewFX.treeGraph.personDetails;

import java.util.function.Consumer;

import lombok.Setter;
import model.Person;
import viewFX.builders.PaneFXMLBuilder;

public class PersonDetailsPaneBuilder extends PaneFXMLBuilder<PersonDetailsPaneController> {

	@Setter
	private Consumer<Person> personClick;
	
	@Override
	protected String getFxmlFileName() {
		return "PersonDetailsPane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setPersonClick(personClick);
	}

}
