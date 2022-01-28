package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void shouldReplaceLowerPolishCharacters() {
		// given
		String polishChars = "¹æê³ñóœ¿Ÿ";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("acelnoszz", withoutPolishChars);
	}

	@Test
	public void shouldReplaceUpperPolishCharacters() {
		// given
		String polishChars = "¥ÆÊ£ÑÓŒ¯";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACELNOSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperPolishCharacters() {
		// given
		String polishChars = "¥ÆÊ³ñóŒ¯";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACElnoSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedPolishCharsWithOtherChar() {
		// given
		String polishChars = "x¥xÆxÊx³xñxóxŒx¯xx";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("xAxCxExlxnxoxSxZxZx", withoutPolishChars);
	}
}
