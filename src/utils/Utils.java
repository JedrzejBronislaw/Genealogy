package utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static String replacePolishChars(String text)
	{
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
