package treeGraphs.painter.font;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import treeGraphs.painter.graphics2DPainter.Graphics2DPainter;

public class Graphics2DPainterTest_Font extends PainterTest_Font{

	public Graphics2DPainterTest_Font() {
		
		Graphics2DPainter g2dPainter = new Graphics2DPainter();
		g2dPainter.setGraphics(createGraphics2D());
		
		painter = g2dPainter;
	}

	private Graphics2D createGraphics2D() {
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
	}

}
