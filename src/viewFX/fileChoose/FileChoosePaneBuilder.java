package viewFX.fileChoose;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import lombok.Setter;
import settings.RecentFile;
import viewFX.builders.PaneFXMLBuilder;

public class FileChoosePaneBuilder extends PaneFXMLBuilder<FileChoosePaneController> {

	@Setter
	private Runnable newTreeEvent;
	@Setter
	private Consumer<File> openFileAction;
	@Setter
	private List<RecentFile> lastOpenFiles;
	
	@Override
	protected String getFxmlFileName() {
		return "FileChoosePane.fxml";
	}

	@Override
	protected void afterBuild() {
		controller.setPathList(lastOpenFiles);
		controller.setNewTreeEvent(newTreeEvent);
		controller.setOpenTreeEvent(openFileAction);
	}
}
