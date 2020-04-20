package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyDateTest_Equals {

	private static MyDate expectedDate;
	private MyDate actualDate;
	
	@BeforeClass
	public static void prepareExpected() {
		MyDate date = new MyDate();
		date.setDay(11);
		date.setMonth(12);
		date.setYear(1913);

		expectedDate = date;
	}

	@Before
	public void prepareActual() {
		MyDate date = new MyDate();
		date.setDay(11);
		date.setMonth(12);
		date.setYear(1913);

		actualDate = date;
	}
	
	@Test
	public void notEqualsNull() {
		assertFalse(expectedDate.equals(null));
	}
	
	@Test
	public void notEqualsObject() {
		assertFalse(expectedDate.equals(new Object()));
	}
	
	@Test
	public void equals() {
		assertTrue(expectedDate.equals(actualDate));
	}
	
	@Test
	public void equals_theSame() {
		assertTrue(expectedDate.equals(expectedDate));
	}
	
	@Test
	public void notEquals_day() {
		actualDate.setDay(15);
		assertFalse(expectedDate.equals(actualDate));
	}
	
	@Test
	public void notEquals_month() {
		actualDate.setMonth(10);
		assertFalse(expectedDate.equals(actualDate));
	}
	
	@Test
	public void notEquals_year() {
		actualDate.setYear(1910);
		assertFalse(expectedDate.equals(actualDate));
	}
}
