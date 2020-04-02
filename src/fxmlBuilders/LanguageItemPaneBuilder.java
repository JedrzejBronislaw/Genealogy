package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.LanguageItemPaneController;
import javafx.scene.control.Label;
import lang.Languages;
import lombok.Getter;
import lombok.Setter;
import tools.Injection;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class LanguageItemPaneBuilder {

	@Getter
	private Label label;
	
	@Setter
	private Languages language;
	
	@Setter
	private Consumer<Languages> changeLanguage;
	
	public LanguageItemPaneBuilder() {}

	public LanguageItemPaneBuilder(Languages language) {
		this.language = language;
	}
	
	public void build() {
		MyFXMLLoader<LanguageItemPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<LanguageItemPaneController> nac = loader.create("LanguageItemPane.fxml");
		LanguageItemPaneController controller = nac.getController();
		
		label = (Label) nac.getNode();
		
		controller.setLanguage(language.getAbbr());
		controller.setClick(() -> Injection.run(changeLanguage, language));
	}
}
