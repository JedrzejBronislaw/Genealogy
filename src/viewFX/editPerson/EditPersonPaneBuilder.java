package viewFX.editPerson;

import java.util.Arrays;
import java.util.function.Consumer;

import lang.Internationalization;
import lombok.Setter;
import model.LifeStatus;
import model.Person;
import model.Sex;
import model.familyRelations.RelationEditor;
import session.Session;
import tools.Tools;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.editPerson.fields.children.EditChildrenItem;
import viewFX.editPerson.fields.date.EditDateItem;
import viewFX.editPerson.fields.enumField.EditEnumItem;
import viewFX.editPerson.fields.marriages.EditMarriagesItem;
import viewFX.editPerson.fields.mlText.EditMLTextItem;
import viewFX.editPerson.fields.person.EditPersonItem;
import viewFX.editPerson.fields.text.EditTextItem;

public class EditPersonPaneBuilder extends PaneFXMLBuilder<EditPersonPaneController> {
	
	@Setter
	private Consumer<Person> changeEvent;
	@Setter
	private Consumer<Person> closePane;
	@Setter
	private Consumer<Person> addToTree;

	@Setter
	private Session session;

	
	@Override
	protected String getFxmlFileName() {
		return "EditPersonPane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setChangeEvent(changeEvent);
		controller.setClosePane(closePane);
		controller.setAddToTree(addToTree);
		
		controller.setEditItems(createEditItems());
	}

	private EditPersonLayout createEditItems() {
		EditPersonLayout layout = new EditPersonLayout();
		RelationEditor relationEditor = new RelationEditor();
		
		layout.addItem(new EditTextItem(Internationalization.get("first_name"),
				(person, value) -> person.setFirstName(value),
				person -> person.getFirstName(),
				true));
		layout.addItem(new EditTextItem(Internationalization.get("alias"),
				(person, value) -> person.setAlias(value),
				person -> person.getAlias(),
				true));
		layout.addItem(new EditTextItem(Internationalization.get("last_name"),
				(person, value) -> person.setLastName(value),
				person -> person.getLastName(),
				true));
		
		layout.addItem(new EditDateItem(Internationalization.get("birth_date"),
				(person, value) -> person.setBirthDate(value),
				person -> person.getBirthDate()));
		layout.addItem(new EditTextItem(Internationalization.get("birth_place"),
				(person, value) -> person.setBirthPlace(value),
				person -> person.getBirthPlace(),
				true));

		layout.addItem(new EditDateItem(Internationalization.get("death_date"),
				(person, value) -> person.setDeathDate(value),
				person -> person.getDeathDate()));
		layout.addItem(new EditTextItem(Internationalization.get("death_place"),
				(person, value) -> person.setDeathPlace(value),
				person -> person.getDeathPlace(),
				true));

		layout.addItem(new EditEnumItem(Internationalization.get("lives"),
				Tools.getStringValues(LifeStatus.class),
				(person, value) -> person.setLifeStatus(LifeStatus.valueOf(value)),
				person -> person.getLifeStatus().name()));
		layout.addItem(new EditEnumItem(Internationalization.get("sex"),
				Tools.getStringValues(Sex.class),
				(person, value) -> person.setSex(Sex.valueOf(value)),
				person -> person.getSex().name()));

		layout.addItem(new EditTextItem(Internationalization.get("baptism_parish"),
				(person, value) -> person.setBaptismParish(value),
				person -> person.getBaptismParish(),
				false));
		layout.addItem(new EditTextItem(Internationalization.get("burial_place"),
				(person, value) -> person.setBurialPlace(value),
				person -> person.getBurialPlace(),
				false));
		
		layout.newColumn();

		layout.addItem(new EditPersonItem(Internationalization.get("father"),
				session,
				(person, value) -> relationEditor.setFatherChildRel(value, person),
				person -> person.getFather()));
		layout.addItem(new EditPersonItem(Internationalization.get("mother"),
				session,
				(person, value) -> relationEditor.setMotherChildRel(value, person),
				person -> person.getMother()));

		layout.addItem(new EditMarriagesItem(Internationalization.get("marriages"),
				session,
				(person, value) -> relationEditor.setMarriagesRel(person, value),
				person -> Arrays.asList(person.getMarriages())));

		layout.addItem(new EditChildrenItem(Internationalization.get("children"),
				session,
				(person, value) -> relationEditor.setParentChildrenRel(person, value),
				person -> Arrays.asList(person.getChildren())));
		
		layout.newColumn();
		
		layout.addItem(new EditMLTextItem(Internationalization.get("contact"),
				(person, value) -> person.setContact(value),
				person -> person.getContact()));
		layout.addItem(new EditMLTextItem(Internationalization.get("comments"),
				(person, value) -> person.setComments(value),
				person -> person.getComments()));
		
		return layout;
	}
}
