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
