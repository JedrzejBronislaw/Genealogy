package viewFX.treeGraph.personDetails;

import java.util.function.Consumer;

import lombok.Setter;
import model.Person;
import viewFX.builders.PaneFXMLBuilder;

public class PersonDetailsPaneBuilder extends PaneFXMLBuilder<PersonDetailsPaneController> {

	@Setter
	private Consumer<Person> personClick;
	
	@Override
	public String getFxmlFileName() {
		return "PersonDetailsPane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setPersonClick(personClick);
	}

}
