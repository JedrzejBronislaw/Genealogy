package viewFX.mainWindow.fullScreen;

import java.util.function.Consumer;
import java.util.function.Supplier;

import lombok.Setter;
import viewFX.builders.PaneFXMLBuilder;

public class FullScreenPaneBuilder extends PaneFXMLBuilder<FullScreenPaneController> {

	@Setter
	private Consumer<Boolean> fullScreenAction;
	@Setter
	private Supplier<Boolean> isFullScreen;
	

	@Override
	public String getFxmlFileName() {
		return "FullScreenPane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setFullScreenAction(fullScreenAction);
		controller.setIsFullScreen(isFullScreen);
	}
}
