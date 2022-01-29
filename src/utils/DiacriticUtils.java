package utils;

import lombok.AllArgsConstructor;

public class DiacriticUtils {
	
	@AllArgsConstructor
	private static class DiacriticPair {
		private String diacritic;
		private String nonDiacritic;
	}
	
	private static DiacriticPair[] polishDiacriticAlphabet = new DiacriticPair[] {
			new DiacriticPair("π", "a"),
			new DiacriticPair("Í", "e"),
			new DiacriticPair("ú", "s"),
			new DiacriticPair("Ê", "c"),
			new DiacriticPair("≥", "l"),
			new DiacriticPair("Û", "o"),
			new DiacriticPair("ü", "z"),
			new DiacriticPair("ø", "z"),
			new DiacriticPair("Ò", "n"),

			new DiacriticPair("•", "A"),
			new DiacriticPair(" ", "E"),
			new DiacriticPair("å", "S"),
			new DiacriticPair("∆", "C"),
			new DiacriticPair("£", "L"),
			new DiacriticPair("”", "O"),
			new DiacriticPair("è", "Z"),
			new DiacriticPair("Ø", "Z"),
			new DiacriticPair("—", "N"),
		};
		
	public static String replacePolishChars(String text) {
		for (DiacriticPair dp : polishDiacriticAlphabet)
			text = text.replace(dp.diacritic, dp.nonDiacritic);
		
		return text;
	}
}
