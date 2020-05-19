package viewFX.editPerson.fields.marriages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Marriage;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeTools;
import model.tools.ManWoman;
import session.Session;
import viewFX.editPerson.fields.EditFieldInterface;
import viewFX.editPerson.fields.SearchBox;
import viewFX.editPerson.fields.marriages.item.EditMarriageItemFieldBuilder;
import viewFX.editPerson.fields.marriages.item.EditMarriageItemFieldController;

public class EditMarriagesFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private VBox marriageList;
	@FXML
	private Label value;
	@FXML
	private Button resetButton;
	private SearchBox searchBox = new SearchBox();
	
	private TreeTools tools;
	
	private List<Marriage> oldMarriages;
	private List<Marriage> marriages;
	private List<EditMarriageItemFieldController> fieldControllers;

	private Person person = null;
	private Sex personSex = Sex.UNKNOWN;

	public void setPerson(Person person) {
		this.person = person;
		personSex = person.getSex();
	}
	
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
		oldMarriages = tools.stringToMarriages(valueText);
		marriages = new ArrayList<>();
		resetMarriageList();
		refreshMarriageList();
		searchBox.clear();
	}
	@Override
	public String getValue() {
		updateMarriagesDetails();
		return tools.marriagesToString(marriages);
	}

	private void updateMarriagesDetails() {
		fieldControllers.forEach(controller -> controller.updateValues());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchBox.setSelectPerson(this::selectPerson);
		box.getChildren().add(searchBox);
		
		resetButton.setOnAction(e -> {
			resetMarriageList();
			refreshMarriageList();
		});
	}
	
	private void selectPerson(Person selectedPerson) {
		if (differentSexes(person, selectedPerson)) {
			addSpouse(selectedPerson);
			refreshMarriageList();

			searchBox.hideSearch();
			box.requestFocus();
		}
	}
	
	private boolean differentSexes(Person person1, Person person2) {
		return new ManWoman(person1, person2).success();
	}
	
	private void addSpouse(Person newSpouse) {
		if (!contains(marriages, newSpouse))
			marriages.add(new Marriage(person, newSpouse));
	}
	
	private void delMarriage(Marriage marriage) {
		marriages.remove(marriage);
	}

	private void resetMarriageList() {
		marriages.clear();
		oldMarriages.forEach(marriages::add);
	}
	
	private void refreshMarriageList() {
		fieldControllers = new ArrayList<>();
		marriageList.getChildren().clear();
		marriages.forEach(this::addMarriageItem);
	}
	
	private void addMarriageItem(Marriage marriage) {
		EditMarriageItemFieldBuilder builder = new EditMarriageItemFieldBuilder();
		EditMarriageItemFieldController controller;
		
		builder.build();
		controller = builder.getController();
		controller.setPersonSex(personSex);
		controller.setMarriage(marriage);
		controller.setDelPressEvent(() -> {
			delMarriage(marriage);
			updateMarriagesDetails();
			refreshMarriageList();
		});
		
		fieldControllers.add(controller);
		marriageList.getChildren().add(builder.getRegion());
	}
	
	private boolean contains(List<Marriage> marriages, Person spouse) {
		if (marriages == null) return false;

		if(personSex == Sex.MAN)
			for(Marriage marriage : marriages)
				if(marriage.getWife() == spouse) return true;

		if(personSex == Sex.WOMAN)
			for(Marriage marriage : marriages)
				if(marriage.getHusband() == spouse) return true;
			
		return false;
	}
}
