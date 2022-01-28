package viewFX.mainWindow.language.item;

import java.util.function.Consumer;

import javafx.scene.control.Label;
import lang.Languages;
import lombok.Setter;
import utils.Injection;
import viewFX.builders.FXMLBuilder;

public class LanguageItemPaneBuilder extends FXMLBuilder<LanguageItemPaneController> {

	public Label getLabel() {
		return (Label) getNode();
	}
	
	@Setter
	private Languages language;
	
	@Setter
	private Consumer<Languages> changeLanguage;
	
	public LanguageItemPaneBuilder() {}

	public LanguageItemPaneBuilder(Languages language) {
		this.language = language;
	}
	

	@Override
	protected String getFxmlFileName() {
		return "LanguageItemPane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setLanguage(language.getAbbr());
		controller.setClick(() -> Injection.run(changeLanguage, language));
	}
}
