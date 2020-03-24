package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CommonSurnamesItemController implements Initializable {

	@FXML
	private Label number;
	@FXML
	private Label surname;
	
	public void set(int number, String surname) {
		this.number.setText(Integer.toString(number));
		this.surname.setText(surname);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
