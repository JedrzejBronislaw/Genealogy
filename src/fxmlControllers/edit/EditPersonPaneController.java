package fxmlControllers.edit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
	private Runnable closePane;
	

	private Person person;
	
	private List<EditItemInterface> editItems = new ArrayList<>();
	
	
	public void addItem(EditItemInterface editItem) {
		editItems.add(editItem);
		itemsPane.getChildren().add(editItem.getPane());
	}
	
	public void setPerson(Person person) {
		this.person = person;
		editItems.forEach(item -> item.refresh(person));
	}
	
	public void save() {
		if (person != null)
			editItems.forEach(item -> item.saveTo(person));
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
