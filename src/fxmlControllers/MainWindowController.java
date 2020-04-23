package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Setter;

public class MainWindowController implements Initializable {

	public enum Views{ChooseFile, Tree, Card, Graph, EditPerson}
	
	@FXML
	private Label titleLabel;
	@FXML
	private StackPane mainPane;
	@FXML
	private StackPane topPane;
	
	@Setter
	private Pane chooseFilePane;
	@Setter
	private Pane cardPane;
	@Setter
	private Pane treePane;
	@Setter
	private Pane graphPane;
	@Setter
	private Pane editPersonPane;
	
	private Views currentView;
	
	public void showView(Views view) {
		Pane selected;
		
		switch (view) {
		case ChooseFile:
			selected = chooseFilePane;
			break;
		case Tree:
			selected = treePane;
			break;
		case Card:
			selected = cardPane;
			break;
		case Graph:
			selected = graphPane;
			break;
		case EditPerson:
			selected = editPersonPane;
			break;

		default:
			selected = null;
		}
		
		if(selected != null && view != currentView) {
			currentView = view;
			Platform.runLater(() -> {
				mainPane.getChildren().clear();
				mainPane.getChildren().add(selected);
			});
		}
	}

	public void setLanguagePane(Pane pane) {
		topPane.getChildren().add(pane);
	}

	public void setFullScreenPane(Pane pane) {
		topPane.getChildren().add(pane);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		titleLabel.setOnMouseClicked(e -> {
			if (currentView != null && currentView != Views.ChooseFile)
				showView(Views.Tree);
		});
	}
}
