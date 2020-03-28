package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import lombok.Setter;

public class MainWindowController implements Initializable {

	public enum Views{ChooseFile, Tree, Card, Graph}
	
	@FXML
	private ScrollPane mainPane;
	
	@Setter
	private Pane chooseFilePane;
	@Setter
	private Pane cardPane;
	@Setter
	private Pane treePane;
	@Setter
	private Pane graphPane;
	
	
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

		default:
			selected = null;
		}
		
		if(selected != null)
			Platform.runLater(() -> mainPane.setContent(selected));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}


}
