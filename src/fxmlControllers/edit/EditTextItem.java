package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditTextFieldBuilder;
import fxmlControllers.edit.EditItemController.EditField;
import model.Person;

public class EditTextItem extends EditItem<String> {

	public EditTextItem(String label, BiConsumer<Person, String> setter, Function<Person, String> getter) {
		super(label, setter, getter);
		build();
	}

	@Override
	protected String specialToString(String value) {
		return value;
	}

	@Override
	protected String stringToSpecial(String value) {
		return value;
	}

	@Override
	protected EditField createEditField() {
		EditTextFieldBuilder builder = new EditTextFieldBuilder();
		builder.build();
		return new EditField(builder.getNode(), builder.getController());
	}
}
