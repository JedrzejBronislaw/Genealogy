package treeGraphs.painter.graphics2DPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import lombok.Setter;
import treeGraphs.painter.Handle;
import treeGraphs.painter.MyColor;
import treeGraphs.painter.MyFont;
import treeGraphs.painter.MyFont.Style;
import treeGraphs.painter.Painter;
import treeGraphs.painter.Point;
import treeGraphs.painter.Rectangle;

public class Graphics2DPainter extends Painter {

	private Graphics2D g;
	
	@Setter
	private G2DHandleFactory handleFactory;
	
	public void setGraphics(Graphics2D graphics) {
		this.g = graphics;
	}
	
	@Override
	public void startDrawing() {
		if (g != null)
			g.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);		

		handleFactory.clearClickMap();
	}
	
	@Override
	public void drawLine(Point start, Point end) {
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	@Override
	public Handle drawText(String text, Point topLeft) {
		g.drawString(text, topLeft.getX(), topLeft.getY());
		
		return handleFactory.createHandle(topLeft, topLeft.addVector(getTextWidth(text), -getTextHeight()));
	}

	@Override
	public Handle drawRectangle(Point topLeft, Point bottomRight) {
		Rectangle rect = new Rectangle(topLeft, bottomRight);
		g.fillRect(rect.getLeft(), rect.getTop(), rect.width(), rect.height());
		
		return handleFactory.createHandle(topLeft, bottomRight);
	}

	@Override
	public Handle drawCircle(Point center, float radius) {
		int diameter = (int)(radius*2);
		int left = (int) (center.getX()-radius);
		int  top = (int) (center.getY()-radius);
		
		g.fillOval(left, top, diameter, diameter);

		return handleFactory.createHandle(left, top, left+diameter, top+diameter);
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
	public void setColor(MyColor color) {
		g.setColor(new Color(
				color.getRed(),
				color.getGreen(),
				color.getBlue(),
				color.getAlpha()));
	}
	
	@Override
	public MyColor getColor() {
		Color color = g.getColor();

		return new MyColor(
				color.getRed(),
				color.getGreen(),
				color.getBlue(),
				color.getAlpha());
	}

	@Override
	public void setTextStyle(String fontName, Style style, int size) {
		int s = Font.PLAIN;
		
		if (style == Style.BOLD_ITALIC) s = Font.BOLD + Font.ITALIC; else
		if (style == Style.BOLD) s = Font.BOLD; else
		if (style == Style.ITALIC) s = Font.ITALIC;
		
		g.setFont(new Font(fontName, s, size));
	}

	@Override
	public MyFont getTextStyle() {
		Font font = g.getFont();
		int style = font.getStyle();
		
		Style s = Style.REGULAR;
		boolean b = false;
		boolean i = false;
		
		if ((style & Font.BOLD)   != 0) b = true;
		if ((style & Font.ITALIC) != 0) i = true;
		
		if (b && i) s = Style.BOLD_ITALIC;
		else if (b) s = Style.BOLD;
		else if (i) s = Style.ITALIC;
		
		return new MyFont(font.getFamily(), s, font.getSize());
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
	public Handle createHandle(Point topLeft, Point bottomRight) {
		return handleFactory.createHandle(topLeft, bottomRight);
	}
}
