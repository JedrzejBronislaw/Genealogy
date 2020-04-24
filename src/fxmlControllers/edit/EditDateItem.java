package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditItemBuilder.Type;
import model.MyDate;
import model.Person;

public class EditDateItem extends EditItem<MyDate> {

	public EditDateItem(String label, BiConsumer<Person, MyDate> setter, Function<Person, MyDate> getter) {
		super(label, Type.Date, setter, getter);
	}

	@Override
	protected String specialToString(MyDate value) {
		return value.toString();
	}

	@Override
	protected MyDate stringToSpecial(String value) {
		return new MyDate(value);
	}
}
