package viewFX.builders;

import javafx.fxml.Initializable;
import javafx.scene.layout.Region;

public abstract class RegionFXMLBuilder<T extends Initializable> extends FXMLBuilder<T> {
	
	public Region getRegion() {
		return (Region) getNode();
	}
}
