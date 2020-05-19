package viewFX.treePane.commonSurnames;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import session.Session;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.treePane.commonSurnames.item.CommonSurnamesItemBuilder;

public class CommonSurnamesPaneBuilder extends PaneFXMLBuilder<CommonSurnamesPaneController> {

	@Override
	public String getFxmlFileName() {
		return "CommonSurnamesPane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setSurnamePaneGenerator(this::surnamePaneGenerator);
		clearSurnames();
	}
	
	public void setSession(Session session) {
		if(session == null) return;

		session.addNewTreeListener(tree -> updateSurnames(Arrays.asList(tree.getCommonSurnames())));
		session.addCloseTreeListener(() -> clearSurnames());
	}
	
	private void clearSurnames() {
		controller.clearCommonSurnames();
	}
	
	private void updateSurnames(List<String> surnames) {
		clearSurnames();
		if (surnames != null)
			surnames.forEach(controller::addCommonSurname);		
	}
	
	private Node surnamePaneGenerator(Integer number, String surname) {
		CommonSurnamesItemBuilder builder = new CommonSurnamesItemBuilder();
		builder.setNumber(number).setSurname(surname).build();
		
		return builder.getPane();
	}
}
