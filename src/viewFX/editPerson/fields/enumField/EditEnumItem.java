package viewFX.editPerson.fields.enumField;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Person;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditEnumItem extends EditItem<String> {

	private String[] values;
	
	public EditEnumItem(String label, String[] values, BiConsumer<Person, String> setter, Function<Person, String> getter) {
		super(label, setter, getter);
		this.values = values;
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
		EditEnumFieldBuilder builder = new EditEnumFieldBuilder();
		builder.setOptions(values);
		builder.build();
		return new EditField(builder.getRegion(), builder.getController());
	}
}
