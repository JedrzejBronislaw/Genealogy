package treeGraphs.painter;

import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

	//AddVector
	@Test
	public void testAddVector_positive() {
		Point point  = new Point(10, 20);
		Point point2 = point.addVector(5, 15);
		
		assertEquals(new Point(15, 35), point2);
		assertFalse(point == point2);
	}

	@Test
	public void testAddVector_negative() {
		Point point  = new Point(10, 20);
		Point point2 = point.addVector(-5, -15);
		
		assertEquals(new Point(5, 5), point2);
		assertFalse(point == point2);
	}
	
	@Test
	public void testAddVector_negativeResult() {
		Point point  = new Point(10, 20);
		Point point2 = point.addVector(-55, -35);
		
		assertEquals(new Point(-45, -15), point2);
		assertFalse(point == point2);
	}
	
	@Test
	public void testAddVector_zero() {
		Point point  = new Point(10, 20);
		Point point2 = point.addVector(0, 0);
		
		assertEquals(point, point2);
		assertFalse(point == point2);
	}

	//Equals(x, y)
	@Test
	public void testEquals() {
		Point point = new Point(10, 20);
		assertTrue(point.equals(10, 20));
	}
	
	@Test
	public void testEquals_wrongX() {
		Point point = new Point(10, 20);
		assertFalse(point.equals(11, 20));
	}
	
	@Test
	public void testEquals_wrongY() {
		Point point = new Point(10, 20);
		assertFalse(point.equals(10, 21));
	}


	//Equals(point)
	@Test
	public void testEquals_itself() {
		Point point = new Point(10, 20);
		assertTrue(point.equals(point));
	}
	
	@Test
	public void testEquals_twoPoints() {
		Point point1 = new Point(10, 20);
		Point point2 = new Point(10, 20);
		assertTrue(point1.equals(point2));
	}
	
	@Test
	public void testEquals_twoDiffPoints() {
		Point point1 = new Point(10, 20);
		Point point2 = new Point(11, 20);
		assertFalse(point1.equals(point2));
	}

	//Middle
	@Test
	public void testMiddle() {
		Point point1 = new Point(0, 0);
		Point point2 = new Point(100, 200);
		
		assertEquals(new Point(50, 100), Point.middle(point1, point2));
	}
	
	@Test
	public void testMiddle2() {
		Point point1 = new Point(10, 50);
		Point point2 = new Point(20, 100);
		
		assertEquals(new Point(15, 75), Point.middle(point1, point2));
	}
	
	@Test
	public void testMiddle_reverseOrder() {
		Point point1 = new Point(0, 0);
		Point point2 = new Point(100, 200);
		
		assertEquals(new Point(50, 100), Point.middle(point2, point1));
	}
	
	@Test
	public void testMiddle_theSamePoint() {
		Point point1 = new Point(100, 200);
		Point point2 = new Point(100, 200);
		
		assertEquals(new Point(100, 200), Point.middle(point2, point1));
	}

	@Test
	public void testMiddle_negativeAndPositive() {
		Point point1 = new Point(-20, -60);
		Point point2 = new Point(100, 200);
		
		assertEquals(new Point(40, 70), Point.middle(point1, point2));
	}
	
	@Test
	public void testMiddle_resultZero() {
		Point point1 = new Point(-100, -200);
		Point point2 = new Point(100, 200);
		
		assertEquals(new Point(0, 0), Point.middle(point1, point2));
	}
	
	@Test
	public void testMiddle_downRound() {
		Point point1 = new Point(1, 1);
		Point point2 = new Point(2, 2);
		
		assertEquals(new Point(1, 1), Point.middle(point1, point2));
	}
}
