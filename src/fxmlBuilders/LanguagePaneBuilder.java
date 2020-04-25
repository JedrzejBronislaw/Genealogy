package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.LanguagePaneController;
import lang.Languages;
import lombok.Setter;

public class LanguagePaneBuilder extends PaneFXMLBuilder<LanguagePaneController> {

	@Setter
	private Consumer<Languages> changeLanguage;

	
	@Override
	public String getFxmlFileName() {
		// TODO Auto-generated method stub
		return "LanguagePane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setChangeLanguage(changeLanguage);
		controller.addLanguage(Languages.POLISH);
		controller.addLanguage(Languages.ENGLISH);
	}

}
