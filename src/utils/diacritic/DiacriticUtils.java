package utils.diacritic;

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
		
	public static String replacePolishChars(String text) {
		return POLISH.replaceDiacriticLetters(text);
	}
	
	public static String replaceGermanChars(String text) {
		return GERMAN.replaceDiacriticLetters(text);
	}
}
