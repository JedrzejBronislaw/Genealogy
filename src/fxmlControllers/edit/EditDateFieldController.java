package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.MyDate;

public class EditDateFieldController implements EditFieldInterface, Initializable {

	@FXML
	private TextField dayField;
	@FXML
	private TextField monthField;
	@FXML
	private TextField yearField;
	
	@Override
	public void setOldValue(String value) {
		MyDate mydate = new MyDate(value);
		
		dayField.setText(mydate.getDay() + "");
		monthField.setText(mydate.getMonth() + "");
		yearField.setText(mydate.getYear() + "");
	}

	@Override
	public String getValue() {
		String day   = dayField.getText();
		String month = monthField.getText();
		String year  = yearField.getText();

		return day + "." + month + "." + year;
	}
	
	public void clear() {
		dayField.setText("");
		monthField.setText("");
		yearField.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
