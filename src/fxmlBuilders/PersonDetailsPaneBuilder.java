package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.PersonDetailsPaneController;
import lombok.Setter;
import model.Person;

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
