package treeGraphs.painter;

import static org.junit.Assert.*;

import org.junit.Test;

public class RectangleTest {

	private int top = 21;
	private int left = 11;
	private int bottom = 50;
	private int right = 30;
	private int height = 29;
	private int width = 19;
	
	public Rectangle genSimpleRect() {
		Point topLeft = new Point(11, 21);
		Point bottomRight = new Point(30, 50);
		return new Rectangle(topLeft, bottomRight);
	}
	public Rectangle genReverseRect() {
		Point topLeft = new Point(30, 50);
		Point bottomRight = new Point(11, 21);
		return new Rectangle(topLeft, bottomRight);
	}
	public Rectangle genRect2() {
		Point topLeft = new Point(11, 50);
		Point bottomRight = new Point(30, 21);
		return new Rectangle(topLeft, bottomRight);
	}
	public Rectangle genReverseRect2() {
		Point topLeft = new Point(30, 21);
		Point bottomRight = new Point(11, 50);
		return new Rectangle(topLeft, bottomRight);
	}

	@Test
	public void test_SimpleRect_Top() {
		assertEquals(top, genSimpleRect().getTop());
	}
	@Test
	public void test_SimpleRect_Left() {
		assertEquals(left, genSimpleRect().getLeft());
	}
	@Test
	public void test_SimpleRect_Bottom() {
		assertEquals(bottom, genSimpleRect().getBottom());
	}
	@Test
	public void test_SimpleRect_Right() {
		assertEquals(right, genSimpleRect().getRight());
	}
	@Test
	public void test_SimpleRect_width() {
		assertEquals(width, genSimpleRect().width());
	}
	@Test
	public void test_SimpleRect_height() {
		assertEquals(height, genSimpleRect().height());
	}



	@Test
	public void test_ReverseRect_Top() {
		assertEquals(top, genReverseRect().getTop());
	}
	@Test
	public void test_ReverseRect_Left() {
		assertEquals(left, genReverseRect().getLeft());
	}
	@Test
	public void test_ReverseRect_Bottom() {
		assertEquals(bottom, genReverseRect().getBottom());
	}
	@Test
	public void test_ReverseRect_Right() {
		assertEquals(right, genReverseRect().getRight());
	}
	@Test
	public void test_ReverseRect_width() {
		assertEquals(width, genReverseRect().width());
	}
	@Test
	public void test_ReverseRect_height() {
		assertEquals(height, genReverseRect().height());
	}



	@Test
	public void test_Rect2_Top() {
		assertEquals(top, genRect2().getTop());
	}
	@Test
	public void test_Rect2_Left() {
		assertEquals(left, genRect2().getLeft());
	}
	@Test
	public void test_Rect2_Bottom() {
		assertEquals(bottom, genRect2().getBottom());
	}
	@Test
	public void test_Rect2_Right() {
		assertEquals(right, genRect2().getRight());
	}
	@Test
	public void test_Rect2_width() {
		assertEquals(width, genRect2().width());
	}
	@Test
	public void test_Rect2_height() {
		assertEquals(height, genRect2().height());
	}



	@Test
	public void test_ReverseRect2_Top() {
		assertEquals(top, genReverseRect2().getTop());
	}
	@Test
	public void test_ReverseRect2_Left() {
		assertEquals(left, genReverseRect2().getLeft());
	}
	@Test
	public void test_ReverseRect2_Bottom() {
		assertEquals(bottom, genReverseRect2().getBottom());
	}
	@Test
	public void test_ReverseRect2_Right() {
		assertEquals(right, genReverseRect2().getRight());
	}
	@Test
	public void test_ReverseRect2_width() {
		assertEquals(width, genReverseRect2().width());
	}
	@Test
	public void test_ReverseRect2_height() {
		assertEquals(height, genReverseRect2().height());
	}

}
