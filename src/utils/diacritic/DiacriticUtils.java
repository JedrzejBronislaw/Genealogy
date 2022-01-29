package utils.diacritic;

import java.util.List;

public class DiacriticUtils {
	
	public static final DiacriticAlphabet POLISH = new DiacriticAlphabet(
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
	
	public static final DiacriticAlphabet GERMAN = new DiacriticAlphabet(
			new DiacriticPair("‰", "ae"),
			new DiacriticPair("ˆ", "oe"),
			new DiacriticPair("¸", "ue"),

			new DiacriticPair("ƒ", "AE"),
			new DiacriticPair("÷", "OE"),
			new DiacriticPair("‹", "UE")
	);
	
	private static final List<DiacriticAlphabet> ALL_ALPHABETS = List.of(POLISH, GERMAN);

	
	public static String replaceDiacriticChars(String text) {
		for (DiacriticAlphabet alphabet : ALL_ALPHABETS)
			text = replaceDiacriticChars(text, alphabet);

		return text;
	}

	public static String replaceDiacriticChars(String text, DiacriticAlphabet alphabet) {
		return alphabet.replaceDiacriticChars(text);
	}
}
