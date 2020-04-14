package treeGraphs.painter;

import java.awt.Color;
import java.awt.Font;

import lombok.Setter;

public abstract class Painter {
	
	@Setter
	protected int arrowheadLenght, arrowheadWidth;
	
	abstract public void startDrawing();
	
	abstract public void drawLine(Point start, Point end);
	abstract public void drawText(String text, Point topLeft);
	abstract public void drawArrowhead(Point vertex, Side side);
	abstract public void drawRectangle(Point topLeft, Point bottomRightS);
	abstract public void drawCircle(Point center, float radius);
	abstract public void drawRing(Point center, float radius);
	
	public void drawSquare(Point topLeft, int size){
		drawRectangle(topLeft, topLeft.addVector(size, size));
	}

	abstract public int getTextHeight();
	abstract public int getTextWidth(String text);
	abstract public Font getTextStyle();
	
	abstract public void setColor(Color color);
	abstract public void setTextStyle(String fontName, int style, int size);
	abstract public void setTextStyle(Font font);
	abstract public void setLineStyle(int thickness);
}
