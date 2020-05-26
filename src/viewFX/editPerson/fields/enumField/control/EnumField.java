package viewFX.editPerson.fields.enumField.control;

import java.util.function.Consumer;

import javafx.scene.Node;
import lombok.Getter;
import tools.Injection;
import tools.Tools;
import viewFX.editPerson.fields.enumField.EditEnumFieldBuilder;
import viewFX.editPerson.fields.enumField.EditEnumFieldController;

public class EnumField<T extends Enum<T>> {

	@Getter
	private Node node;
	private EditEnumFieldController controller;
	
	private Class<T> enumClass;
	
	@SuppressWarnings("unchecked")
	public EnumField(T initialValue) {
		enumClass = (Class<T>) initialValue.getClass();
		build();
		setValue(initialValue);
	}

	private void build() {
		EditEnumFieldBuilder builder = new EditEnumFieldBuilder();
		
		builder.setOptions(Tools.getStringValues(enumClass));
		builder.build();
		
		controller = builder.getController();
		node = builder.getNode();
	}
	
	public void setValue(T value) {
		if (value == null)
			controller.uncheckAll();
		else
			controller.setOldValue(value.toString());
	}

	public T getValue() {
		return strToValue(controller.getValue());
	}
	
	public void setOnChange(Consumer<T> onChange) {
		controller.setOnChange(strValue ->
			Injection.run(onChange, strToValue(strValue)));
	}

	private T strToValue(String string) {
		return Enum.valueOf(enumClass, string);
	}
}
