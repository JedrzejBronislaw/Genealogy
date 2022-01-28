package viewFX.editPerson.fields.marriages.item;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Setter;
import model.Marriage;
import model.Person;
import model.Sex;
import utils.Injection;
import viewFX.editPerson.fields.date.EditDateFieldBuilder;
import viewFX.editPerson.fields.date.EditDateFieldController;

public class EditMarriageItemFieldController implements Initializable {

	@FXML
	private Label spouseLabel;
	@FXML
	private TextField placeField;
	@FXML
	private VBox detailsBox;
	
	@FXML
	private Label xLabel;
	
	@Setter
	private Runnable delPressEvent = null;
	
	@Setter
	private Sex personSex = Sex.UNKNOWN;
	
	private Marriage marriage = null;
	private EditDateFieldController dateController;
	
	public void setMarriage(Marriage marriage) {
		clearField();
		this.marriage = marriage;
		
		Person spouse = null;
		String date  = marriage.getDate();
		String place = marriage.getPlace();
		
		if (personSex == Sex.MAN)
			spouse = marriage.getWife();
		if (personSex == Sex.WOMAN)
			spouse = marriage.getHusband();
		
			
		if (spouse != null)
			spouseLabel.setText(spouse.nameSurname());

		if (date != null && !date.isEmpty())
			dateController.setOldValue(date);
		
		if (place != null && !place.isEmpty())
			placeField.setText(place);
	}
	public Marriage updateValues() {
		marriage.setDate(dateController.getValue());
		marriage.setPlace(placeField.getText());
		
		return marriage;
	}
	
	private void clearField() {
		spouseLabel.setText("");
		dateController.clear();
		placeField.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		xLabel.setOnMouseClicked(e -> Injection.run(delPressEvent));
		xLabel.setOnMouseEntered(e -> setFontWeight(xLabel,FontWeight.EXTRA_BOLD));
		xLabel.setOnMouseExited(e -> setFontWeight(xLabel, FontWeight.NORMAL));
		
		detailsBox.getChildren().add(0, createDateField());
	}
	
	private Node createDateField() {
		EditDateFieldBuilder builder = new EditDateFieldBuilder();
		builder.build();
		
		dateController = builder.getController();
		
		return builder.getRegion();
	}
	
	private void setFontWeight(Label label, FontWeight weight) {
		Font font = label.getFont();
		String family = font.getFamily();
		double size = font.getSize();
		
		label.setFont(Font.font(family, weight, size));
	}
}
