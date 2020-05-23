package viewFX.editPerson.fields.enumField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import lang.Internationalization;
import lombok.Setter;
import tools.Injection;
import viewFX.editPerson.fields.EditFieldInterface;

public class EditEnumFieldController implements EditFieldInterface, Initializable {

	@FXML
	private VBox box;
	
	@Setter
	private Consumer<String> onChange;
	
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
		radio.setOnAction(e -> {
			this.value = value;
			Injection.run(onChange, value);
		});
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
