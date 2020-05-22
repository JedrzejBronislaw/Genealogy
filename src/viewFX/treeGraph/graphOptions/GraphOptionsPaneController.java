package viewFX.treeGraph.graphOptions;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Person;
import session.Session;
import tools.Injection;
import tools.Tools;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import viewFX.editPerson.fields.SearchBox;
import viewFX.editPerson.fields.enumField.EditEnumFieldBuilder;
import viewFX.editPerson.fields.enumField.EditEnumFieldController;

public class GraphOptionsPaneController implements Initializable {

	@FXML
	private Label personName;

	@FXML
	private Button drawButton;

	@FXML
	private VBox personBox;
	@FXML
	private VBox graphTypeBox;
	
	private SearchBox searchBox = new SearchBox();
	private EditEnumFieldController graphTypeField;
	
	@Setter
	private Consumer<TreeGraphParameters> drawAction;
	
	private Person person = null;
	
	public void setState(TreeGraphParameters parameters) {
		if (parameters == null) return;
		
		selectPerson(parameters.getPerson());
		graphTypeField.setOldValue(parameters.getGraphType().toString());
		
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
		
		graphTypeBox.getChildren().add(createGraphTypeField());
	}

	private void selectPerson(Person person) {
		this.person = person;
		personName.setText((person == null) ? "" : person.nameSurname());
		
		searchBox.hideSearch();
		enableDrawButton();
	}
	
	private Node createGraphTypeField() {
		EditEnumFieldBuilder builder = new EditEnumFieldBuilder();
		builder.setOptions(Tools.getStringValues(TreeGraphType.class));
		builder.build();
		graphTypeField = builder.getController();
		
		return builder.getNode();
	}
	
	private TreeGraphParameters getParameters() {
		String graphType = graphTypeField.getValue();
		
		if (graphType == null || graphType.isEmpty()) return null;
		
		return TreeGraphParameters.builder()
				.person(person)
				.graphType(TreeGraphType.valueOf(graphType))
				.build();
	}

	private void enableDrawButton() {
		drawButton.setDisable(false);
	}

	private void disableDrawButton() {
		drawButton.setDisable(true);
	}

}
