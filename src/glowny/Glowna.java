package glowny;

import java.awt.EventQueue;
import java.io.FileNotFoundException;

import narzedzia.Akcja;
import narzedzia.Ustawienia;

import okna.EkranWizytowka;
import okna.Okno;
import okna.OknoGlowne;
import okna.OknoWyborPliku;

import dane.Drzewo;
import dane.Osoba;
import dane.PlikPGL;

public class Glowna {

	static OknoGlowne oknoGlowne;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		pokazOkno();
		if (!Ustawienia.zaladuj()) System.out.println("brak ustawieñ");
	}
	
	public static void pokazOkno()
	{
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
//				pokazOknoWyporuPliku();
				pokazOknoGlowne("../drzewo.pgl");
			}
			
			public void pokazOknoWyporuPliku()
			{
				final OknoWyborPliku okno = new OknoWyborPliku();
				okno.setCoPo(new Akcja() {
					
					@Override
					public void start() {
		
						String sciezka = okno.getSciezka();
						if (sciezka == null) return;
						
						pokazOknoGlowne(sciezka);
					}
				});				
			}
			
			public boolean pokazOknoGlowne(String sciezka)
			{
				Drzewo d = ladujZPLiku(sciezka);
				if (d == null) return false;
				
				oknoGlowne = new OknoGlowne();
				oknoGlowne.setDrzewo(d);
				return true;
			}
		});
	}
	
	public static void fun()
	{
		System.out.println("funkcja");
	}
	
	private static Drzewo ladujZPLiku(String sciezka)
	{
		Drzewo drzewo = new Drzewo();
		boolean sukces = false;
		
		try {
			PlikPGL plikPGL = new PlikPGL(sciezka);
			sukces = plikPGL.laduj(drzewo);
		} catch (FileNotFoundException e) {
			System.out.println("Nie znaleziono pliku (" + sciezka + ").");
			return null;
		}		
		
		return drzewo;
	}
	
	public static Drzewo test(boolean wyswietl)
	{
		Drzewo drzewo = new Drzewo();
		int liczbaOsob;
		String[] identyfikatory;
		boolean sukces = false;
//		String sciezka = "..\\..\\przyk³adowe drzewa\\";
		String nazwaPliku = "drzwo.pgl";
		String sciezka = "";
		
		try {
			PlikPGL plikPGL = new PlikPGL(sciezka+nazwaPliku);
			sukces = plikPGL.laduj(drzewo);
		} catch (FileNotFoundException e) {
			System.out.println("Nie znaleziono pliku (" + sciezka+nazwaPliku + ").");
			return null;
		}
		
		if (wyswietl)
		{
			if (sukces)
				System.out.println("Za³adowano poprawnie.");
			else
				System.out.println("Wyst¹pi³ b³¹d podczas ³adowania danych.");
			
			System.out.println();
			
			liczbaOsob = drzewo.liczbaOsob();
			System.out.println("Liczba osób w drzewie: " + liczbaOsob);
			System.out.println("\n\t-----\n");
			identyfikatory = drzewo.getIdentyfikatory();
			Osoba o;
			for (int i=0; i<liczbaOsob; i++)
			{
				o = drzewo.getOsoba(identyfikatory[i]);
				System.out.println(identyfikatory[i]);
				System.out.println(o.imieNazwisko());
				System.out.println("p³eæ: " + o.getPlec());
				System.out.println(o.getDataUrodzenia() + " - " + o.getDataSmierci());
				System.out.println(o.getMiejsceUrodzenia() + " - " + o.getMiejsceSmierci());
				System.out.println("¿yje: " + o.getZyje());
				
				System.out.print("Ojciec: ");
				if (o.getOjciec() != null)
					System.out.print(o.getOjciec().imieNazwisko());
				System.out.println();
				
				System.out.print("Matka: ");
				if (o.getMatka() != null)
					System.out.print("Matka: " + o.getMatka().imieNazwisko());
				System.out.println();
				
				System.out.println("Dzieci (" + o.liczbaDzieci() + "):");
				for (int j=0; j<o.liczbaDzieci(); j++)
					System.out.println("\t" + (j+1) + ". " + o.getDziecko(j).imieNazwisko());
				
				System.out.println("Œluby (" + o.liczbaMalzenstw() + "):");
				for (int j=0; j<o.liczbaMalzenstw(); j++)
					System.out.println("\t" + (j+1) + ". " + o.getMalzonek(j).imieNazwisko());
				
				System.out.println("\t-----");
			}
		}
		return drzewo;
	}

}
