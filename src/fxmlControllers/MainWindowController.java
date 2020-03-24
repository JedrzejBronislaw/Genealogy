package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class MainWindowController implements Initializable {

	@FXML
	private HBox mainBox;
	
	public void addNode(Node node) {
		mainBox.getChildren().add(node);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public Object addCommonSurname(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
