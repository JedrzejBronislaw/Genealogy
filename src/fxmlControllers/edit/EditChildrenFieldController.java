package fxmlControllers.edit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fxmlBuilders.SearchViewBuilder;
import fxmlControllers.SearchViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Person;
import model.Tree;
import model.TreeTools;
import searchEngine.SearchEngine;

public class EditChildrenFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private VBox childList;
	@FXML
	private Label value;
	@FXML
	private Button resetButton;
	
	private TreeTools tools;
	private SearchEngine searchEngine = new SearchEngine();
	private SearchViewController searchController;
	
	private List<Person> oldChildren;
	private List<Person> children;
	
	public void setTree(Tree tree) {
		tools = (tree == null) ? null : new TreeTools(tree);
		searchEngine.setTree(tree);
	}

	@Override
	public void setOldValue(String valueText) {
		oldChildren = tools.stringToPersons(valueText);
		resetChildList();
		refreshChildList();
		searchController.clearFields();
	}
	@Override
	public String getValue() {
		return tools.personsToString(children);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		box.getChildren().add(generateSearchPane());
		resetButton.setOnAction(e -> {
			resetChildList();
			refreshChildList();
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
		addChild(person);
		refreshChildList();
	}
	
	private void addChild(Person newChild) {
		if (!children.contains(newChild))
			children.add(newChild);
	}
	
	private void delChild(Person child) {
		if (children.contains(child))
			children.remove(child);
	}
	
	private void resetChildList() {
		if (children == null) children = new ArrayList<>();
		children.clear();
		oldChildren.forEach(child -> children.add(child));
	}
	
	private void refreshChildList() {
		childList.getChildren().clear();
		children.forEach(child -> childList.getChildren().add(createChildLabel(child)));
	}

	private Label createChildLabel(Person child) {
		Label label = new Label(child.nameSurname());
		label.setOnMouseClicked(e -> {
			delChild(child);
			refreshChildList();
		});
		return label;
	}
}
