package viewFX.card;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Person;
import model.Sex;
import other.PersonDetails;
import treeGraphs.ClosestTreeGraph;
import treeGraphs.painter.service.FXPainterService;
import treeGraphs.painter.service.PainterService;
import utils.Injection;

public class CardPaneController implements Initializable{

	@FXML
	private Label idLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label aliasLabel;
	@FXML
	private Label birthDateLabel;
	@FXML
	private Label birthPlaceLabel;
	@FXML
	private Label baptismParishLabel;
	@FXML
	private Label deathDateLabel;
	@FXML
	private Label deathPlaceLabel;
	@FXML
	private Label burialPlaceLabel;

	@FXML
	private ImageView sexImg;
	@FXML
	private ImageView liveImg;

	@FXML
	private Label ageLabel;
	
	@FXML
	private BorderPane miniTreePane;
	
	@FXML
	private Button ancestorsButton;
	@FXML
	private Button descendantsButton;
	@FXML
	private Button drawnigButton;
	
	@FXML
	private VBox marriagesBox;
	
	@FXML
	private TextArea commentsArea;
	@FXML
	private TextArea contactArea;

	@FXML
	private Button editButton;
	
	
	@Setter
	private Consumer<Person> ancestorsTreeAction;
	@Setter
	private Consumer<Person> descendantsTreeAction;
	@Setter
	private Consumer<Person> drawingTreeAction;
	
	@Setter
	private Consumer<Person> editAction;
	
	@Setter
	private Consumer<Person> graphClickAction;
	
	private Person person;
	private PainterService painterService = new FXPainterService();
	
	private Image star, cross, venus, mars;
	
	public void setPerson(Person person) {
		this.person = person;
		refresh();
	}
	public void refresh() {
		Platform.runLater(() -> refreshValues());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ancestorsButton.setOnAction(e -> Injection.run(ancestorsTreeAction, person));
		descendantsButton.setOnAction(e -> Injection.run(descendantsTreeAction, person));
		drawnigButton.setOnAction(e -> Injection.run(drawingTreeAction, person));
		editButton.setOnAction(e -> Injection.run(editAction, person));

		miniTreePane.setCenter(painterService.getCanvas(miniTreePane));
		ClosestTreeGraph graph = new ClosestTreeGraph();
		graph.setPersonDoubleClickAction(person -> Injection.run(graphClickAction, person));
		painterService.setGraph(graph);

		star = loadImage("img/star.jpg");
		cross = loadImage("img/cross.jpg");
		venus = loadImage("img/venus.jpg");
		mars = loadImage("img/mars.jpg");
	}

	private Image loadImage(String path) {
		Image image = null;
		try {
			image = new Image(path);
		} catch (IllegalArgumentException e) {}
		
		return image;
	}

	private void refreshValues() {
		clearValues();
		if(person == null) return;
		
		firstNameLabel.setText(person.getFirstName());
		lastNameLabel.setText(person.getLastName());
		aliasLabel.setText(person.getAlias());
		if(person.getBirthDate() != null)
			birthDateLabel.setText(person.getBirthDate().toString());
		birthPlaceLabel.setText(person.getBirthPlace());
		baptismParishLabel.setText(person.getBaptismParish());
		if(person.getDeathDate() != null)
			deathDateLabel.setText(person.getDeathDate().toString());
		deathPlaceLabel.setText(person.getDeathPlace());
		burialPlaceLabel.setText(person.getBurialPlace());

		sexImg.setImage(getSexSymbol());
		liveImg.setImage(getLiveSymbol());

		if(person.getBirthDate() != null)
			ageLabel.setText(PersonDetails.wiekStr(person));
		
		for(int i=0; i<person.numberOfMarriages(); i++) {
			marriagesBox.getChildren().add(generateMarriageLabel(i));
		}
		
		commentsArea.setText(person.getComments());
		contactArea.setText(person.getContact());

		painterService.getGraph().setMainPerson(person);
		painterService.refreshGraph();
	}

	private Image getSexSymbol() {
		if (person.getSex() == Sex.WOMAN)
			return venus;
		if (person.getSex() == Sex.MAN)
			return mars;
		else
			return null;
	}
	
	private Image getLiveSymbol() {
		if (person.isAlive()) return star;
		if (person.isDead())  return cross;
		else
			return null;
	}

	private void clearValues() {
		
		firstNameLabel.setText("");
		lastNameLabel.setText("");
		aliasLabel.setText("");
		birthDateLabel.setText("");
		birthPlaceLabel.setText("");
		baptismParishLabel.setText("");
		deathDateLabel.setText("");
		deathPlaceLabel.setText("");
		burialPlaceLabel.setText("");

		ageLabel.setText("");
		
		marriagesBox.getChildren().clear();
		
		commentsArea.clear();
		contactArea.clear();
	}

	private Node generateMarriageLabel(int marriageNum) {
		StringBuffer output = new StringBuffer();
		StringBuffer details = new StringBuffer();
		
		String name = person.getSpouse(marriageNum).nameSurname();
		String date = person.getMarriage(marriageNum).getDate();
		String place = person.getMarriage(marriageNum).getPlace();

		if(date != null && !date.isBlank()) {
			details.append(date);
			details.append(" ");
		}
		if(place != null && !place.isBlank())
			details.append(place);
		
		output.append(name);
		if (details.length() > 0) {
			output.append(" (");
			output.append(details.toString().trim());
			output.append(")");
		}
		
		return new Label(output.toString());
	}
}
