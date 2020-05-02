package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import fxmlBuilders.edit.EditTextFieldBuilder;
import fxmlControllers.edit.EditItemController.EditField;
import model.Person;
import model.Tree;

public class EditPersonItem extends EditItem<Person> {

	private Supplier<Tree> getTree;
	
	public EditPersonItem(String label, Supplier<Tree> getTree, BiConsumer<Person, Person> setter, Function<Person, Person> getter) {
		super(label, setter, getter);
		this.getTree = getTree;
		build();
	}

	@Override
	protected String specialToString(Person value) {
		return getTree.get().getID(value);
	}

	@Override
	protected Person stringToSpecial(String value) {
		return getTree.get().getPerson(value);
	}

	@Override
	protected EditField createEditField() {
		EditTextFieldBuilder builder = new EditTextFieldBuilder();
		builder.build();
		return new EditField(builder.getRegion(), builder.getController());
	}
}
