package utils.diacritic;

import java.util.List;

public class DiacriticUtils {
	
	private static final DiacriticAlphabet POLISH = new DiacriticAlphabet(
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
			new DiacriticPair("�", "N")
	);
	
	private static final DiacriticAlphabet GERMAN = new DiacriticAlphabet(
			new DiacriticPair("�", "a"),
			new DiacriticPair("�", "o"),
			new DiacriticPair("�", "u"),

			new DiacriticPair("�", "A"),
			new DiacriticPair("�", "O"),
			new DiacriticPair("�", "U")
	);
	
	private static final List<DiacriticAlphabet> ALL_ALPHABETS = List.of(POLISH, GERMAN);

	
	public static String replaceDiacriticChars(String text) {
		for (DiacriticAlphabet alphabet : ALL_ALPHABETS)
			text = replaceDiacriticChars(text, alphabet);

		return text;
	}
	
	public static String replacePolishChars(String text) {
		return replaceDiacriticChars(text, POLISH);
	}
	
	public static String replaceGermanChars(String text) {
		return replaceDiacriticChars(text, GERMAN);
	}

	private static String replaceDiacriticChars(String text, DiacriticAlphabet alphabet) {
		return alphabet.replaceDiacriticLetters(text);
	}
}
