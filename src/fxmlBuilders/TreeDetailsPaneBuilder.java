package fxmlBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import fxmlControllers.TreeDetailsPaneController;
import model.Tree;
import session.Session;

public class TreeDetailsPaneBuilder extends PaneFXMLBuilder<TreeDetailsPaneController> {
	
	public void setSession(Session session) {
		if (session == null) return;
		
		session.addNewTreeListener(tree -> {
			setLabelsValue(tree);
		});
	}

	@Override
	public String getFxmlFileName() {
		return "TreeDetailsPane.fxml";
	}

	@Override
	public void afterBuild() {
		clearLabelsValue();
	}
	
	private void clearLabelsValue() {
		controller.set("", "", "");
	}
	
	private void setLabelsValue(Tree tree) {
		if(tree == null) return;

		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
		
		final Date lastOpen = tree.getLastOpen();
		final Date lastModi = tree.getLastModification();
		
		String lastOpenDate = (lastOpen != null) ? sdf.format(lastOpen) : "";
		String lastModificationDate = (lastModi != null) ? sdf.format(lastModi) : "";
		String numOfPersons = Integer.toString(tree.getNumberOfPersons());

		controller.set(
				lastOpenDate,
				lastModificationDate,
				numOfPersons);
	}
}
