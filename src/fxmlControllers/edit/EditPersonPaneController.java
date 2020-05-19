package fxmlControllers.edit;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import fxmlBuilders.edit.EditPersonLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Person;
import tools.Injection;

public class EditPersonPaneController implements Initializable {

	@FXML
	private VBox itemsPane;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	@Setter
	private Consumer<Person> changeEvent;
	@Setter
	private Consumer<Person> closePane;
	@Setter
	private Consumer<Person> addToTree;
	
	@Setter
	private boolean addToTreeWhenSaving = false;

	private Person person;
	
	private List<EditItemInterface> editItems;
	
	public void setEditItems(EditPersonLayout layout) {
		if (layout == null) return;
		
		editItems = layout.getEditItems();
		layout.set(itemsPane);
	}
	
	public void setPerson(Person person) {
		this.person = person;
		editItems.forEach(item -> item.refresh(person));
	}
	
	public void save() {
		if (person == null) return;
		
		editItems.forEach(item -> item.saveTo(person));
		
		if (addToTreeWhenSaving) {
			Injection.run(addToTree, person);
			addToTreeWhenSaving = false;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancelButton.setOnMouseClicked(e -> Injection.run(closePane, person));
		okButton.setOnMouseClicked(e -> {
			save();
			Injection.run(changeEvent, person);
			Injection.run(closePane, person);
		});
	}

}
