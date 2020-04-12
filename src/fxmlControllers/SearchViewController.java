package fxmlControllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Setter;
import model.Person;
import other.PersonDetails;
import tools.Injection;

public class SearchViewController implements Initializable {

	@FXML
	private ListView<Person> list;
	
	@FXML
	private Label numOfResults;
	
	@FXML
	private TextField queryField;
	

	@Setter
	private Consumer<Person> chooseAction;

	@Setter
	private Consumer<String> submitQuery;
	
	
	public void setItems(List<Person> items) {
		list.getItems().setAll(items);
		numOfResults.setText(Integer.toString(items.size()));
	}
	
	public void clearFields() {
		queryField.clear();
		list.getItems().clear();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		list.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2)
				Injection.run(chooseAction, list.getSelectionModel().getSelectedItem());
		});
		
		
		list.setCellFactory(param -> new ListCell<Person>() {
			protected void updateItem(Person item, boolean empty) {
				super.updateItem(item, empty);
				
				if(empty || item == null)
					setText(null);
				else
					setText(createLabel(item));
			};
		});
		
		queryField.textProperty().addListener((observable, oldValue, newValue) -> Injection.run(submitQuery, newValue));
	}

	public String createLabel(Person person) {
		String parents, label;
		
		parents = PersonDetails.whoseChild(person);
		if (parents.isEmpty())
			label = person.nameSurname();
		else
			label = person.nameSurname() + ", " + parents;
	
		return label;
	}
}