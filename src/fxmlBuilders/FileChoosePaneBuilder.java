package fxmlBuilders;

import java.util.List;

import fxmlControllers.FileChoosePaneController;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import tools.MyFXMLLoader;
import tools.MyFXMLLoader.NodeAndController;

public class FileChoosePaneBuilder {

	@Getter
	private Pane pane;

	@Setter
	private List<String> lastOpenFiles;
	
	
	public void build() {
		MyFXMLLoader<FileChoosePaneController> loader = new MyFXMLLoader<>();
		NodeAndController<FileChoosePaneController> nac = loader.create("FileChoosePane.fxml");
		FileChoosePaneController controller;
		
		controller = nac.getController();
		pane = (Pane) nac.getNode();
		
	
		controller.setPathList(lastOpenFiles);
		controller.setNewTreeEvent(() -> System.out.println("Create new tree"));
		controller.setOpenTreeEvent(file -> System.out.println("Open tree file (" + file.getName() + ")"));
	}

}
