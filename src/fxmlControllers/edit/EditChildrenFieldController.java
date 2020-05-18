package fxmlControllers.edit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Person;
import model.Tree;
import model.TreeTools;
import session.Session;

public class EditChildrenFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private VBox childList;
	@FXML
	private Label value;
	@FXML
	private Button resetButton;
	private SearchBox searchBox = new SearchBox();
	
	private TreeTools tools;
	
	private List<Person> oldChildren;
	private List<Person> children;

	
	public void setSession(Session session) {
		if (session == null) return;

		session.addNewTreeListener(this::setTree);
		session.addEditPersonListener(searchBox.getSearchEngine()::refreshOrAdd);
	}
	
	private void setTree(Tree tree) {
		tools = (tree == null) ? null : new TreeTools(tree);
		searchBox.setTree(tree);
	}

	@Override
	public void setOldValue(String valueText) {
		oldChildren = tools.stringToPersons(valueText);
		resetChildList();
		refreshChildList();
		searchBox.clear();
	}
	@Override
	public String getValue() {
		return tools.personsToString(children);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchBox.setSelectPerson(this::selectPerson);
		box.getChildren().add(searchBox);
		
		resetButton.setOnAction(e -> {
			resetChildList();
			refreshChildList();
		});
	}
	
	private void selectPerson(Person person) {
		addChild(person);
		refreshChildList();

		searchBox.hideSearch();
		box.requestFocus();
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
		label.getStyleClass().add("nameLabel");
		label.setOnMouseClicked(e -> {
			delChild(child);
			refreshChildList();
		});
		return label;
	}
}
