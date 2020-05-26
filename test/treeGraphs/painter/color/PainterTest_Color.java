package treeGraphs.painter.color;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import treeGraphs.painter.MyColor;
import treeGraphs.painter.Painter;

public abstract class PainterTest_Color {

	protected Painter painter;
	
	@Test
	public void testSetAndGetColor() {
		painter.setColor(new MyColor(123,231,132,100));
		MyColor color = painter.getColor();
		
		assertEquals(123, color.getRed());
		assertEquals(231, color.getGreen());
		assertEquals(132, color.getBlue());
		assertEquals(100, color.getAlpha());
	}
}
