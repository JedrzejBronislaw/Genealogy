package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.LanguageItemPaneController;
import javafx.scene.control.Label;
import lang.Languages;
import lombok.Setter;
import tools.Injection;

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
	public String getFxmlFileName() {
		return "LanguageItemPane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setLanguage(language.getAbbr());
		controller.setClick(() -> Injection.run(changeLanguage, language));
	}
}
