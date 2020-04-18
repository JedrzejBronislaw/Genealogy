package treeGraphs.painter.graphics2DPainter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import treeGraphs.painter.GraphSaver;
import windows.Canvas;

@AllArgsConstructor
public class Graphics2DGraphSaver extends GraphSaver {

	@Setter @NonNull
	private Canvas canvas;
	
	
	@Override
	public boolean save(File file) {
		BufferedImage bufImage = new BufferedImage(canvas.getSize().width, canvas.getSize().height,BufferedImage.TYPE_INT_RGB);
		canvas.paint(bufImage.createGraphics());
	    
		try{
	        ImageIO.write(bufImage, "png", file);
	    }catch(Exception e){
			e.printStackTrace();
	    	return false;
	    }
		
		return true;
	}
}
