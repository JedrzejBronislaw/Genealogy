package treeGraphs.painter.fxPainter;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import treeGraphs.painter.GraphSaver;

@AllArgsConstructor
public class FxGraphSaver extends GraphSaver {

	@Setter @NonNull
	private Pane pane;
	
	
	@Override
	public boolean save(File file) {
		WritableImage writableImage = pane.snapshot(new SnapshotParameters(), null);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);

		try {
			ImageIO.write(renderedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
