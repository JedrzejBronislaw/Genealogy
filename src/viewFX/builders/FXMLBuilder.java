package viewFX.builders;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import lombok.Getter;
import utils.MyFXMLLoader;
import utils.MyFXMLLoader.NodeAndController;

public abstract class FXMLBuilder<T extends Initializable> {
	
	@Getter
	private Node node;
	@Getter
	protected T controller;
	
	public void build(){
		MyFXMLLoader<T> loader = new MyFXMLLoader<>();
		NodeAndController<T> nac = loader.create(getFxmlFileName());
		
		controller = nac.getController();
		node = nac.getNode();
		
		afterBuild();
	}

	protected abstract String getFxmlFileName();
	protected abstract void afterBuild();
}
