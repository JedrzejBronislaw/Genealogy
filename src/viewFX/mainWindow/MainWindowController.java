package viewFX.mainWindow;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tools.Injection;

public class MainWindowController implements Initializable {

	public enum Views{ChooseFile, OpenError, Tree, Card, Graph, EditPerson}
	
	@RequiredArgsConstructor
	public static class ViewPane{
		private final Pane pane;
		@Setter private Pane leftPane, rightPane, topPane, bottomPane;
		
		private final Runnable onShowAction;
		
		public ViewPane(Pane pane) {
			this.pane = pane;
			this.onShowAction = null;
		}
	}
	
	@FXML
	private Label titleLabel;
	@FXML
	private BorderPane borderPane;
	@FXML
	private StackPane mainPane;
	@FXML
	private StackPane topPane;
	
	private Map<Views, ViewPane> panes = new HashMap<>();
	
	public void addPane(Views viewType, ViewPane viewPane) {
		panes.put(viewType, viewPane);
	}
	
	private Views currentView;
	
	public void showView(Views view) {
		ViewPane selected;
		
		selected = panes.get(view);
		
		if(selected != null && view != currentView) {
			currentView = view;
			Platform.runLater(() -> {
				mainPane.getChildren().clear();
				mainPane.getChildren().add(selected.pane);
				borderPane.setLeft(selected.leftPane);
				borderPane.setRight(selected.rightPane);
				borderPane.setTop(selected.topPane);
				borderPane.setBottom(selected.bottomPane);
				Injection.run(selected.onShowAction);
			});
		}
	}

	public void setLanguagePane(Pane pane) {
		topPane.getChildren().add(pane);
	}

	public void setFullScreenPane(Pane pane) {
		topPane.getChildren().add(pane);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		titleLabel.setOnMouseClicked(e -> {
			if (currentView != null &&
				currentView != Views.ChooseFile &&
				currentView != Views.OpenError)
				showView(Views.Tree);
			
			if (currentView == Views.OpenError)
				showView(Views.ChooseFile);
		});
	}
}
