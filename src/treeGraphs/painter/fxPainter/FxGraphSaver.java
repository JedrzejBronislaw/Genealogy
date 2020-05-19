package treeGraphs.painter.fxPainter;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;
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
		WritableImage writableImage = createScaledSnapshot(2);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);

		try {
			ImageIO.write(renderedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
    private WritableImage createScaledSnapshot(int scale) {
        final Bounds bounds = pane.getLayoutBounds();

        final WritableImage image = new WritableImage(
            (int) Math.round(bounds.getWidth() * scale),
            (int) Math.round(bounds.getHeight() * scale));

        final SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(scale, scale));

        pane.snapshot(spa, image);
        
        return image;
    }
}
