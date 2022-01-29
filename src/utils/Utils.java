package utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
	
	public static String replacePolishChars(String text) {
		text = text.replace("�", "a");
		text = text.replace("�", "e");
		text = text.replace("�", "s");
		text = text.replace("�", "c");
		text = text.replace("�", "l");
		text = text.replace("�", "o");
		text = text.replace("�", "z");
		text = text.replace("�", "z");
		text = text.replace("�", "n");

		text = text.replace("�", "A");
		text = text.replace("�", "E");
		text = text.replace("�", "S");
		text = text.replace("�", "C");
		text = text.replace("�", "L");
		text = text.replace("�", "O");
		text = text.replace("�", "Z");
		text = text.replace("�", "Z");
		text = text.replace("�", "N");
				
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
