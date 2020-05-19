package viewFX.builders;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public abstract class PaneFXMLBuilder<T extends Initializable> extends FXMLBuilder<T> {
	
	public Pane getPane() {
		return (Pane) getNode();
	}
}
