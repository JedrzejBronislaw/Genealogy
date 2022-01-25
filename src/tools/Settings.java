package tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Settings {

	public static final String sciezkaPliku = "ustawienia.ust";

	private static BufferedReader brPlik;
	private static ArrayList<String> ostatnioOtwarte = new ArrayList<String>();
	
	public static String[] getOstatnioOtwarte()
	{
		String[] tab = new String[ostatnioOtwarte.size()];
		ostatnioOtwarte.toArray(tab);
		return tab;
	}
	
	private static void otworzPlik(String sciezka) throws FileNotFoundException
	{
		FileInputStream fis;
		DataInputStream dis;
		
		if (sciezka.charAt(1) != ':')
			sciezka = PathUtils.JAR_PATH + sciezka;
		
		fis = new FileInputStream(sciezka);
		dis = new DataInputStream(fis);
		
		brPlik = new BufferedReader(new InputStreamReader(dis));
	}
	
	public static boolean zaladuj()
	{
		HashMap<String, String> pola = new HashMap<String, String>();

		buforuj(pola);
		wypelnij(pola);
		
		return true;
	}

	private static boolean buforuj(HashMap<String, String> pola)
	{
		String linia;
		String[] podzial;
		
		try {
			otworzPlik(sciezkaPliku);
		} catch (FileNotFoundException e) {
			return false;
		}
		
		try {
			linia = brPlik.readLine();
			while (linia != null)
			{
				linia = linia.trim();
				podzial = linia.split("=", 2);
				
				pola.put(podzial[0], podzial[1]);
				
				linia = brPlik.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	
	private static void wypelnij(HashMap<String, String> pola) {
		String wartosc;
		
		//scie¿ki ostatnio otwartych plików
		for (int i=1; i<=10; i++)
		{
			wartosc = pola.get("oo"+i);
			if (wartosc != null) ostatnioOtwarte.add(wartosc);
		}
	}

	public boolean zapisz()
	{
		
		
		return true;//TODO uzale¿niæ zwracan¹ wartoœæ od powodzenia zapisu
	}
}
