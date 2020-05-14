package fxmlControllers.edit;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditMarriagesFieldBuilder;
import fxmlControllers.edit.EditItemController.EditField;
import model.Marriage;
import model.Person;
import model.Tree;
import model.TreeTools;
import session.TreeSupplier;

public class EditMarriagesItem extends EditItem<List<Marriage>> {
	
	private TreeTools tools;
	private EditMarriagesFieldController fieldController;

	
	public EditMarriagesItem(String label, TreeSupplier treeSupplier, BiConsumer<Person, List<Marriage>> setter, Function<Person, List<Marriage>> getter) {
		super(label, setter, getter);
		treeSupplier.addListener(this::updateTree);
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
		return new EditField(builder.getRegion(), fieldController);
	}

	@Override
	protected void beforeRefreshingPerson(Person person) {
		fieldController.setPerson(person);
	}
	
	private void updateTree(Tree tree) {
		fieldController.setTree(tree);
		tools = (tree == null) ? null : new TreeTools(tree);
	}
}
