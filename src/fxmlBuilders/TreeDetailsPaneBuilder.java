package fxmlBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import fxmlControllers.TreeDetailsPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import model.Tree;
import session.Session;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class TreeDetailsPaneBuilder {

	@Getter
	private Pane pane;
	private TreeDetailsPaneController controller;
	
	public void setSession(Session session) {
		if (session == null) return;
		
		session.addNewTreeListener(tree -> {
			setLabelsValue(tree);
		});
	}
	
	public void build() {
		MyFXMLLoader<TreeDetailsPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<TreeDetailsPaneController> nac = loader.create("TreeDetailsPane.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
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
