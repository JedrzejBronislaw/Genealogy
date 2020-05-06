package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class EditMLTextFieldController implements EditFieldInterface, Initializable {

	@FXML
	private TextArea value;
	
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
