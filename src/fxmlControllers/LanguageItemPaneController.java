package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Setter;
import tools.Injection;

public class LanguageItemPaneController implements Initializable {
	
	@FXML
	private Label abbrLabel;
	@Setter
	private Runnable click;

	public void setLanguage(String lang) {
		abbrLabel.setText(lang);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		abbrLabel.setOnMouseClicked(e -> Injection.run(click));
	}

		
}
