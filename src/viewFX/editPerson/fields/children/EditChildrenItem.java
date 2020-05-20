package viewFX.editPerson.fields.children;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Person;
import model.Tree;
import model.TreeTools;
import session.Session;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditChildrenItem extends EditItem<List<Person>> {

	private Session session;
	private TreeTools tools;
	private EditChildrenFieldController fieldController;
	

	public EditChildrenItem(String label, Session session, BiConsumer<Person, List<Person>> setter, Function<Person, List<Person>> getter) {
		super(label, setter, getter);
		this.session = session;
		session.addNewTreeListener(this::updateTreeTools);
		build();
	}

	@Override
	protected String specialToString(List<Person> value) {
		if (tools == null) return null;
		
		return tools.personsToString(value);
	}

	@Override
	protected List<Person> stringToSpecial(String value) {
		if (tools == null) return null;
		
		return tools.stringToPersons(value);
	}

	@Override
	protected EditField createEditField() {
		EditChildrenFieldBuilder builder = new EditChildrenFieldBuilder();
		builder.build();
		fieldController = builder.getController();
		fieldController.setSession(session);
		return new EditField(builder.getRegion(), fieldController);
	}
	
	private void updateTreeTools(Tree tree) {
		tools = (tree == null) ? null : new TreeTools(tree);
	}
}
