package viewFX.editPerson;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import viewFX.editPerson.fields.EditItemInterface;

public class EditPersonLayout {

	@Getter
	private List<EditItemInterface> editItems = new ArrayList<>();
	
	private List<List<Node>> nodes = new ArrayList<>();
	private List<Node> currentColumn;
	
	public EditPersonLayout() {
		newColumn();
	}
	
	public void addItem(EditItemInterface editItem) {
		editItems.add(editItem);
		currentColumn.add(editItem.getPane());
	}

	public void newColumn() {
		currentColumn = new ArrayList<>();
		nodes.add(currentColumn);
	}
	
	public void set(Pane pane) {
		HBox hBox = new HBox();
		hBox.setSpacing(40);
		
		for(List<Node> column : nodes) {
			VBox vBox = new VBox();
			vBox.setSpacing(10);
			vBox.setAlignment(Pos.TOP_RIGHT);
			vBox.getChildren().addAll(column);
			hBox.getChildren().add(vBox);
		}
		
		pane.getChildren().clear();
		pane.getChildren().add(hBox);
	}
}
