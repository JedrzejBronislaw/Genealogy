package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lang.Internationalization;
import lombok.Setter;
import model.MyDate;
import model.Person;
import model.Person.LifeStatus;
import tools.Injection;

public class PersonDetailsPaneController implements Initializable {

	@FXML
	private Label nameLabel;
	@FXML
	private Label surnameLabel;
	@FXML
	private Label fatherLabel;
	@FXML
	private Label motherLabel;
	@FXML
	private Label birthDateLabel;
	@FXML
	private Label deathDateLabel;
	
	private Person father, mother;
	
	@Setter
	private Consumer<Person> personClick;
	
	public void setPerson(Person person) {
		clearFields();
		father = person.getFather();
		mother = person.getMother();
		
		Platform.runLater(() -> {
			MyDate birthDate = person.getBirthDate();
			MyDate deathDate = person.getDeathDate();
			
			nameLabel.setText(person.getFirstName());
			surnameLabel.setText(person.getLastName());
			setParentLabel(fatherLabel, person.getFather());
			setParentLabel(motherLabel, person.getMother());
			if (birthDate != null) birthDateLabel.setText(birthDate.toString());
			if (person.getLifeStatus() == LifeStatus.NO)
				if (deathDate != null) deathDateLabel.setText(deathDate.toString());
			if (person.getLifeStatus() == LifeStatus.YES)
				deathDateLabel.setText(Internationalization.get("lives"));
		});
	}
	
	public void clearFields() {
		father = null;
		mother = null;
		
		Platform.runLater(() -> {
			nameLabel.setText("");
			surnameLabel.setText("");
			setParentLabel(fatherLabel, null);
			setParentLabel(motherLabel, null);
			birthDateLabel.setText("");
			deathDateLabel.setText("");
		});
	}
	
	public void setParentLabel(Label label, Person parent) {
		label.setText(System.lineSeparator());
		if (parent == null) return;
		
		String name = parent.getFirstName();
		String surname = parent.getLastName();
		StringBuffer text = new StringBuffer();
		
		text.append((name == null) ? "" : name);
		text.append(System.lineSeparator());
		text.append((surname == null) ? "" : surname);
		
		label.setText(text.toString());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearFields();
		
		motherLabel.setOnMouseClicked(e -> Injection.run(personClick, mother));
		fatherLabel.setOnMouseClicked(e -> Injection.run(personClick, father));
	}

}
