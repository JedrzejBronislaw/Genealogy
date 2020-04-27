package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tools.Injection;

public class MainWindowController implements Initializable {

	public enum Views{ChooseFile, Tree, Card, Graph, EditPerson}
	
	@RequiredArgsConstructor
	public static class ViewPane{
		private final Pane pane;
		private final Runnable onShowAction;
		
		public ViewPane(Pane pane) {
			this.pane = pane;
			this.onShowAction = null;
		}
	}
	
	@FXML
	private Label titleLabel;
	@FXML
	private StackPane mainPane;
	@FXML
	private StackPane topPane;
	
	@Setter
	private ViewPane chooseFilePane;
	@Setter
	private ViewPane cardPane;
	@Setter
	private ViewPane treePane;
	@Setter
	private ViewPane graphPane;
	@Setter
	private ViewPane editPersonPane;
	
	private Views currentView;
	
	public void showView(Views view) {
		ViewPane selected;
		
		switch (view) {
		case ChooseFile:
			selected = chooseFilePane;
			break;
		case Tree:
			selected = treePane;
			break;
		case Card:
			selected = cardPane;
			break;
		case Graph:
			selected = graphPane;
			break;
		case EditPerson:
			selected = editPersonPane;
			break;

		default:
			selected = null;
		}
		
		if(selected != null && view != currentView) {
			currentView = view;
			Platform.runLater(() -> {
				mainPane.getChildren().clear();
				mainPane.getChildren().add(selected.pane);
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
			if (currentView != null && currentView != Views.ChooseFile)
				showView(Views.Tree);
		});
	}
}
