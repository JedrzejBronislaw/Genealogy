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
		tekst = tekst.replace("π", "a");
		tekst = tekst.replace("Í", "e");
		tekst = tekst.replace("ú", "s");
		tekst = tekst.replace("Ê", "c");
		tekst = tekst.replace("≥", "l");
		tekst = tekst.replace("Û", "o");
		tekst = tekst.replace("ü", "z");
		tekst = tekst.replace("ø", "z");
		tekst = tekst.replace("Ò", "n");

		tekst = tekst.replace("•", "A");
		tekst = tekst.replace(" ", "E");
		tekst = tekst.replace("å", "S");
		tekst = tekst.replace("∆", "C");
		tekst = tekst.replace("£", "L");
		tekst = tekst.replace("”", "O");
		tekst = tekst.replace("è", "Z");
		tekst = tekst.replace("Ø", "Z");
		tekst = tekst.replace("—", "N");
				
		return tekst;
	}
}
