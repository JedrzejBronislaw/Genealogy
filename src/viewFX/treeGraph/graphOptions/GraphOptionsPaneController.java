package viewFX.treeGraph.graphOptions;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Person;
import session.Session;
import tools.Injection;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import treeGraphs.painter.PainterServiceType;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;
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
	@FXML
	private VBox painterBox;
	@FXML
	private CheckBox markersBox;
	
	private SearchBox searchBox = new SearchBox();
	private EnumField<TreeGraphType> graphTypeField = new EnumField<>(TreeGraphType.ancestors);
	private EnumField<NameDisplayerType> nameDisplayerField = new EnumField<>(NameDisplayerType.onlyName);
	private EnumField<PainterServiceType> painterField = new EnumField<>(PainterServiceType.FX);
	
	@Setter
	private Consumer<TreeGraphParameters> drawAction;
	
	private Person person = null;
	
	public void setState(TreeGraphParameters parameters) {
		if (parameters == null) return;
		
		selectPerson(parameters.getPerson());
		graphTypeField.setValue(parameters.getGraphType());
		nameDisplayerField.setValue(parameters.getNameDisplayerType());
		painterField.setValue(parameters.getPainterType());
		markersBox.setSelected(parameters.isMarkers());
		
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

		personDisplayerBox.getChildren().add(nameDisplayerField.getNode());
		nameDisplayerField.setOnChange(v -> this.enableDrawButton());

		painterBox.getChildren().add(painterField.getNode());
		painterField.setOnChange(v -> this.enableDrawButton());
		
		markersBox.setOnAction(v -> this.enableDrawButton());
	}

	private void selectPerson(Person person) {
		this.person = person;
		personName.setText((person == null) ? "" : person.nameSurname());
		
		searchBox.hideSearch();
		enableDrawButton();
	}
	
	private TreeGraphParameters getParameters() {
		TreeGraphType graphType = graphTypeField.getValue();
		NameDisplayerType nameDisplayerType = nameDisplayerField.getValue();
		PainterServiceType painterType = painterField.getValue();
		boolean markers = markersBox.isSelected();
		
		if (graphType == null) return null;
		
		return TreeGraphParameters.builder()
				.person(person)
				.graphType(graphType)
				.nameDisplayerType(nameDisplayerType)
				.painterType(painterType)
				.markers(markers)
				.build();
	}

	private void enableDrawButton() {
		drawButton.setDisable(false);
	}

	private void disableDrawButton() {
		drawButton.setDisable(true);
	}
}
