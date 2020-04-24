package fxmlControllers.edit;

import javafx.scene.layout.Pane;
import model.Person;

public interface EditItemInterface {
	public Pane getPane();
	public void saveTo(Person person);
	public void refresh(Person person);
}
