package viewFX.editPerson.fields.text;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Person;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditTextItem extends EditItem<String> {

	private boolean autoUpper;
	
	public EditTextItem(String label, BiConsumer<Person, String> setter, Function<Person, String> getter, boolean autoUpperFirstCase) {
		super(label, setter, getter);
		autoUpper = autoUpperFirstCase;
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
		builder.getController().setAutoUpperFirstCase(autoUpper);
		return new EditField(builder.getRegion(), builder.getController());
	}
}
