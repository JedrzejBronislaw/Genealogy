package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import fxmlBuilders.SearchViewBuilder;
import fxmlControllers.SearchViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lang.Internationalization;
import model.Person;
import model.Tree;
import searchEngine.SearchEngine;

public class EditPersonFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private Label value;
	@FXML
	private Label showSearchLabel;

	private Tree tree;
	private SearchEngine searchEngine = new SearchEngine();
	private SearchViewController searchController;
	
	private String selectedPersonID;

	private Pane searchPane;
	private boolean searchVisible;
	
	public void setTree(Tree tree) {
		this.tree = tree;
		searchEngine.setTree(tree);
	}

	@Override
	public void setOldValue(String valueText) {
		selectedPersonID = valueText;
		Person person = tree.getPerson(selectedPersonID);
		String personName = (person == null) ? "" : person.nameSurname();
		value.setText(personName);
		
		searchController.clearFields();
	}
	@Override
	public String getValue() {
		return selectedPersonID;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchPane = generateSearchPane();
		box.getChildren().add(searchPane);
		hideSearch();
		
		showSearchLabel.setOnMouseClicked(e -> {
			if (searchVisible)
				hideSearch();
			else
				showSearch();
		});
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
		hideSearch();
	}

	private void showSearch() {
		searchPane.setManaged(true);
		searchPane.setVisible(true);
		searchVisible = true;
		showSearchLabel.setText(Internationalization.get("hide_search"));
	}
	private void hideSearch() {
		searchPane.setManaged(false);
		searchPane.setVisible(false);
		searchVisible = false;
		showSearchLabel.setText(Internationalization.get("show_search"));
	}
}
