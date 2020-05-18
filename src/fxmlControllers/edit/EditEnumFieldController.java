package fxmlControllers.edit;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import lang.Internationalization;

public class EditEnumFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	
	private String value = "";
	
	private Map<String, RadioButton> options = new HashMap<String, RadioButton>();
	private ToggleGroup radioGroup = new ToggleGroup();

	public void addOption(String optionName) {
		RadioButton option = generateOption(optionName);
		options.put(optionName, option);
		box.getChildren().add(option);
	}
	
	private RadioButton generateOption(String value) {
		String name = Internationalization.get(value);
		RadioButton radio = new RadioButton(name);
		
		radio.setToggleGroup(radioGroup);
		radio.setOnAction(e -> this.value = value);
		return radio;
	}
	
	@Override
	public void setOldValue(String value) {
		this.value = value;
		options.get(value).setSelected(true);
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
