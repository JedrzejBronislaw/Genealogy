package utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest {

	// replacePolishChars
	
	@Test
	public void shouldReplaceLowerPolishCharacters() {
		// given
		String polishChars = "πÊÍ≥ÒÛúøü";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("acelnoszz", withoutPolishChars);
	}

	@Test
	public void shouldReplaceUpperPolishCharacters() {
		// given
		String polishChars = "•∆ £—”åØè";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACELNOSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperPolishCharacters() {
		// given
		String polishChars = "•∆ ≥ÒÛåØè";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACElnoSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedPolishCharsWithOtherChar() {
		// given
		String polishChars = "x•x∆x x≥xÒxÛxåxØxèx";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("xAxCxExlxnxoxSxZxZx", withoutPolishChars);
	}
	
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
}
