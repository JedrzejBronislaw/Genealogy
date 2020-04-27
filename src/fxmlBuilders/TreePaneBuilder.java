package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.TreePaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import searchEngine.SearchEngine;
import session.Session;

public class TreePaneBuilder extends PaneFXMLBuilder<TreePaneController> {
	
	@Setter
	private Session session;
	
	@Setter
	private Consumer<Person> selectPerson;
	@Setter
	private Runnable closeTree;
	
	@Getter
	private Runnable settingSearchFocus;


	@Override
	public String getFxmlFileName() {
		return "TreePane.fxml";
	}

	@Override
	public void afterBuild() {
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
		
		settingSearchFocus = () -> builder.getController().requestFocus();
		
		return builder.getPane();
	}
}
