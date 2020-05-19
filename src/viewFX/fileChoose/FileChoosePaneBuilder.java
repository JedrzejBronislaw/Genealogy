package viewFX.fileChoose;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import lombok.Setter;
import settings.RecentFile;
import viewFX.builders.PaneFXMLBuilder;

public class FileChoosePaneBuilder extends PaneFXMLBuilder<FileChoosePaneController> {

	@Setter
	private Consumer<File> openFileAction;
	@Setter
	private List<RecentFile> lastOpenFiles;
	
	
	@Override
	public String getFxmlFileName() {
		return "FileChoosePane.fxml";
	}

	@Override
	public void afterBuild() {
		controller.setPathList(lastOpenFiles);
		controller.setNewTreeEvent(() -> System.out.println("Create new tree"));
		controller.setOpenTreeEvent(openFileAction);
	}

}
