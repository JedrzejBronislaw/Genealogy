package viewFX.editPerson.fields.person;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Person;
import session.Session;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditPersonItem extends EditItem<Person> {
	
	private Session session;
	private EditPersonFieldController fieldController;
	

	public EditPersonItem(String label, Session session, BiConsumer<Person, Person> setter, Function<Person, Person> getter) {
		super(label, setter, getter);
		this.session = session;
		build();
	}

	@Override
	protected String specialToString(Person value) {
		return session.getTree().getID(value);
	}

	@Override
	protected Person stringToSpecial(String value) {
		return session.getTree().getPerson(value);
	}

	@Override
	protected EditField createEditField() {
		EditPersonFieldBuilder builder = new EditPersonFieldBuilder();
		builder.build();
		fieldController = builder.getController();
		fieldController.setSession(session);
		return new EditField(builder.getRegion(), fieldController);
	}
}
