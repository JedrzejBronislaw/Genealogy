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
import model.Marriage;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeTools;
import model.tools.ManWoman;
import searchEngine.SearchEngine;

public class EditMarriagesFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	@FXML
	private VBox marriageList;
	@FXML
	private Label value;
	@FXML
	private Button resetButton;
	
	private TreeTools tools;
	private SearchEngine searchEngine = new SearchEngine();
	private SearchViewController searchController;
	
	private List<Marriage> oldMarriages;
	private List<Marriage> marriages;

	private Person person = null;
	private Sex personSex = Sex.UNKNOWN;

	public void setPerson(Person person) {
		this.person = person;
		personSex = person.getSex();
	}
	
	public void setTree(Tree tree) {
		tools = (tree == null) ? null : new TreeTools(tree);
		searchEngine.setTree(tree);
	}

	@Override
	public void setOldValue(String valueText) {
		oldMarriages = tools.stringToMarriages(valueText);
		marriages = new ArrayList<>();
		resetMarriageList();
		refreshMarriageList();
		searchController.clearFields();
	}
	@Override
	public String getValue() {
		return tools.marriagesToString(marriages);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		box.getChildren().add(generateSearchPane());
		
		resetButton.setOnAction(e -> {
			resetMarriageList();
			refreshMarriageList();
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
	
	private void selectPerson(Person selectedPerson) {
		if (differentSexes(person, selectedPerson)) {
			addSpouse(selectedPerson);
			refreshMarriageList();
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
		marriageList.getChildren().clear();
		marriages.forEach(this::addMarriageItem);
	}
	
	private void addMarriageItem(Marriage marriage) {
		marriageList.getChildren().add(createMarriageLabel(marriage));
	}

	private Label createMarriageLabel(Marriage marriage) {
		Label label = new Label(generateLabelText(marriage));
		
		label.setOnMouseClicked(e -> {
			delMarriage(marriage);
			refreshMarriageList();
		});
		
		return label;
	}

	private String generateLabelText(Marriage marriage) {
		StringBuilder sb = new StringBuilder();
		Person spouse = null;
		String date  = marriage.getDate();
		String place = marriage.getPlace();
		
		if (personSex == Sex.MAN)
			spouse = marriage.getWife();
		if (personSex == Sex.WOMAN)
			spouse = marriage.getHusband();
		
		if (spouse != null) sb.append(spouse.nameSurname());
			
		
		for(String detail : new String[] {date, place}) {
			sb.append(" ");
			if (detail != null && !detail.isEmpty())
				sb.append(detail);
		}
		
		return sb.toString();
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
	
//	private void remove(List<Marriage> marriages, Person spouse) {
//		if(personSex == Sex.MAN)
//			for(Marriage marriage : marriages)
//				if(marriage.getWife() == spouse) marriages.remove(marriage);
//
//		if(personSex == Sex.WOMAN)
//			for(Marriage marriage : marriages)
//				if(marriage.getHusband() == spouse) marriages.remove(marriage);
//	}
}
