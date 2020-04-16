package treeGraphs.painter;

import java.awt.Color;
import java.awt.Font;

import lombok.Setter;

public abstract class Painter {
	
	@Setter
	protected int arrowheadLenght = 5, arrowheadWidth = 4;
	
	abstract public void startDrawing();
	
	abstract public void drawLine(Point start, Point end);
	abstract public void drawText(String text, Point topLeft);
	abstract public void drawArrowhead(Point vertex, Direction direction);
	abstract public void drawRectangle(Point topLeft, Point bottomRight);
	abstract public void drawCircle(Point center, float radius);
	abstract public void drawRing(Point center, float radius);
	
	abstract public void setColor(Color color);
	abstract public Color getColor();
	abstract public void setTextStyle(String fontName, int style, int size);
	abstract public void setTextStyle(Font font);
	abstract public Font getTextStyle();
	abstract public void setLineStyle(int thickness);
	abstract public int getLineThickness();

	abstract public int getTextHeight();
	abstract public int getTextWidth(String text);
	
	public void drawSquare(Point topLeft, int size){
		drawRectangle(topLeft, topLeft.addVector(size, size));
	}
	public void drawRing(Point topLeft, Point bottomRight) {
		int dx = Math.abs(bottomRight.getX() - topLeft.getX());
		int dy = Math.abs(bottomRight.getY() - topLeft.getY());
		float radius = Math.min(dx, dy)/2f;
		
		System.out.println("TL -> " + topLeft.toString() + "; BR -> " + bottomRight.toString());
		System.out.println("dx = " + dx + "; dy = " + dy + "; r = " + radius);
		
		drawRing(Point.middle(topLeft, bottomRight), radius);
	}
	public void drawCircle(Point topLeft, Point bottomRight) {
		int dx = Math.abs(bottomRight.getX() - topLeft.getX());
		int dy = Math.abs(bottomRight.getY() - topLeft.getY());
		float radius = Math.min(dx, dy)/2f;
		
		System.out.println("TL -> " + topLeft.toString() + "; BR -> " + bottomRight.toString());
		System.out.println("dx = " + dx + "; dy = " + dy + "; r = " + radius);
		
		drawCircle(Point.middle(topLeft, bottomRight), radius);
	}
}
