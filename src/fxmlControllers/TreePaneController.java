package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class TreePaneController implements Initializable{

	@FXML
	private VBox mainBox;
	
	public void addNode(Node node) {
		mainBox.getChildren().add(node);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
