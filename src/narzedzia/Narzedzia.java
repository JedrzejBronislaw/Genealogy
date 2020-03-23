package narzedzia;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Narzedzia {
	
	public static String sciezkaFolderuZJarem()
	{
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		String sciezka = null;

		try {
			sciezka = URLDecoder.decode(jarDir.getAbsolutePath(), "UTF-8");
			sciezka = sciezka+File.separator;//"/";//"\\";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};

		return sciezka;
	}
	
	public static String usunPolskieZnaki(String tekst)
	{
		tekst = tekst.replace("�", "a");
		tekst = tekst.replace("�", "e");
		tekst = tekst.replace("�", "s");
		tekst = tekst.replace("�", "c");
		tekst = tekst.replace("�", "l");
		tekst = tekst.replace("�", "o");
		tekst = tekst.replace("�", "z");
		tekst = tekst.replace("�", "z");
		tekst = tekst.replace("�", "n");

		tekst = tekst.replace("�", "A");
		tekst = tekst.replace("�", "E");
		tekst = tekst.replace("�", "S");
		tekst = tekst.replace("�", "C");
		tekst = tekst.replace("�", "L");
		tekst = tekst.replace("�", "O");
		tekst = tekst.replace("�", "Z");
		tekst = tekst.replace("�", "Z");
		tekst = tekst.replace("�", "N");
				
		return tekst;
	}
}
