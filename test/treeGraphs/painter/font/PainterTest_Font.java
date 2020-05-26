package treeGraphs.painter.font;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import treeGraphs.painter.MyFont;
import treeGraphs.painter.Painter;
import treeGraphs.painter.MyFont.Style;

public abstract class PainterTest_Font {

	protected Painter painter;
	
	@Test
	public void testSetAndGetTextStyle() {
		painter.setTextStyle("Serif", Style.REGULAR, 10);
		MyFont font = painter.getTextStyle();
		
		assertEquals("Serif", font.getName());
		assertEquals(Style.REGULAR, font.getStyle());
		assertEquals(10, font.getSize());
	}
	
	@Test
	public void testTextSize() {
		painter.setTextStyle("Serif", Style.REGULAR, 20);
		MyFont font = painter.getTextStyle();
		
		assertEquals(20, font.getSize());
	}

	@Test
	public void testFontName() {
		painter.setTextStyle("Arial", Style.REGULAR, 20);
		MyFont font = painter.getTextStyle();
		
		assertEquals("Arial", font.getName());
	}
	
	@Test
	public void testBoldStyle() {
		painter.setTextStyle("Serif", Style.BOLD, 10);
		MyFont font = painter.getTextStyle();
		
		assertEquals(Style.BOLD, font.getStyle());
	}

	@Test
	public void testBoldItalicStyle() {
		painter.setTextStyle("Serif", Style.BOLD_ITALIC, 10);
		MyFont font = painter.getTextStyle();
		
		assertEquals(Style.BOLD_ITALIC, font.getStyle());
	}

	@Test
	public void testItalicStyle() {
		painter.setTextStyle("Serif", Style.ITALIC, 10);
		MyFont font = painter.getTextStyle();
		
		assertEquals(Style.ITALIC, font.getStyle());
	}
}
