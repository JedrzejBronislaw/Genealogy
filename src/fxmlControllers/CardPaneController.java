package fxmlControllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
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
import model.Person.LifeStatus;
import model.Person.Sex;
import other.PersonDetails;
import tools.Injection;
import tools.SwingRefresher;
import treeGraphs.ClosestTreeGraph;
import treeGraphs.painter.graphics2DPainter.Graphics2DPainter;
import windows.Canvas;

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

	@Setter
	private Consumer<Person> ancestorsTreeAction;
	@Setter
	private Consumer<Person> descendantsTreeAction;
	@Setter
	private Consumer<Person> drawingTreeAction;
	
	@Setter
	private Consumer<Person> graphClickAction;
	
	private Person person;
	private ClosestTreeGraph grafMiniDrzewo = new ClosestTreeGraph();
	SwingNode swingNode;
	
	private Image star, cross, venus, mars;
	
	public void setPerson(Person person) {
		this.person = person;
		Platform.runLater(() -> refreshValues());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ancestorsButton.setOnAction(e -> Injection.run(ancestorsTreeAction, person));
		descendantsButton.setOnAction(e -> Injection.run(descendantsTreeAction, person));
		drawnigButton.setOnAction(e -> Injection.run(drawingTreeAction, person));
		
		swingNode = new SwingNode();
		Canvas miniGraphCanvas = miniTree();
		swingNode.setContent(miniGraphCanvas);
		miniTreePane.setCenter(swingNode);
		
		miniGraphCanvas.setDimensions((width, height) -> {
    		miniTreePane.setPrefHeight(height);
    		miniTreePane.setPrefWidth(width);
		});

		star = loadImage("res/img/star.jpg");
		cross = loadImage("res/img/cross.jpg");
		venus = loadImage("res/img/venus.jpg");
		mars = loadImage("res/img/mars.jpg");
		
		Graphics2DPainter painter = new Graphics2DPainter();
		miniGraphCanvas.setPainter(painter);
		grafMiniDrzewo.setPainter(painter);
	}

	private Image loadImage(String path) {
		Image image = null;
		
		try (FileInputStream input = new FileInputStream(path)) {
			image = new Image(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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

		grafMiniDrzewo.setMainPerson(person);
		SwingRefresher.refreshGraph(swingNode);
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
		if (person.getLifeStatus() == LifeStatus.YES)
			return star;
		if (person.getLifeStatus() == LifeStatus.NO)
			return cross;
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
		
//		grafMiniDrzewo.clear();
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
	
	private Canvas miniTree() {
		Canvas plutnoGrafu = new Canvas();
		plutnoGrafu.setTreeGraph(grafMiniDrzewo);
		grafMiniDrzewo.setPersonClickAction(person -> Injection.run(graphClickAction, person));
		return plutnoGrafu;
	}
}
