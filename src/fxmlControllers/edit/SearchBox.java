package fxmlControllers.edit;

import java.util.function.Consumer;

import fxmlBuilders.SearchViewBuilder;
import fxmlControllers.SearchViewController;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lang.Internationalization;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import model.Tree;
import searchEngine.SearchEngine;
import tools.Injection;

public class SearchBox extends VBox {

	private Label showSearchLabel;
	@Getter
	private SearchEngine searchEngine = new SearchEngine();
	
	private Pane searchPane;
	private boolean searchVisible;
	private SearchViewController searchController;
	
	@Setter
	private Consumer<Person> selectPerson;

	
	public void setTree(Tree tree) {
		searchEngine.setTree(tree);
	}

	
	public SearchBox() {
		build();
	}
		
	public void build() {
		showSearchLabel = new Label();
		searchPane = generateSearchPane();
		getChildren().add(showSearchLabel);
		getChildren().add(searchPane);
		
		showSearchLabel.setOnMouseClicked(e -> setSearchVisible(!searchVisible));
		hideSearch();
	}
	
	private Pane generateSearchPane() {
		SearchViewBuilder builder = new SearchViewBuilder();
		
		builder.setChooseAction(person -> Injection.run(selectPerson, person));
		builder.setSearchEngine(searchEngine);
		
		builder.build();
		
		searchController = builder.getController();
		searchController.setTitleVisible(false);
		
		return builder.getPane();
	}
	
	public void setSearchVisible(boolean visible) {

		searchPane.setManaged(visible);
		searchPane.setVisible(visible);
		searchVisible = visible;

		showSearchLabel.setText(Internationalization.get(
				(visible) ? "hide_search" : "show_search"));
		
		if (visible) searchController.requestFocus();
	}
	
	public void showSearch() {
		setSearchVisible(true);
	}
	public void hideSearch() {
		setSearchVisible(false);
	}
	
	public void clear() {
		searchController.clearFields();
	}
}
