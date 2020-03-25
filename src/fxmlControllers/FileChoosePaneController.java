package fxmlControllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lombok.Setter;

public class FileChoosePaneController implements Initializable {

	@FXML
	private Button newTreeButton;
	@FXML
	private Button browseButton;
	@FXML
	private ListView<String> fileList;

	@Setter
	private Runnable newTreeEvent;
	@Setter
	private Consumer<File> openTreeEvent;

	public void setPathList(List<String> pathList) {
		pathList.forEach(fileList.getItems()::add);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		newTreeButton.setOnAction(e -> {
			if (newTreeEvent != null)
				newTreeEvent.run();
		});
		
		browseButton.setOnAction(e -> {
			File treeFile = selectTree();
			if (openTreeEvent != null && treeFile != null)
				openTreeEvent.accept(treeFile);
		});
		
		fileList.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2 && openTreeEvent != null)
				openTreeEvent.accept(new File(fileList.getSelectionModel().getSelectedItem()));
		});
	}

	
	private File selectTree() {
		FileChooser chooser = new FileChooser();
		File file;
		
		chooser.getExtensionFilters().add(new ExtensionFilter("PGL Trees", "*.pgl"));
		file = chooser.showOpenDialog(null);
		
		return file;
	}
}
