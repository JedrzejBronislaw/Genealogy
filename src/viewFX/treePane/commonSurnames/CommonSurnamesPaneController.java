package viewFX.treePane.commonSurnames;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class CommonSurnamesPaneController implements Initializable {

	@FXML
	private VBox surnamesBox;

	@Setter
	private BiFunction<Integer, String, Node> surnamePaneGenerator;
	
	int numOfSurnames = 0;
	
	public void addCommonSurname(String name) {
		Node surnamePane;
		
		if(surnamePaneGenerator != null) {
			surnamePane = surnamePaneGenerator.apply(++numOfSurnames, name);
			surnamesBox.getChildren().add(surnamePane);
		}
	}

	public void clearCommonSurnames() {
		surnamesBox.getChildren().clear();
		numOfSurnames = 0;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
