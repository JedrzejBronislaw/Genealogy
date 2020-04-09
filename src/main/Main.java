package main;

import java.awt.EventQueue;
import java.io.FileNotFoundException;

import model.PGLFile;
import model.Person;
import model.Tree;
import tools.Action;
import tools.Settings;
import windows.FileChooseWindow;
import windows.MainWindow;

public class Main {

	static MainWindow oknoGlowne;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pokazOkno();
		if (!Settings.zaladuj()) System.out.println("brak ustawieñ");
	}
	
	public static void pokazOkno()
	{
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
//				pokazOknoWyporuPliku();
				pokazOknoGlowne("tree.pgl");
			}
			
			public void pokazOknoWyporuPliku()
			{
				final FileChooseWindow okno = new FileChooseWindow();
				okno.setCoPo(new Action() {
					
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
				Tree d = ladujZPLiku(sciezka);
				if (d == null) return false;
				
				oknoGlowne = new MainWindow();
				oknoGlowne.setDrzewo(d);
				return true;
			}
		});
	}
	
	public static void fun()
	{
		System.out.println("funkcja");
	}
	
	private static Tree ladujZPLiku(String sciezka)
	{
		Tree drzewo = new Tree();
		boolean sukces = false;
		
		try {
			PGLFile plikPGL = new PGLFile(sciezka);
			sukces = plikPGL.laduj(drzewo);
		} catch (FileNotFoundException e) {
			System.out.println("Nie znaleziono pliku (" + sciezka + ").");
			return null;
		}		
		
		return drzewo;
	}
	
	public static Tree test(boolean wyswietl)
	{
		Tree drzewo = new Tree();
		int liczbaOsob;
		String[] identyfikatory;
		boolean sukces = false;
//		String sciezka = "..\\..\\przyk³adowe drzewa\\";
		String nazwaPliku = "drzwo.pgl";
		String sciezka = "";
		
		try {
			PGLFile plikPGL = new PGLFile(sciezka+nazwaPliku);
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
			
			liczbaOsob = drzewo.numberOfPersons();
			System.out.println("Liczba osób w drzewie: " + liczbaOsob);
			System.out.println("\n\t-----\n");
			identyfikatory = drzewo.getIDs();
			Person o;
			for (int i=0; i<liczbaOsob; i++)
			{
				o = drzewo.getPerson(identyfikatory[i]);
				System.out.println(identyfikatory[i]);
				System.out.println(o.nameSurname());
				System.out.println("p³eæ: " + o.getSex());
				System.out.println(o.getBirthDate() + " - " + o.getDeathDate());
				System.out.println(o.getBirthPlace() + " - " + o.getDeathPlace());
				System.out.println("¿yje: " + o.getLifeStatus());
				
				System.out.print("Ojciec: ");
				if (o.getFather() != null)
					System.out.print(o.getFather().nameSurname());
				System.out.println();
				
				System.out.print("Matka: ");
				if (o.getMother() != null)
					System.out.print("Matka: " + o.getMother().nameSurname());
				System.out.println();
				
				System.out.println("Dzieci (" + o.numberOfChildren() + "):");
				for (int j=0; j<o.numberOfChildren(); j++)
					System.out.println("\t" + (j+1) + ". " + o.getChild(j).nameSurname());
				
				System.out.println("Œluby (" + o.numberOfMarriages() + "):");
				for (int j=0; j<o.numberOfMarriages(); j++)
					System.out.println("\t" + (j+1) + ". " + o.getSpouse(j).nameSurname());
				
				System.out.println("\t-----");
			}
		}
		return drzewo;
	}

}
