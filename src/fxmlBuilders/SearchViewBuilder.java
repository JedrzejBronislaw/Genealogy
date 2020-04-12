package fxmlBuilders;

import java.util.function.Consumer;

import fxmlControllers.SearchViewController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import searchEngine.SearchEngine;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class SearchViewBuilder {

	@Getter
	private Pane pane;
	
	@Getter
	private SearchViewController controller;

	@Setter
	private Consumer<Person> chooseAction;
	
	@Setter
	private SearchEngine searchEngine = new SearchEngine();
	
	public void build() {
		MyFXMLLoader<SearchViewController> loader = new MyFXMLLoader<>();
		NodeAndController<SearchViewController> nac = loader.create("SearchView.fxml");
		
		pane = (Pane) nac.getNode();
		controller = nac.getController();
		
		controller.setChooseAction(chooseAction);
		controller.setSubmitQuery(query -> controller.setItems(searchEngine.run(query)));
	}
}
