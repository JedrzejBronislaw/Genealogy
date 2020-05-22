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
import session.Session;
import tools.Injection;
import treeGraphs.TreeGraphParameters;
import viewFX.editPerson.fields.SearchBox;

public class GraphOptionsPaneController implements Initializable {

	@FXML
	private Label personName;

	@FXML
	private Button drawButton;

	@FXML
	private VBox personBox;
	
	private SearchBox searchBox = new SearchBox();
	
	@Setter
	private Consumer<TreeGraphParameters> drawAction;
	
	private Person person = null;
	
	public void setState(TreeGraphParameters parameters) {
		selectPerson(parameters.getPerson());
		drawButton.setDisable(true);
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
			drawButton.setDisable(true);
			Injection.run(drawAction, getParameters());
		});
	}

	private void selectPerson(Person person) {
		this.person = person;
		personName.setText(person.nameSurname());
		
		searchBox.hideSearch();
		drawButton.setDisable(false);
	}
	
	private TreeGraphParameters getParameters() {
		TreeGraphParameters parameters = TreeGraphParameters.builder()
				.person(person)
				.build();
		
		return parameters;
	}

}
