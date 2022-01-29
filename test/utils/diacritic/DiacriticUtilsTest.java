package utils.diacritic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static utils.diacritic.DiacriticUtils.POLISH;
import static utils.diacritic.DiacriticUtils.GERMAN;

public class DiacriticUtilsTest {

	// replacePolishChars
	
	@Test
	public void shouldReplaceLowerPolishCharacters() {
		// given
		String polishChars = "πÊÍ≥ÒÛúøü";
		
		// when
		String withoutPolishChars = DiacriticUtils.replaceDiacriticChars(polishChars, POLISH);
		
		// then
		assertEquals("acelnoszz", withoutPolishChars);
	}

	@Test
	public void shouldReplaceUpperPolishCharacters() {
		// given
		String polishChars = "•∆ £—”åØè";
		
		// when
		String withoutPolishChars = DiacriticUtils.replaceDiacriticChars(polishChars, POLISH);
		
		// then
		assertEquals("ACELNOSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperPolishCharacters() {
		// given
		String polishChars = "•∆ ≥ÒÛåØè";
		
		// when
		String withoutPolishChars = DiacriticUtils.replaceDiacriticChars(polishChars, POLISH);
		
		// then
		assertEquals("ACElnoSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedPolishCharsWithOtherChar() {
		// given
		String polishChars = "x•x∆x x≥xÒxÛxåxØxèx";
		
		// when
		String withoutPolishChars = DiacriticUtils.replaceDiacriticChars(polishChars, POLISH);
		
		// then
		assertEquals("xAxCxExlxnxoxSxZxZx", withoutPolishChars);
	}

	@Test
	public void shouldHandleSentence_Zazolc_gesla_jazn() {
		// given
		String sentence         = "ZaøÛ≥Ê gÍúlπ jaüÒ";
		String expectedSentence = "Zazolc gesla jazn";
		
		// when
		String returnedSentence = DiacriticUtils.replaceDiacriticChars(sentence, POLISH);
		
		// then
		assertEquals(expectedSentence, returnedSentence);
	}

	// replaceGermanChars
	
	@Test
	public void shouldReplaceLowerGermanCharacters() {
		// given
		String germanChars = "‰¸ˆ";
		
		// when
		String withoutGermanChars = DiacriticUtils.replaceDiacriticChars(germanChars, GERMAN);
		
		// then
		assertEquals("aeueoe", withoutGermanChars);
	}

	@Test
	public void shouldReplaceUpperGermanCharacters() {
		// given
		String germanChars = "ƒ‹÷";
		
		// when
		String withoutGermanChars = DiacriticUtils.replaceDiacriticChars(germanChars, GERMAN);
		
		// then
		assertEquals("AEUEOE", withoutGermanChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperGermanCharacters() {
		// given
		String germanChars = "‰‹ˆ";
		
		// when
		String withoutGermanChars = DiacriticUtils.replaceDiacriticChars(germanChars, GERMAN);
		
		// then
		assertEquals("aeUEoe", withoutGermanChars);
	}
	
	//

	@Test
	public void shouldReplaceMixedPolishAndGermanCharacters() {
		// given
		String diacriticChars = "•∆ ‰¸ˆ";
		
		// when
		String returnedChars = DiacriticUtils.replaceDiacriticChars(diacriticChars);
		
		// then
		assertEquals("ACEaeueoe", returnedChars);
	}
}
