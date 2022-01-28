package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void shouldReplaceLowerPolishCharacters() {
		// given
		String polishChars = "����󜿟";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("acelnoszz", withoutPolishChars);
	}

	@Test
	public void shouldReplaceUpperPolishCharacters() {
		// given
		String polishChars = "��ʣ�ӌ��";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACELNOSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedLowerAndUpperPolishCharacters() {
		// given
		String polishChars = "��ʳ�󌯏";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("ACElnoSZZ", withoutPolishChars);
	}

	@Test
	public void shouldReplaceMixedPolishCharsWithOtherChar() {
		// given
		String polishChars = "x�x�x�x�x�x�x�x�x�x";
		
		// when
		String withoutPolishChars = Utils.replacePolishChars(polishChars);
		
		// then
		assertEquals("xAxCxExlxnxoxSxZxZx", withoutPolishChars);
	}
}
