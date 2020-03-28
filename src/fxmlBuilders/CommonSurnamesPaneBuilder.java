package fxmlBuilders;

import java.util.Arrays;
import java.util.List;

import fxmlBuilders.session.Session;
import fxmlControllers.CommonSurnamesPaneController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class CommonSurnamesPaneBuilder {

	@Getter
	private Pane pane;
	private CommonSurnamesPaneController controller;

	public void setSession(Session session) {
		session.addNewTreeListener(tree -> {
			updateSurnames(Arrays.asList(tree.getGlowneNazwiska()));
		});
	}
	
	public void build() {
		MyFXMLLoader<CommonSurnamesPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<CommonSurnamesPaneController> nac = loader.create("CommonSurnamesPane.fxml");
		
		pane = (Pane) nac.getNode();
		controller = nac.getController();
		
		controller.setSurnamePaneGenerator(this::surnamePaneGenerator);
		clearSurnames();
	}

	private void clearSurnames() {
		controller.clearCommonSurnames();
	}
	
	private void updateSurnames(List<String> surnames) {
		clearSurnames();
		if (surnames != null)
			surnames.forEach(controller::addCommonSurname);		
	}
	
	private Node surnamePaneGenerator(Integer number, String surname) {
		CommonSurnamesItemBuilder builder = new CommonSurnamesItemBuilder();
		builder.setNumber(number).setSurname(surname).build();
		
		return builder.getPane();
	}
}
