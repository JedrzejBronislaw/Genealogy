package fxmlBuilders;

import java.util.List;

import fxmlControllers.CommonSurnamesPaneController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class CommonSurnamesPaneBuilder {

	@Getter
	private Pane pane;

	@Setter
	private List<String> surnames;
	
	
	public void build() {
		MyFXMLLoader<CommonSurnamesPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<CommonSurnamesPaneController> nac = loader.create("CommonSurnamesPane.fxml");
		
		pane = (Pane) nac.getNode();

		nac.getController().setSurnamePaneGenerator(this::surnamePaneGenerator);
		surnames.forEach(name -> nac.getController().addCommonSurname(name));
	}
	
	private Node surnamePaneGenerator(Integer number, String surname) {
		CommonSurnamesItemBuilder builder = new CommonSurnamesItemBuilder();
		builder.setNumber(number).setSurname(surname).build();
		
		return builder.getPane();
	}
}
