package fxmlControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import fxmlBuilders.EditItemBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import tools.Injection;

public class EditPersonPaneController implements Initializable {

	public static class EditField{
		@Getter
		private Pane pane;
		private EditItemController controller;
		
		private BiConsumer<Person, String> setter;
		private Function<Person, String> getter;

		
		public EditField(String label, BiConsumer<Person, String> setter, Function<Person, String> getter) {
			EditItemBuilder builder = new EditItemBuilder();
			builder.build();
			builder.getController().setLabel(label);
			
			this.pane = builder.getPane();
			this.controller = builder.getController();
			this.getter = getter;
			this.setter = setter;
		}
		
		
		public void save(Person person, String value) {
			setter.accept(person, value);
		}
		public void refresh(Person person) {
			if (person == null) return;
			
			String value = (person != null) ? getter.apply(person) : "";
			controller.setOldValue(value);
		}
		public String getValue() {
			return controller.getValue();
		}
	}
	
	
	@FXML
	private VBox itemsPane;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	@Setter
	private Consumer<Person> changeEvent;
	@Setter
	private Runnable closePane;
	

	private Person person;
	
	private List<EditField> editItems = new ArrayList<>();
	
	
	public void addItem(EditField editItem) {
		editItems.add(editItem);
		itemsPane.getChildren().add(editItem.getPane());
	}
	
	public void setPerson(Person person) {
		this.person = person;
		editItems.forEach(item -> item.refresh(person));
	}
	
	public void save() {
		if (person != null)
			editItems.forEach(item -> item.save(person, item.getValue()));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancelButton.setOnMouseClicked(e -> Injection.run(closePane));
		okButton.setOnMouseClicked(e -> {
			save();
			Injection.run(changeEvent, person);
			Injection.run(closePane);
		});
	}

}
