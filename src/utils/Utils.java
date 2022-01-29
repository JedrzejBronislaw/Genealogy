package utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;

public class Utils {
	
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
	
	public static String[] getStringValues(Class<? extends Enum<?>> enumClass) {
		if (enumClass == null)
			throw new IllegalArgumentException("Enum class must be set.");
		
		return Stream.of(enumClass.getEnumConstants())
				.map(Enum::name)
				.toArray(String[]::new);
	}
	
	public static <T> List<T> removeNullElements(List<T> list) {
		if (list == null)
			throw new IllegalArgumentException("List must be not null");
		
		return list.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
