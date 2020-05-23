package viewFX.treeGraph.graphOptions;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Person;
import nameDisplaying.NameDisplayingType;
import session.Session;
import tools.Injection;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import viewFX.editPerson.fields.SearchBox;
import viewFX.editPerson.fields.enumField.control.EnumField;

public class GraphOptionsPaneController implements Initializable {

	@FXML
	private Label personName;

	@FXML
	private Button drawButton;

	@FXML
	private VBox personBox;
	@FXML
	private VBox graphTypeBox;
	@FXML
	private VBox personDisplayerBox;
	
	private SearchBox searchBox = new SearchBox();
	private EnumField<TreeGraphType> graphTypeField = new EnumField<>(TreeGraphType.ancestors);
	private EnumField<NameDisplayingType> nameDisplayingField = new EnumField<>(NameDisplayingType.onlyName);
	
	@Setter
	private Consumer<TreeGraphParameters> drawAction;
	
	private Person person = null;
	
	public void setState(TreeGraphParameters parameters) {
		if (parameters == null) return;
		
		selectPerson(parameters.getPerson());
		graphTypeField.setValue(parameters.getGraphType());
		nameDisplayingField.setValue(parameters.getNameDisplaying());
		
		disableDrawButton();
	}

	public void setSession(Session session) {
		if (session == null) return;
		
		session.addNewTreeListener(searchBox::setTree);
		session.addEditPersonListener(searchBox.getSearchEngine()::refreshOrAdd);
	}
	
	public void clearFields() {
		personName.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearFields();
		
		personBox.getChildren().add(searchBox);
		searchBox.setSelectPerson(this::selectPerson);
		
		drawButton.setOnAction(e -> {
			TreeGraphParameters parameters = getParameters();
			
			if (parameters != null) {
				disableDrawButton();
				Injection.run(drawAction, parameters);
			}
		});
		
		graphTypeBox.getChildren().add(graphTypeField.getNode());
		graphTypeField.setOnChange(v -> this.enableDrawButton());

		personDisplayerBox.getChildren().add(nameDisplayingField.getNode());
		nameDisplayingField.setOnChange(v -> this.enableDrawButton());
	}

	private void selectPerson(Person person) {
		this.person = person;
		personName.setText((person == null) ? "" : person.nameSurname());
		
		searchBox.hideSearch();
		enableDrawButton();
	}
	
	private TreeGraphParameters getParameters() {
		TreeGraphType graphType = graphTypeField.getValue();
		NameDisplayingType nameDisplaying = nameDisplayingField.getValue();
		
		if (graphType == null) return null;
		
		return TreeGraphParameters.builder()
				.person(person)
				.graphType(graphType)
				.nameDisplaying(nameDisplaying)
				.build();
	}

	private void enableDrawButton() {
		drawButton.setDisable(false);
	}

	private void disableDrawButton() {
		drawButton.setDisable(true);
	}
}
