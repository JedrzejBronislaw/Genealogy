package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Getter;

public class EditItemController implements Initializable {

	public static class EditField{
		private Node field;
		
		private EditFieldInterface controller;
		
		public EditField(Node field, EditFieldInterface controller) {
			this.field = field;
			this.controller = controller;
		}
	}
	
	@FXML
	private Label label;
	@FXML
	private HBox box;
	
	@Getter
	private EditField field;
	
	public void setEditField(EditField editField){
		this.field = editField;
		box.getChildren().clear();
		box.getChildren().add(label);
		box.getChildren().add(editField.field);
	}
	
	public void setLabel(String labelText) {
		label.setText(labelText);
	}
	
	public void setOldValue(String valueText) {
		field.controller.setOldValue(valueText);
	}

	public String getValue() {
		return field.controller.getValue();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
