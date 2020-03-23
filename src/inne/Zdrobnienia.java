package inne;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Zdrobnienia {

	private static Zdrobnienia wbudowane = new Zdrobnienia("zasoby/zdrobnienia.ust", true);
	private HashMap<String, String> zdrobnienia = new HashMap<String, String>();
	
	
	private Zdrobnienia(String sciezka, boolean zasoby) {
		if (!ladujZZasobow(sciezka)) System.out.println("brak zasobu");
	}
	
	public Zdrobnienia(String sciezka) {
		ladujPlik(sciezka);
	}
	
	private boolean ladujPlik(String sciezka)
	{
		return analizaPliku(new File(sciezka));
	}
	
	private boolean ladujZZasobow(String sciezka)
	{
		ClassLoader loader = getClass().getClassLoader();
		File plik;
		
		try {
			plik = new File(loader.getResource(sciezka).getFile());
		} catch (NullPointerException e)
		{
			return false;
		}
		
		return analizaPliku(plik);
	}

	private boolean analizaPliku(File plik)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(plik);
		} catch (FileNotFoundException e) {
			return false;
		}
		
		String linia;
		String[] podzial;
		
		while(scanner.hasNextLine())
		{
			linia = scanner.nextLine();
			podzial = linia.split("[|]");
			if (podzial.length > 1)
				zdrobnienia.put(podzial[0], podzial[1]);
		}
		scanner.close();
		return true;		
	}
	
	public static String dlaImieniaW(String imie) {
		return wbudowane.dlaImienia(imie);
	}
	public String dlaImienia(String imie) {
		String zdrob = zdrobnienia.get(imie);
		return (zdrob != null) ? zdrob : "";
	}
}
