package viewFX.mainWindow.fullScreen;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lang.Internationalization;
import lombok.Setter;
import tools.Injection;

public class FullScreenPaneController implements Initializable {

	@FXML
	private Label fullScreenLabel;

	@Setter
	private Consumer<Boolean> fullScreenAction;

	private Supplier<Boolean> isFullScreen;
	private boolean fullScreen = false;
	
	public void setIsFullScreen(Supplier<Boolean> isFullScreen) {
		this.isFullScreen = isFullScreen;
		fullScreen = Injection.get(isFullScreen, fullScreen);
		Platform.runLater(this::setLabelText);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fullScreen = Injection.get(isFullScreen, fullScreen);
		setLabelText();
		
		fullScreenLabel.setOnMouseClicked(e -> {
			Injection.run(fullScreenAction, !fullScreen);	
			fullScreen = Injection.get(isFullScreen, fullScreen);
			setLabelText();
		});
	}
	
	private void setLabelText() {
		fullScreenLabel.setText(fullScreen ?
				Internationalization.get("close_full_screen") :
				Internationalization.get("full_screen"));
	}
}
