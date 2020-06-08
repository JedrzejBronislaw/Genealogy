package viewFX.treePane;

import java.util.function.Consumer;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import searchEngine.SearchEngine;
import session.Session;
import viewFX.builders.PaneFXMLBuilder;
import viewFX.search.SearchViewBuilder;
import viewFX.treePane.commonSurnames.CommonSurnamesPaneBuilder;
import viewFX.treePane.treeDetails.TreeDetailsPaneBuilder;

public class TreePaneBuilder extends PaneFXMLBuilder<TreePaneController> {
	
	@Setter
	private Session session;
	
	@Setter
	private Consumer<Person> selectPerson;

	@Setter
	private Runnable saveTree;
	@Setter
	private Runnable saveTreeAs;
	@Setter
	private Runnable closeTree;
	@Setter
	private Runnable createNewPerson;
	
	@Getter
	private Runnable settingSearchFocus;


	@Override
	protected String getFxmlFileName() {
		return "TreePane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setTreeDetailsPane(generateTreeDetailsPane());
		controller.setCommonSurnamePane(generateCommonNamePane());
		controller.setSearchPane(generateSearchPane());

		controller.setSaveTree(saveTree);
		controller.setSaveTreeAs(saveTreeAs);
		controller.setCloseTree(closeTree);
		controller.setCreateNewPerson(createNewPerson);
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
				searchEngine.refreshOrAdd(person);
			});
		}
		
		settingSearchFocus = () -> builder.getController().requestFocus();
		
		return builder.getPane();
	}
}
