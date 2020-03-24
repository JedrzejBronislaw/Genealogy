package fxmlBuilders;

import fxmlControllers.CommonSurnamesItemController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class CommonSurnamesItemBuilder {

	@Getter
	private Pane pane;
	
	private int number;
	private String surname;

	public CommonSurnamesItemBuilder setNumber(int number) {
		this.number = number;
		return this;
	}
	public CommonSurnamesItemBuilder setSurname(String surname) {
		this.surname = surname;
		return this;
	}
	
	public void build() {
		MyFXMLLoader<CommonSurnamesItemController> loader = new MyFXMLLoader<>();
		NodeAndController<CommonSurnamesItemController> nac = loader.create("CommonSurnamesItem.fxml");
		
		pane = (Pane) nac.getNode();

		nac.getController().set(number, surname);
	}
}
