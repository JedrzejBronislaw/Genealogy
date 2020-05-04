package fxmlBuilders.edit;

import java.util.function.Consumer;

import fxmlBuilders.PaneFXMLBuilder;
import fxmlControllers.edit.EditDateItem;
import fxmlControllers.edit.EditEnumItem;
import fxmlControllers.edit.EditPersonItem;
import fxmlControllers.edit.EditPersonPaneController;
import fxmlControllers.edit.EditTextItem;
import lang.Internationalization;
import lombok.Setter;
import model.Person;
import model.Person.LifeStatus;
import model.Person.Sex;
import model.Tree;
import model.familyRelations.Editor;
import tools.Tools;

public class EditPersonPaneBuilder extends PaneFXMLBuilder<EditPersonPaneController> {
	
	@Setter
	private Consumer<Person> changeEvent;
	@Setter
	private Runnable closePane;
	@Setter
	private Tree tree;
	

	@Override
	public String getFxmlFileName() {
		return "EditPersonPane.fxml";
	}

	@Override
	public void afterBuild() {
		Editor relationEditor = new Editor(tree);
		controller.setChangeEvent(changeEvent);
		controller.setClosePane(closePane);
		
		controller.addItem(new EditTextItem(Internationalization.get("first_name"),
				(person, value) -> person.setFirstName(value),
				person -> person.getFirstName()));
		controller.addItem(new EditTextItem(Internationalization.get("alias"),
				(person, value) -> person.setAlias(value),
				person -> person.getAlias()));
		controller.addItem(new EditTextItem(Internationalization.get("last_name"),
				(person, value) -> person.setLastName(value),
				person -> person.getLastName()));
		
		controller.addItem(new EditDateItem(Internationalization.get("birth_date"),
				(person, value) -> person.setBirthDate(value),
				person -> person.getBirthDate()));
		controller.addItem(new EditTextItem(Internationalization.get("birth_place"),
				(person, value) -> person.setBirthPlace(value),
				person -> person.getBirthPlace()));

		controller.addItem(new EditDateItem(Internationalization.get("death_date"),
				(person, value) -> person.setDeathDate(value),
				person -> person.getDeathDate()));
		controller.addItem(new EditTextItem(Internationalization.get("death_place"),
				(person, value) -> person.setDeathPlace(value),
				person -> person.getDeathPlace()));

		controller.addItem(new EditEnumItem(Internationalization.get("lives"),
				Tools.getStringValues(LifeStatus.class),
				(person, value) -> person.setLifeStatus(LifeStatus.valueOf(value)),
				person -> person.getLifeStatus().toString()));
		controller.addItem(new EditEnumItem(Internationalization.get("sex"),
				Tools.getStringValues(Sex.class),
				(person, value) -> person.setSex(Sex.valueOf(value)),
				person -> person.getSex().toString()));

		controller.addItem(new EditTextItem(Internationalization.get("baptism_parish"),
				(person, value) -> person.setBaptismParish(value),
				person -> person.getBaptismParish()));
		controller.addItem(new EditTextItem(Internationalization.get("burial_place"),
				(person, value) -> person.setBurialPlace(value),
				person -> person.getBurialPlace()));
		
		//contact
		//comments

		controller.addItem(new EditPersonItem(Internationalization.get("father"),
				() -> tree,
				(person, value) -> relationEditor.setFatherChildRel(value, person),
				person -> person.getFather()));
		controller.addItem(new EditPersonItem(Internationalization.get("mother"),
				() -> tree,
				(person, value) -> relationEditor.setMotherChildRel(value, person),
				person -> person.getMother()));

		//spouses
		//children
	}
}
