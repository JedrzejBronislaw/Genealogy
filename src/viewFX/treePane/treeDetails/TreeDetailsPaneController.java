package viewFX.treePane.treeDetails;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TreeDetailsPaneController implements Initializable {

	@FXML
	private Label lastOpenDate;
	@FXML
	private Label lastModificationDate;
	@FXML
	private Label numOfPersons;
	
	public void set(String lastOpenDate, String lastModificationDate, String numOfPersons) {
		this.lastOpenDate.setText(lastOpenDate);
		this.lastModificationDate.setText(lastModificationDate);
		this.numOfPersons.setText(numOfPersons);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
