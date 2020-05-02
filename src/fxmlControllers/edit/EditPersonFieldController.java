package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class EditPersonFieldController implements EditFieldInterface, Initializable {

	@FXML
	private TextField value;
	
	@Override
	public void setOldValue(String valueText) {
		value.setText(valueText);
		value.setPromptText(valueText);
	}
	@Override
	public String getValue() {
		return value.getText();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
