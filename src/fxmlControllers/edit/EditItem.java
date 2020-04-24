package fxmlControllers.edit;

import java.util.function.BiConsumer;
import java.util.function.Function;

import fxmlBuilders.edit.EditItemBuilder;
import fxmlBuilders.edit.EditItemBuilder.Type;
import javafx.scene.layout.Pane;
import lombok.Getter;
import model.Person;

public abstract class EditItem<ValueType> implements EditItemInterface{
	@Getter
	protected Pane pane;
	protected EditItemController controller;
	
	private BiConsumer<Person, ValueType> setter;
	private Function<Person, ValueType> getter;

	
	public EditItem(String label, Type type, BiConsumer<Person, ValueType> setter, Function<Person, ValueType> getter) {
		EditItemBuilder builder = new EditItemBuilder();
		builder.setFieldType(type);
		builder.build();
		builder.getController().setLabel(label);
		
		this.pane = builder.getPane();
		this.controller = builder.getController();
		this.getter = getter;
		this.setter = setter;
	}
	
	@Override
	public void saveTo(Person person) {
		ValueType v = stringToSpecial(controller.getValue());
		setter.accept(person, v);
	}
	@Override
	public void refresh(Person person) {
		ValueType value = (person != null) ? getter.apply(person) : null;
		String strValue = specialToString(value);
		controller.setOldValue(strValue);
	}
	
	protected abstract String specialToString(ValueType value);
	protected abstract ValueType stringToSpecial(String value);
}
