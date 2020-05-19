package viewFX.editPerson.fields;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.scene.layout.Pane;
import lombok.Getter;
import model.Person;
import viewFX.editPerson.fields.EditItemController.EditField;

public abstract class EditItem<ValueType> implements EditItemInterface{

	@Getter
	protected Pane pane;
	protected EditItemController controller;

	private String label;
	private BiConsumer<Person, ValueType> setter;
	private Function<Person, ValueType> getter;

	
	public EditItem(String label, BiConsumer<Person, ValueType> setter, Function<Person, ValueType> getter) {
		this.label = label;
		this.getter = getter;
		this.setter = setter;
	}
	
	protected void build() {
		EditItemBuilder builder = new EditItemBuilder();
		builder.setEditField(createEditField());
		builder.build();
		builder.getController().setLabel(label);
		
		this.pane = builder.getPane();
		this.controller = builder.getController();
	}
	
	@Override
	public void saveTo(Person person) {
		ValueType v = stringToSpecial(controller.getValue());
		setter.accept(person, v);
	}
	@Override
	public void refresh(Person person) {
		beforeRefreshingPerson(person);
		ValueType value = (person != null) ? getter.apply(person) : null;
		String strValue = specialToString(value);
		controller.setOldValue(strValue);
	}
	
	protected abstract String specialToString(ValueType value);
	protected abstract ValueType stringToSpecial(String value);
	protected abstract EditField createEditField();
	protected void beforeRefreshingPerson(Person person) {};
}
