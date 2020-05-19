package viewFX.editPerson.fields.mlText;

import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Person;
import viewFX.editPerson.fields.EditItem;
import viewFX.editPerson.fields.EditItemController;
import viewFX.editPerson.fields.EditItemController.EditField;

public class EditMLTextItem extends EditItem<String> {

	public EditMLTextItem(String label, BiConsumer<Person, String> setter, Function<Person, String> getter) {
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
		EditMLTextFieldBuilder builder = new EditMLTextFieldBuilder();
		builder.build();
		return new EditField(builder.getRegion(), builder.getController());
	}
}
