package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.EditPersonPaneController;
import fxmlControllers.EditPersonPaneController.EditField;
import javafx.scene.layout.Pane;
import lang.Internationalization;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class EditPersonPaneBuilder {
	
	@Getter
	private Pane pane;
	@Getter
	private EditPersonPaneController controller;

	@Setter
	private Consumer<Person> changeEvent;
	@Setter
	private Runnable closePane;

	
	public void build(){
		MyFXMLLoader<EditPersonPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<EditPersonPaneController> nac = loader.create("EditPersonPane.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
		controller.setChangeEvent(changeEvent);
		controller.setClosePane(closePane);
		
		controller.addItem(new EditField(Internationalization.get("first_name"),
				(person, value) -> person.setFirstName(value),
				person -> person.getFirstName()));
		controller.addItem(new EditField(Internationalization.get("alias"),
				(person, value) -> person.setAlias(value),
				person -> person.getAlias()));
		controller.addItem(new EditField(Internationalization.get("last_name"),
				(person, value) -> person.setLastName(value),
				person -> person.getLastName()));
		//birth date
		controller.addItem(new EditField(Internationalization.get("birth_place"),
				(person, value) -> person.setBirthPlace(value),
				person -> person.getBirthPlace()));
		//death date
		controller.addItem(new EditField(Internationalization.get("death_place"),
				(person, value) -> person.setDeathPlace(value),
				person -> person.getDeathPlace()));
		//isalive
		//sex
		controller.addItem(new EditField(Internationalization.get("baptism_parish"),
				(person, value) -> person.setBaptismParish(value),
				person -> person.getBaptismParish()));
		controller.addItem(new EditField(Internationalization.get("burial_place"),
				(person, value) -> person.setBurialPlace(value),
				person -> person.getBurialPlace()));
		
		//contact
		//comments

		//father
		//mother
		//spouses
		//children
	}
}
