package viewFX.card;

import java.util.function.Consumer;

import lombok.Setter;
import model.Person;
import viewFX.builders.PaneFXMLBuilder;

public class CardPaneBuilder extends PaneFXMLBuilder<CardPaneController> {

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
	
	@Override
	protected String getFxmlFileName() {
		return "CardPane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setAncestorsTreeAction(showAncestorsTree);
		controller.setDescendantsTreeAction(showDescendantsTree);
		controller.setDrawingTreeAction(showDrawingTree);
		
		controller.setEditAction(editAction);
		
		controller.setGraphClickAction(graphClickAction);
	}

}
