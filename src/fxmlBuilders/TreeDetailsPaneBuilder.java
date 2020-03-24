package fxmlBuilders;

import java.text.SimpleDateFormat;

import fxmlControllers.TreeDetailsPaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Tree;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class TreeDetailsPaneBuilder {

	@Getter
	private Pane pane;
	private TreeDetailsPaneController controller;
	
	@Setter
	private Tree tree;
	
	public void build() {
		MyFXMLLoader<TreeDetailsPaneController> loader = new MyFXMLLoader<>();
		NodeAndController<TreeDetailsPaneController> nac = loader.create("TreeDetailsPane.fxml");
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
		setLabelsValue();
	}
	
	private void setLabelsValue() {
		if(tree == null)
			return;

		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
		
		String lastOpenDate = sdf.format(tree.getOstatnieOtwarcie());
		String lastModificationDate = sdf.format(tree.getOstatniaZmiana());
		String numOfPersons = Integer.toString(tree.getLiczbaOsob());

		controller.set(
				lastOpenDate,
				lastModificationDate,
				numOfPersons);
	}
}
