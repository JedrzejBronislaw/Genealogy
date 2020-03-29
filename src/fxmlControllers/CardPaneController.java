package fxmlControllers;

import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Person;
import other.PersonDetails;
import tools.Injection;
import tools.SwingRefresher;
import treeGraphs.ClosestTreeGraph;
import visualComponents.Group;
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
	
	private Person person;
	private ClosestTreeGraph grafMiniDrzewo = new ClosestTreeGraph();
	SwingNode swingNode;
	
	public void setPerson(Person person) {
		this.person = person;
		refreshValues();
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
		
		miniGraphCanvas.setWymiary((sz, wys) -> {
    		miniTreePane.setPrefHeight(wys);
    		miniTreePane.setPrefWidth(sz);
		});
	}

	private void refreshValues() {
		clearValues();
		if(person == null) return;
		
		firstNameLabel.setText(person.getImie());
		lastNameLabel.setText(person.getNazwisko());
		aliasLabel.setText(person.getPseudonim());
		birthDateLabel.setText(person.getDataUrodzenia().toString());
		birthPlaceLabel.setText(person.getMiejsceUrodzenia());
		baptismParishLabel.setText(person.getParafiaChrztu());
		deathDateLabel.setText(person.getDataSmierci().toString());
		deathPlaceLabel.setText(person.getMiejsceSmierci());
		burialPlaceLabel.setText(person.getMiejscePochowku());

		ageLabel.setText(PersonDetails.wiekStr(person));
		
		for(int i=0; i<person.liczbaMalzenstw(); i++) {
			marriagesBox.getChildren().add(generateMarriageLabel(i));
		}
		
		commentsArea.setText(person.getUwagi());
		contactArea.setText(person.getKontakt());

		grafMiniDrzewo.setOsobaGlowna(person);
		SwingRefresher.refreshGraph(swingNode);
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
		
		String name = person.getMalzonek(marriageNum).imieNazwisko();
		String date = person.getMalzenstwo(marriageNum).getData();
		String place = person.getMalzenstwo(marriageNum).getMiejsce();

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
		plutnoGrafu.setGrafDrzewa(grafMiniDrzewo);
		return plutnoGrafu;
	}
}
