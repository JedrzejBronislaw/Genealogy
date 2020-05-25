package viewFX.treePane.commonSurnames.item;

import viewFX.builders.PaneFXMLBuilder;

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
	protected String getFxmlFileName() {
		return "CommonSurnamesItem.fxml";
	}
	@Override
	protected void afterBuild() {
		controller.set(number, surname);
	}
}
