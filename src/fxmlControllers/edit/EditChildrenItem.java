package fxmlControllers.edit;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditChildrenFieldBuilder;
import fxmlControllers.edit.EditItemController.EditField;
import model.Person;
import model.Tree;
import model.TreeTools;
import session.TreeSupplier;

public class EditChildrenItem extends EditItem<List<Person>> {
	
	private TreeTools tools;
	private EditChildrenFieldController fieldController;
	
	public void setTree(Tree tree) {
		fieldController.setTree(tree);
	}
	

	public EditChildrenItem(String label, TreeSupplier treeSupplier, BiConsumer<Person, List<Person>> setter, Function<Person, List<Person>> getter) {
		super(label, setter, getter);
		treeSupplier.addListener(this::updateTree);
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
		return new EditField(builder.getRegion(), fieldController);
	}
	
	private void updateTree (Tree tree) {
		fieldController.setTree(tree);
		
		tools = (tree == null) ? null : new TreeTools(tree);
	}
}
