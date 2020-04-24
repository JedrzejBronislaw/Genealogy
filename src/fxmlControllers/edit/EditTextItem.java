package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditItemBuilder.Type;
import model.Person;

public class EditTextItem extends EditItem<String> {

	public EditTextItem(String label, BiConsumer<Person, String> setter, Function<Person, String> getter) {
		super(label, Type.Text, setter, getter);
	}

	@Override
	protected String specialToString(String value) {
		return value;
	}

	@Override
	protected String stringToSpecial(String value) {
		return value;
	}
}
