package utils;

import lombok.AllArgsConstructor;

public class DiacriticUtils {
	
	@AllArgsConstructor
	private static class DiacriticPair {
		private String diacritic;
		private String nonDiacritic;
	}
	
	private static DiacriticPair[] polishDiacriticAlphabet = new DiacriticPair[] {
			new DiacriticPair("�", "a"),
			new DiacriticPair("�", "e"),
			new DiacriticPair("�", "s"),
			new DiacriticPair("�", "c"),
			new DiacriticPair("�", "l"),
			new DiacriticPair("�", "o"),
			new DiacriticPair("�", "z"),
			new DiacriticPair("�", "z"),
			new DiacriticPair("�", "n"),

			new DiacriticPair("�", "A"),
			new DiacriticPair("�", "E"),
			new DiacriticPair("�", "S"),
			new DiacriticPair("�", "C"),
			new DiacriticPair("�", "L"),
			new DiacriticPair("�", "O"),
			new DiacriticPair("�", "Z"),
			new DiacriticPair("�", "Z"),
			new DiacriticPair("�", "N"),
		};
		
	public static String replacePolishChars(String text) {
		for (DiacriticPair dp : polishDiacriticAlphabet)
			text = text.replace(dp.diacritic, dp.nonDiacritic);
		
		return text;
	}
}
