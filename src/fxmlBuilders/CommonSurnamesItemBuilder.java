package fxmlBuilders;

import fxmlControllers.CommonSurnamesItemController;

public class CommonSurnamesItemBuilder extends PaneFXMLBuilder<CommonSurnamesItemController> {
	
	private int number;
	private String surname;

	public CommonSurnamesItemBuilder setNumber(int number) {
		this.number = number;
		return this;
	}
	public CommonSurnamesItemBuilder setSurname(String surname) {
		this.surname = surname;
		return this;
	}
	
	@Override
	public String getFxmlFileName() {
		return "CommonSurnamesItem.fxml";
	}
	@Override
	public void afterBuild() {
		controller.set(number, surname);
	}
}
