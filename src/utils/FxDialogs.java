package utils;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FxDialogs {
	
	private final static ExtensionFilter PGL = new ExtensionFilter("PGL Trees", "*.pgl");
	private final static ExtensionFilter PNG = new ExtensionFilter("PNG Files", "*.png");
	
	public static File openPGL() {
		return PGLchooser(PGL).showOpenDialog(null);
	}

	public static File savePGL() {
		return PGLchooser(PGL).showSaveDialog(null);
	}

	public static File savePNG(){
		return PGLchooser(PNG).showSaveDialog(null);
	}
	
	private static FileChooser PGLchooser(ExtensionFilter... extensions) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(extensions);
		
		return chooser;
	}
}
