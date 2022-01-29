package utils.diacritic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DiacriticUtilsTest {

	// replacePolishChars
	
	@Test
	public void shouldReplaceLowerPolishCharacters() {
		// given
		String polishChars = "¹æê³ñóœ¿Ÿ";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("acelnoszz", withoutPolishChars);
	}

	@Test
	public void shouldReplaceUpperPolishCharacters() {
		// given
		String polishChars = "¥ÆÊ£ÑÓŒ¯";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACELNOSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperPolishCharacters() {
		// given
		String polishChars = "¥ÆÊ³ñóŒ¯";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACElnoSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedPolishCharsWithOtherChar() {
		// given
		String polishChars = "x¥xÆxÊx³xñxóxŒx¯xx";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("xAxCxExlxnxoxSxZxZx", withoutPolishChars);
	}

	@Test
	public void shouldHandleSentence_Zazolc_gesla_jazn() {
		// given
		String sentence         = "Za¿ó³æ gêœl¹ jaŸñ";
		String expectedSentence = "Zazolc gesla jazn";
		
		// when
		String returnedSentence = DiacriticUtils.replacePolishChars(sentence);
		
		// then
		assertEquals(expectedSentence, returnedSentence);
	}
}
