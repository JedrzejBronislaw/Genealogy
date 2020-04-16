package treeGraphs.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Graphics2DPainter extends Painter {

	private Graphics2D g;
	
	
	public void setGraphics(Graphics2D graphics) {
		this.g = graphics;
	}
	
	@Override
	public void startDrawing() {
		if (g != null)
			g.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);		
	}
	
	@Override
	public void drawLine(Point start, Point end) {
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	@Override
	public void drawText(String text, Point topLeft) {
		g.drawString(text, topLeft.getX(), topLeft.getY());
	}

	@Override
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

	@Override
	public void drawRectangle(Point topLeft, Point bottomRight) {
		Rectangle rect = new Rectangle(topLeft, bottomRight);
		g.fillRect(rect.getLeft(), rect.getTop(), rect.width(), rect.height());
	}

	@Override
	public void drawCircle(Point center, float radius) {
		int diameter = (int)(radius*2);
		int left = (int) (center.getX()-radius);
		int  top = (int) (center.getY()-radius);
		
		g.fillOval(left, top, diameter, diameter);
	}
	
	@Override
	public void drawCircle(Point topLeft, Point bottomRight) {
		Rectangle rect = new Rectangle(topLeft, bottomRight);
		g.fillOval(rect.getLeft(), rect.getTop(), rect.width(), rect.height());
	}

	@Override
	public void drawRing(Point center, float radius) {
		int diameter = (int)(radius*2);
		int left = (int) (center.getX()-radius);
		int  top = (int) (center.getY()-radius);
		
		g.drawOval(left, top, diameter, diameter);
	}
	
	@Override
	public void drawRing(Point topLeft, Point bottomRight) {
		Rectangle rect = new Rectangle(topLeft, bottomRight);
		g.drawOval(rect.getLeft(), rect.getTop(), rect.width(), rect.height());
	}

	@Override
	public void setColor(Color color) {
		g.setColor(color);
	}
	
	@Override
	public Color getColor() {
		return g.getColor();
	}

	@Override
	public void setTextStyle(String fontName, int style, int size) {
		g.setFont(new Font(fontName, style, size));
	}

	@Override
	public void setTextStyle(Font font) {
		g.setFont(font);
	}

	@Override
	public void setLineStyle(int thickness) {
		g.setStroke(new BasicStroke(thickness));
	}
	
	@Override
	public int getLineThickness() {
		return (int)((BasicStroke)g.getStroke()).getLineWidth();
	}

	@Override
	public int getTextHeight() {
		FontMetrics fm = g.getFontMetrics();
		return fm.getAscent()-fm.getDescent();
	}

	@Override
	public int getTextWidth(String text) {
		FontMetrics fm = g.getFontMetrics();
		return fm.stringWidth(text);
	}

	@Override
	public Font getTextStyle() {
		return g.getFont();
	}

}
