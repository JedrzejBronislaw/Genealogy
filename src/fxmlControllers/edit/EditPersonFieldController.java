package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Person;
import model.Tree;

public class EditPersonFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private Label value;

	private Tree tree;
	
	private String selectedPersonID;
	private SearchBox searchBox;

	
	public void setTree(Tree tree) {
		this.tree = tree;
		searchBox.setTree(tree);
	}

	@Override
	public void setOldValue(String valueText) {
		selectedPersonID = valueText;
		Person person = tree.getPerson(selectedPersonID);
		String personName = (person == null) ? "" : person.nameSurname();
		value.setText(personName);
		
		searchBox.clear();
	}
	@Override
	public String getValue() {
		return selectedPersonID;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchBox = new SearchBox();
		searchBox.setSelectPerson(this::selectPerson);
		box.getChildren().add(searchBox);
	}

	private void selectPerson(Person person) {
		selectedPersonID = tree.getID(person);
		value.setText(person.nameSurname());

		searchBox.hideSearch();
		box.requestFocus();
	}
}
