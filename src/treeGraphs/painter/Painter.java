package treeGraphs.painter;

import lombok.Setter;
import treeGraphs.painter.MyFont.Style;

public abstract class Painter {
	
	@Setter
	protected int arrowheadLenght = 5, arrowheadWidth = 4;
	
	abstract public void startDrawing();
	
	abstract public void drawLine(Point start, Point end);
	public void drawHLine(Point start, int length) {
		drawLine(start, start.addVector(length, 0));
	}
	public void drawVLine(Point start, int length) {
		drawLine(start, start.addVector(0, length));
	}
	public void drawHLineTo(Point start, int endX) {
		drawLine(start, new Point(endX, start.getY()));
	}
	public void drawVLineTo(Point start, int endY) {
		drawLine(start, new Point(start.getX(), endY));
	}
	
	abstract public Handle drawText(String text, Point topLeft);
	public Handle drawText(String text, int top, int left) {
		return drawText(text, new Point(top, left));
	}
	public Handle drawMultilineText(Point topLeft, int lineHeight, String... lines) {
		MultiHandle handles = new MultiHandle();
		
		for(String line : lines) {
			handles.addHandle(drawText(line, topLeft));
			topLeft = topLeft.addVector(0, lineHeight);
		}
		
		return handles;
	}
	
	abstract public Handle drawRectangle(Point topLeft, Point bottomRight);
	abstract public Handle drawCircle(Point center, float radius);
	abstract public void drawRing(Point center, float radius);
	
	abstract public void setColor(MyColor color);
	abstract public MyColor getColor();
	public MyColor changeColor(MyColor color) {
		MyColor oldColor = getColor();
		setColor(color);
		return oldColor;
	}
	
	abstract public void setTextStyle(String fontName, MyFont.Style style, int size);
	public void setTextStyle(MyFont font) {
		setTextStyle(font.getName(), font.getStyle(), font.getSize());
	}
	public void bold() {
		MyFont font = getTextStyle();
		Style style = font.getStyle();
		
		style = (style == Style.ITALIC ||
				 style == Style.BOLD_ITALIC) ? Style.BOLD_ITALIC : Style.BOLD;
		
		setTextStyle(font.getName(), style, font.getSize());
	}
	public void unbold() {
		MyFont font = getTextStyle();
		Style style = font.getStyle();
		
		style = (style == Style.ITALIC ||
				 style == Style.BOLD_ITALIC) ? Style.ITALIC : Style.REGULAR;
		
		setTextStyle(font.copy(style));
	}
	public void setTextSize(int size) {
		setTextStyle(getTextStyle().copy(size));
	}
	public int changeTextSize(int size) {
		int oldSize = getTextStyle().getSize();
		setTextSize(size);
		return oldSize;
	}
	public void setTextSize(double size) {
		setTextStyle(getTextStyle().copy(size));
	}
	public int changeTextSize(double size) {
		int oldSize = getTextStyle().getSize();
		setTextSize(size);
		return oldSize;
	}
	public void setFontName(String fontName) {
		setTextStyle(getTextStyle().copy(fontName));
	}
	public String changeFontName(String fontName) {
		String oldName = getTextStyle().getName();
		setFontName(fontName);
		return oldName;
	}
	
	abstract public MyFont getTextStyle();
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
		
		drawRing(Point.middle(topLeft, bottomRight), radius);
	}
	public void drawCircle(Point topLeft, Point bottomRight) {
		int dx = Math.abs(bottomRight.getX() - topLeft.getX());
		int dy = Math.abs(bottomRight.getY() - topLeft.getY());
		float radius = Math.min(dx, dy)/2f;
		
		drawCircle(Point.middle(topLeft, bottomRight), radius);
	}
	
	abstract public Handle createHandle(Point topLeft, Point bottomRight);
	
	public void drawArrowhead(Point vertex, Direction side) {
		int a, b, c, d;
		int dx, dy;
		
		a = b = c = d = 1;
		dx = dy = 1;
		
		switch (side) {
			case UP:    a = -1; b = 1;  c = 1;  d = 1;  break;
			case DOWN:  a = -1; b = -1; c = 1;  d = -1; break;
			case LEFT:  a = 1;  b = 1;  c = 1;  d = -1; break;
			case RIGHT: a = -1; b = 1;  c = -1; d = -1; break;
		}

		switch (side) {
			case UP:
			case DOWN:  dx = arrowheadWidth/2; dy = arrowheadLenght; break;
			case LEFT:
			case RIGHT: dx = arrowheadLenght; dy = arrowheadWidth/2; break;
		}
		
		drawLine(vertex, vertex.addVector(a * dx, b * dy));
		drawLine(vertex, vertex.addVector(c * dx, d * dy));
	}
}
