package viewFX.search;

import java.util.function.Consumer;

import lombok.Setter;
import model.Person;
import searchEngine.SearchEngine;
import viewFX.builders.PaneFXMLBuilder;

public class SearchViewBuilder extends PaneFXMLBuilder<SearchViewController> {

	@Setter
	private Consumer<Person> chooseAction;
	
	@Setter
	private SearchEngine searchEngine = new SearchEngine();
	

	@Override
	protected String getFxmlFileName() {
		return "SearchView.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setChooseAction(chooseAction);
		controller.setSubmitQuery(query -> controller.setItems(searchEngine.run(query)));
	}
}
