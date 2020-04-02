package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.LanguagePaneController;
import javafx.scene.layout.Pane;
import lang.Languages;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class LanguagePaneBuilder {

	@Getter
	private Pane pane;
	@Setter
	private Consumer<Languages> changeLanguage;
	
	public void build() {
		MyFXMLLoader<LanguagePaneController> loader = new MyFXMLLoader<>();
		NodeAndController<LanguagePaneController> nac = loader.create("LanguagePane.fxml");
		LanguagePaneController controller = nac.getController();
		
		pane = (Pane) nac.getNode();
		

		controller.setChangeLanguage(changeLanguage);
		controller.addLanguage(Languages.POLISH);
		controller.addLanguage(Languages.ENGLISH);
	}

}
