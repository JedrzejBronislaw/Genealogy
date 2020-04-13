package tools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Tools {
	
	public static String dirWithJarPath()
	{
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		String path = null;

		try {
			path = URLDecoder.decode(jarDir.getAbsolutePath(), "UTF-8");
			path = path+File.separator;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};

		return path;
	}
	
	public static String removePolishChars(String text)
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
}
