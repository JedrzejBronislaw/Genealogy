package fxmlBuilders;

import fxmlControllers.CardPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class CardPaneBuilder {

	@Getter
	private Pane pane;
	@Getter
	private CardPaneController controller;
	
	
	public void build() {
		MyFXMLLoader<CardPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<CardPaneController> nac = loader.create("CardPane.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
	
		controller.setAncestorsTreeAction(person -> System.out.println("Draw ancestor tree for " + person.imieNazwisko()));
		controller.setDescendantsTreeAction(person -> System.out.println("Draw descendants tree for " + person.imieNazwisko()));
		controller.setDrawingTreeAction(person -> System.out.println("Draw drawing descendants tree for " + person.imieNazwisko()));
	}

}
