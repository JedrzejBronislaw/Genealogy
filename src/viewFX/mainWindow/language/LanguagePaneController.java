package viewFX.mainWindow.language;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import lang.Languages;
import lombok.Setter;
import viewFX.mainWindow.language.item.LanguageItemPaneBuilder;

public class LanguagePaneController implements Initializable {

	@FXML
	private HBox mainBox;
	@Setter
	private Consumer<Languages> changeLanguage;
	
	public void addLanguage(Languages lang) {
		LanguageItemPaneBuilder builder = new LanguageItemPaneBuilder(lang);
		builder.setChangeLanguage(changeLanguage);
		builder.build();

//		Node langItem = ;
//		langItem.setOnMouseClicked(e -> Injection.run(changeLanguage, lang));
		mainBox.getChildren().add(builder.getLabel());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
