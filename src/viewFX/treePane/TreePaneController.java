package viewFX.treePane;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Setter;
import tools.Injection;

public class TreePaneController implements Initializable{

	@FXML
	private StackPane searchBox;
	@FXML
	private HBox topHBox;
	@FXML
	private Button saveTreeButton;
	@FXML
	private Button saveTreeAsButton;
	@FXML
	private Button closeTreeButton;
	@FXML
	private Button newPersonButton;

	@Setter
	private Runnable saveTree;
	@Setter
	private Runnable saveTreeAs;
	@Setter
	private Runnable closeTree;
	@Setter
	private Runnable createNewPerson;

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
		saveTreeButton.setOnAction(e -> Injection.run(saveTree));
		saveTreeAsButton.setOnAction(e -> Injection.run(saveTreeAs));
		closeTreeButton.setOnAction(e -> Injection.run(closeTree));
		newPersonButton.setOnAction(e -> Injection.run(createNewPerson));
	}

}
