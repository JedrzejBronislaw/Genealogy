package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import fxmlBuilders.SearchViewBuilder;
import fxmlControllers.SearchViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Person;
import model.Tree;
import searchEngine.SearchEngine;

public class EditPersonFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private TextField value;
	
	private Tree tree;
	private SearchEngine searchEngine = new SearchEngine();
	private SearchViewController searchController;
	
	private String selectedPersonID;
	
	public void setTree(Tree tree) {
		this.tree = tree;
		searchEngine.setTree(tree);
	}

	@Override
	public void setOldValue(String valueText) {
		selectedPersonID = valueText;
		String personName = tree.getPerson(selectedPersonID).nameSurname();
		value.setText(personName);
		value.setPromptText(personName);
		
		searchController.clearFields();
	}
	@Override
	public String getValue() {
		return selectedPersonID;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		box.getChildren().add(generateSearchPane());
		
	}
	
	private Pane generateSearchPane() {
		SearchViewBuilder builder = new SearchViewBuilder();
		
		builder.setChooseAction(this::selectPerson);
		builder.setSearchEngine(searchEngine);
		
		builder.build();
		
		searchController = builder.getController();
		return builder.getPane();
	}
	
	private void selectPerson(Person person) {
		selectedPersonID = tree.getID(person);
		value.setText(person.nameSurname());
	}
}
