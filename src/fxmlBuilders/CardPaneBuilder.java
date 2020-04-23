package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.CardPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class CardPaneBuilder {

	@Getter
	private Pane pane;
	@Getter
	private CardPaneController controller;
	
	@Setter
	private Consumer<Person> showAncestorsTree;
	@Setter
	private Consumer<Person> showDescendantsTree;
	@Setter
	private Consumer<Person> showDrawingTree;

	@Setter
	private Consumer<Person> editAction;
	
	@Setter
	private Consumer<Person> graphClickAction;
	
	
	public void build() {
		MyFXMLLoader<CardPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<CardPaneController> nac = loader.create("CardPane.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
	
		controller.setAncestorsTreeAction(showAncestorsTree);
		controller.setDescendantsTreeAction(showDescendantsTree);
		controller.setDrawingTreeAction(showDrawingTree);

		controller.setEditAction(editAction);
		
		controller.setGraphClickAction(graphClickAction);
	}

}
