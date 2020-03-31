package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TreePaneController implements Initializable{

	@FXML
	private StackPane searchBox;
	@FXML
	private HBox topHBox;

	public void setTreeDetailsPane(Pane pane) {
		topHBox.getChildren().add(0, pane);
	}
	
	public void setCommonSurnamePane(Pane pane) {
		topHBox.getChildren().add(1, pane);
	}
	
	public void setSearchPane(Node node) {
		searchBox.getChildren().clear();
		searchBox.getChildren().add(node);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
