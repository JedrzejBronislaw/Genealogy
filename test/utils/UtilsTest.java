package utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UtilsTest {
	
	// getStringValues
	
	enum EnumExample{A, Bb, Ccc};
	enum EmptyEnumExample{};
	enum AdvancedEnumExample{Aa(100), Bb(200), Cc(300); AdvancedEnumExample(int param) {}};
	
	@Test
	public void shouldReturnStringValuesOfEnum() {
		// when
		String[] values = Utils.getStringValues(EnumExample.class);
		
		// then
		assertArrayEquals(new String[] {"A", "Bb", "Ccc"}, values);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenEnumIsNull() {
		Utils.getStringValues(null);
	}
	
	@Test
	public void shouldReturnEmptyArrayWhenEnumIsEmpty() {
		// when
		String[] values = Utils.getStringValues(EmptyEnumExample.class);
		
		// then
		assertEquals(0, values.length);
		assertArrayEquals(new String[] {}, values);
	}
	
	@Test
	public void shouldReturnStringValuesOfAdvancedEnum() {
		// when
		String[] values = Utils.getStringValues(AdvancedEnumExample.class);
		
		// then
		assertArrayEquals(new String[] {"Aa", "Bb", "Cc"}, values);
	}
	
	// removeNullElements
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenListIsNull() {
		Utils.removeNullElements(null);
	}
	
	@Test
	public void shouldReturnEmptyListWhenListIsEmpty() {
		// given
		List<String> list = new ArrayList<>();
		
		// when
		List<String> returnedList = Utils.removeNullElements(list);
		
		// then
		assertTrue(returnedList.isEmpty());
	}
	
	@Test
	public void shouldReturnEqualListWhenListHasNoNulls() {
		// given
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		
		// when
		List<String> returnedList = Utils.removeNullElements(list);
		
		// then
		assertEquals(list, returnedList);
	}
	
	@Test
	public void shouldReturnNotTheSameObjectListWhenListHasNoNulls() {
		// given
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		
		// when
		List<String> returnedList = Utils.removeNullElements(list);
		
		// then
		assertNotSame(list, returnedList);
	}
	
	@Test
	public void shouldRemoveAllNullWhenListHasManyNulls() {
		// given
		List<String> list = new ArrayList<>();
		list.add(null);
		list.add("A");
		list.add(null);
		list.add("B");
		list.add("C");
		list.add(null);
		
		List<String> expectedList = new ArrayList<>();
		expectedList.add("A");
		expectedList.add("B");
		expectedList.add("C");
		
		// when
		List<String> returnedList = Utils.removeNullElements(list);
		
		// then
		assertEquals(expectedList, returnedList);
	}
}
