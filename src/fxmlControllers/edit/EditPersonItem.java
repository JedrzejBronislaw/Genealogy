package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditPersonFieldBuilder;
import fxmlControllers.edit.EditItemController.EditField;
import model.Person;
import model.Tree;
import session.TreeSupplier;

public class EditPersonItem extends EditItem<Person> {
	
	private TreeSupplier treeSupplier;
	private EditPersonFieldController fieldController;
	
	public void setTree(Tree tree) {
		fieldController.setTree(tree);
	}
	

	public EditPersonItem(String label, TreeSupplier treeSupplier, BiConsumer<Person, Person> setter, Function<Person, Person> getter) {
		super(label, setter, getter);
		this.treeSupplier = treeSupplier;
		treeSupplier.addListener(this::updateTree);
		build();
	}

	@Override
	protected String specialToString(Person value) {
		return treeSupplier.get().getID(value);
	}

	@Override
	protected Person stringToSpecial(String value) {
		return treeSupplier.get().getPerson(value);
	}

	@Override
	protected EditField createEditField() {
		EditPersonFieldBuilder builder = new EditPersonFieldBuilder();
		builder.build();
		fieldController = builder.getController();
		return new EditField(builder.getRegion(), fieldController);
	}
	
	private void updateTree (Tree tree) {
		fieldController.setTree(tree);
	}
}
