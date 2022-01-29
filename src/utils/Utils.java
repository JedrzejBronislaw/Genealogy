package utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
	
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
