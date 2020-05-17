package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditDateFieldBuilder;
import fxmlControllers.edit.EditItemController.EditField;
import model.MyDate;
import model.Person;

public class EditDateItem extends EditItem<MyDate> {

	public EditDateItem(String label, BiConsumer<Person, MyDate> setter, Function<Person, MyDate> getter) {
		super(label, setter, getter);
		build();
	}

	@Override
	protected String specialToString(MyDate value) {
		return (value == null) ? "" : value.toString();
	}

	@Override
	protected MyDate stringToSpecial(String value) {
		return new MyDate(value);
	}

	@Override
	protected EditField createEditField() {
		EditDateFieldBuilder builder = new EditDateFieldBuilder();
		builder.build();
		return new EditField(builder.getRegion(), builder.getController());
	}
}
