package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditItemController implements Initializable {

	@FXML
	private Label label;
	@FXML
	private TextField value;
	
	public void setLabel(String labelText) {
		label.setText(labelText);
	}
	
	public void setOldValue(String valueText) {
		value.setText(valueText);
		value.setPromptText(valueText);
	}

	public String getValue() {
		return value.getText();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
