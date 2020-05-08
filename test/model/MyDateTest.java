package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyDateTest {

//	@Test
//	public void testSetDay() {
//	}
//
//	@Test
//	public void testSetMonth() {
//	}
//
//	@Test
//	public void testSetYear() {
//	}
//
//	@Test
//	public void testIsLeapYear() {
//	}
//
//	@Test
//	public void testCompare() {
//	}
//
//	@Test
//	public void testDifference() {
//	}

	//-----copy
	
	@Test
	public void testCopy_equalSign() {
		MyDate orignal = new MyDate(1, 2, 2020);
		MyDate copy = orignal.copy();
		
		assertFalse(orignal == copy);
	}
	
	@Test
	public void testCopy_equals() {
		MyDate orignal = new MyDate(1, 2, 2020);
		MyDate copy = orignal.copy();
		
		assertEquals(orignal, copy);
	}

//	@Test
//	public void testToString() {
//	}

}
