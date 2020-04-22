package treeGraphs.painter.fxPainter;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import treeGraphs.painter.Painter;
import treeGraphs.painter.Point;
import treeGraphs.painter.Rectangle;

public class FxPainter extends Painter{

	private Pane pane;
	
	private int fontsize = 10;
	private String fontfamily = "System";
	private boolean fontBold = false;
	private boolean fontItalic = false;	
	private int linewidth = 1;
	private Color color = Color.BLACK;
	
	public FxPainter(Pane pane) {
		this.pane = pane;
	}

	private Font currentFont() {
		return Font.font(
				fontfamily,
				fontBold ? FontWeight.BLACK : FontWeight.NORMAL,
				fontItalic ? FontPosture.ITALIC : FontPosture.REGULAR,
				fontsize);
	}
	
	@Override
	public void startDrawing() {}

	@Override
	public void drawLine(Point start, Point end) {
		Line line = new Line(
				start.getX() + 0.5,
				start.getY() + 0.5,
				end.getX() + 0.5,
				end.getY() + 0.5);
		line.setStroke(color);
		line.setStrokeWidth(linewidth);
		
		pane.getChildren().add(line);
	}

	@Override
	public void drawText(String text, Point topLeft) {
		Text textFX = new Text();

		textFX.setText(text);
		textFX.setX(topLeft.getX());
		textFX.setY(topLeft.getY());
		textFX.setFont(currentFont());

		pane.getChildren().add(textFX);
	}

	@Override
	public void drawRectangle(Point topLeft, Point bottomRight) {
		Rectangle rect = new Rectangle(topLeft, bottomRight);
		
		javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(rect.getLeft(), rect.getTop(), rect.width(), rect.height());
		r.setFill(color);
		pane.getChildren().add(r);
	}

	@Override
	public void drawCircle(Point center, float radius) {
		Circle circle = new Circle(center.getX(), center.getY(), radius);

		circle.setFill(color);
		
		pane.getChildren().add(circle);
	}

	@Override
	public void drawRing(Point center, float radius) {
		Circle circle = new Circle(center.getX(), center.getY(), radius);
		
		circle.setStrokeWidth(linewidth);
		circle.setStroke(color);
		circle.setFill(new Color(0,0,0,0));
		
		pane.getChildren().add(circle);
	}

	@Override
	public void setColor(java.awt.Color color) {
		float r = color.getRed()/255f;
		float g = color.getGreen()/255f;
		float b = color.getBlue()/255f;
		float a = color.getAlpha()/255f;
		
		this.color = new Color(r,g,b,a);
	}

	@Override
	public java.awt.Color getColor() {
		color.getRed();
		return new java.awt.Color(
				(float) color.getRed(),
				(float) color.getGreen(),
				(float) color.getBlue());
	}

	@Override
	public void setTextStyle(String fontName, int style, int size) {
		fontfamily = fontName;
		fontsize = size;
		
		fontBold   = (style & java.awt.Font.BOLD)   != 0;
		fontItalic = (style & java.awt.Font.ITALIC) != 0;
	}

	@Override
	public void setTextStyle(java.awt.Font font) {
		fontfamily = font.getFamily();
		fontsize = font.getSize();
		
		fontBold   = (font.getStyle() & java.awt.Font.BOLD)   != 0;
		fontItalic = (font.getStyle() & java.awt.Font.ITALIC) != 0;
	}

	@Override
	public java.awt.Font getTextStyle() {
		int style = 0;
		
		if (fontBold)   style += java.awt.Font.BOLD;
		if (fontItalic) style += java.awt.Font.ITALIC;
		
		return new java.awt.Font(fontfamily, style, fontsize);
	}

	@Override
	public void setLineStyle(int thickness) {
		linewidth = thickness;
	}

	@Override
	public int getLineThickness() {
		return linewidth;
	}

	@Override
	public int getTextHeight() {
		Text textFX = new Text();
		textFX.setFont(currentFont());
		
		return (int) (textFX.getFont().getSize() * 0.8);
	}

	@Override
	public int getTextWidth(String text) {
		Text textFX = new Text();

		textFX.setFont(currentFont());
		textFX.setText(text);
		
		return (int) textFX.getBoundsInLocal().getWidth();
	}

}
