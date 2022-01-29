package utils.diacritic;

public class DiacriticUtils {
	
	private static final DiacriticAlphabet POLISH = new DiacriticAlphabet(
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
			new DiacriticPair("—", "N")
	);
	
	private static final DiacriticAlphabet GERMAN = new DiacriticAlphabet(
			new DiacriticPair("‰", "a"),
			new DiacriticPair("ˆ", "o"),
			new DiacriticPair("¸", "u"),

			new DiacriticPair("ƒ", "A"),
			new DiacriticPair("÷", "O"),
			new DiacriticPair("‹", "U")
	);
		
	public static String replacePolishChars(String text) {
		return POLISH.replaceDiacriticLetters(text);
	}
	
	public static String replaceGermanChars(String text) {
		return GERMAN.replaceDiacriticLetters(text);
	}
}
