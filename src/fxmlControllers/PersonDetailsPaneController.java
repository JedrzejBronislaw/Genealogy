package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Person;

public class PersonDetailsPaneController implements Initializable {

	@FXML
	private Label nameLabel;
	@FXML
	private Label surnameLabel;
	
	public void setPerson(Person person) {
		nameLabel.setText(person.getFirstName());
		surnameLabel.setText(person.getLastName());
	}
	
	public void clearFields() {
		nameLabel.setText("");
		surnameLabel.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearFields();
	}

}
