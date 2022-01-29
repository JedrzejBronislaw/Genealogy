package utils.diacritic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DiacriticUtilsTest {

	// replacePolishChars
	
	@Test
	public void shouldReplaceLowerPolishCharacters() {
		// given
		String polishChars = "����󜿟";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("acelnoszz", withoutPolishChars);
	}

	@Test
	public void shouldReplaceUpperPolishCharacters() {
		// given
		String polishChars = "��ʣ�ӌ��";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACELNOSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperPolishCharacters() {
		// given
		String polishChars = "��ʳ�󌯏";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACElnoSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedPolishCharsWithOtherChar() {
		// given
		String polishChars = "x�x�x�x�x�x�x�x�x�x";
		
		// when
		String withoutPolishChars = DiacriticUtils.replacePolishChars(polishChars);
		
		// then
		assertEquals("xAxCxExlxnxoxSxZxZx", withoutPolishChars);
	}

	@Test
	public void shouldHandleSentence_Zazolc_gesla_jazn() {
		// given
		String sentence         = "Za��� g�l� ja��";
		String expectedSentence = "Zazolc gesla jazn";
		
		// when
		String returnedSentence = DiacriticUtils.replacePolishChars(sentence);
		
		// then
		assertEquals(expectedSentence, returnedSentence);
	}

	// replaceGermanChars
	
	@Test
	public void shouldReplaceLowerGermanCharacters() {
		// given
		String germanChars = "���";
		
		// when
		String withoutGermanChars = DiacriticUtils.replaceGermanChars(germanChars);
		
		// then
		assertEquals("aeueoe", withoutGermanChars);
	}

	@Test
	public void shouldReplaceUpperGermanCharacters() {
		// given
		String germanChars = "���";
		
		// when
		String withoutGermanChars = DiacriticUtils.replaceGermanChars(germanChars);
		
		// then
		assertEquals("AEUEOE", withoutGermanChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperGermanCharacters() {
		// given
		String germanChars = "���";
		
		// when
		String withoutGermanChars = DiacriticUtils.replaceGermanChars(germanChars);
		
		// then
		assertEquals("aeUEoe", withoutGermanChars);
	}
	
	//

	@Test
	public void shouldReplaceMixedPolishAndGermanCharacters() {
		// given
		String diacriticChars = "������";
		
		// when
		String returnedChars = DiacriticUtils.replaceDiacriticChars(diacriticChars);
		
		// then
		assertEquals("ACEaeueoe", returnedChars);
	}
}
