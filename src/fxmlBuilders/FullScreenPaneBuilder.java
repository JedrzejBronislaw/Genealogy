package fxmlBuilders;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fxmlControllers.FullScreenPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class FullScreenPaneBuilder {

	@Getter
	private Pane pane;

	@Setter
	private Consumer<Boolean> fullScreenAction;
	@Setter
	private Supplier<Boolean> isFullScreen;
	
	public void build() {
		MyFXMLLoader<FullScreenPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<FullScreenPaneController> nac = loader.create("FullScreenPane.fxml");
		FullScreenPaneController controller = nac.getController();
		
		pane = (Pane) nac.getNode();
		
		controller.setFullScreenAction(fullScreenAction);
		controller.setIsFullScreen(isFullScreen);
	}
}
