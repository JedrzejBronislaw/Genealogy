package viewFX.editPerson.fields.date;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.MyDate;
import model.Person;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController.EditField;

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
