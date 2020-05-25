package viewFX.mainWindow.language;

import java.util.function.Consumer;

import lang.Languages;
import lombok.Setter;
import viewFX.builders.PaneFXMLBuilder;

public class LanguagePaneBuilder extends PaneFXMLBuilder<LanguagePaneController> {

	@Setter
	private Consumer<Languages> changeLanguage;

	
	@Override
	protected String getFxmlFileName() {
		return "LanguagePane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setChangeLanguage(changeLanguage);
		controller.addLanguage(Languages.POLISH);
		controller.addLanguage(Languages.ENGLISH);
	}

}
