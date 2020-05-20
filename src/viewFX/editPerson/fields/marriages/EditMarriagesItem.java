package viewFX.editPerson.fields.marriages;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Marriage;
import model.Person;
import model.Tree;
import model.TreeTools;
import session.Session;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditMarriagesItem extends EditItem<List<Marriage>> {

	private Session session;
	private TreeTools tools;
	private EditMarriagesFieldController fieldController;

	
	public EditMarriagesItem(String label, Session session, BiConsumer<Person, List<Marriage>> setter, Function<Person, List<Marriage>> getter) {
		super(label, setter, getter);
		this.session = session;
		session.addNewTreeListener(this::updateTreeTools);
		build();
	}

	@Override
	protected String specialToString(List<Marriage> value) {
		if (tools == null) return null;
		return tools.marriagesToString(value);
	}

	@Override
	protected List<Marriage> stringToSpecial(String value) {
		if (tools == null) return null;
		return tools.stringToMarriages(value);
	}

	@Override
	protected EditField createEditField() {
		EditMarriagesFieldBuilder builder = new EditMarriagesFieldBuilder();
		builder.build();
		fieldController = builder.getController();
		fieldController.setSession(session);
		return new EditField(builder.getRegion(), fieldController);
	}

	@Override
	protected void beforeRefreshingPerson(Person person) {
		fieldController.setPerson(person);
	}
	
	private void updateTreeTools(Tree tree) {
		tools = (tree == null) ? null : new TreeTools(tree);
	}
}
