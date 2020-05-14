package fxmlControllers.edit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Setter;
import model.Marriage;
import model.Person;
import model.Person.Sex;
import tools.Injection;

public class EditMarriageItemFieldController implements Initializable {

	@FXML
	private Label spouseLabel;
	@FXML
	private TextField dateField;
	@FXML
	private TextField placeField;
	
	@FXML
	private Label xLabel;
	
	@Setter
	private Runnable delPressEvent = null;
	
	@Setter
	private Sex personSex = Sex.UNKNOWN;
	
	private Marriage marriage = null;
	
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
			dateField.setText(date);
		
		if (place != null && !place.isEmpty())
			placeField.setText(place);
	}
	public Marriage updateValues() {
		marriage.setDate(dateField.getText());
		marriage.setPlace(placeField.getText());
		
		return marriage;
	}
	
	private void clearField() {
		spouseLabel.setText("");
		dateField.setText("");
		placeField.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		xLabel.setOnMouseClicked(e -> Injection.run(delPressEvent));
		xLabel.setOnMouseEntered(e -> setFontWeight(xLabel,FontWeight.EXTRA_BOLD));
		xLabel.setOnMouseExited(e -> setFontWeight(xLabel, FontWeight.NORMAL));
	}
	
	private void setFontWeight(Label label, FontWeight weight) {
		Font font = label.getFont();
		String family = font.getFamily();
		double size = font.getSize();
		
		label.setFont(Font.font(family, weight, size));
	}
}
