package utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static String replacePolishChars(String text)
	{
		text = text.replace("π", "a");
		text = text.replace("Í", "e");
		text = text.replace("ú", "s");
		text = text.replace("Ê", "c");
		text = text.replace("≥", "l");
		text = text.replace("Û", "o");
		text = text.replace("ü", "z");
		text = text.replace("ø", "z");
		text = text.replace("Ò", "n");

		text = text.replace("•", "A");
		text = text.replace(" ", "E");
		text = text.replace("å", "S");
		text = text.replace("∆", "C");
		text = text.replace("£", "L");
		text = text.replace("”", "O");
		text = text.replace("è", "Z");
		text = text.replace("Ø", "Z");
		text = text.replace("—", "N");
				
		return text;
	}
	
	public static String[] getStringValues(Class<? extends Enum<?>> enumClass) {
		Enum<?>[] rawValues = enumClass.getEnumConstants();
		String[] strValues = new String[rawValues.length];
		
		for(int i=0; i<rawValues.length; i++)
			strValues[i] = rawValues[i].name();
		
		return strValues;
	}
	
	public static <T> List<T> removeNullElements(List<T> list) {
		List<T> listWithoutNulls = new ArrayList<>();
		
		list.forEach(element -> {
			if (element != null)
				listWithoutNulls.add(element);
			});
		list = listWithoutNulls;
		
		return list;
	}
}
