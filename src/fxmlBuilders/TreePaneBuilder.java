package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.TreePaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import searchEngine.SearchEngine;
import session.Session;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class TreePaneBuilder {

	@Getter
	private Pane pane;
	private TreePaneController controller;
	
	@Setter
	private Session session;
	
	@Setter
	private Consumer<Person> selectPerson;
	@Setter
	private Runnable closeTree;
	
	
	public void build() {
		MyFXMLLoader<TreePaneController> loader = new MyFXMLLoader<>();
		NodeAndController<TreePaneController> nac = loader.create("TreePane.fxml");
		
		pane = (Pane) nac.getNode();
		controller = nac.getController();

		controller.setTreeDetailsPane(generateTreeDetailsPane());
		controller.setCommonSurnamePane(generateCommonNamePane());
		controller.setSearchPane(generateSearchPane());
		
		controller.setCloseTree(closeTree);
	}
	
	private Pane generateTreeDetailsPane() {
		TreeDetailsPaneBuilder builder = new TreeDetailsPaneBuilder();
		builder.setSession(session);
		builder.build();
		
		return builder.getPane();
	}


	private Pane generateCommonNamePane() {
		CommonSurnamesPaneBuilder builder = new CommonSurnamesPaneBuilder();
		builder.setSession(session);
		builder.build();
		
		return builder.getPane();
	}
	
	private Pane generateSearchPane() {
		SearchViewBuilder builder = new SearchViewBuilder();
		SearchEngine searchEngine = new SearchEngine();
		builder.setChooseAction(selectPerson);
		builder.setSearchEngine(searchEngine);
		
		builder.build();
		
		if (session != null) {
			session.addNewTreeListener(tree -> searchEngine.setTree(tree));
			session.addCloseTreeListener(() -> {
				searchEngine.forgetTree();
				builder.getController().clearFields();
			});
			session.addEditPersonListener(person -> {
				searchEngine.refresh(person);
			});
		}
		
		
		return builder.getPane();
	}
}
