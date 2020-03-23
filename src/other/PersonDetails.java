package other;

import model.MyDate;
import model.Person;
import model.Person.Plec;

public class PersonDetails {
	
	public static String czyjeDziecko(Person o)
	{
		if ((o.getOjciec() == null) && (o.getMatka() == null)) return "";
		
		String wynik;
		if (o.getPlec() == Plec.Kobieta)
			wynik = "c�rka ";
		else if (o.getPlec() == Plec.Mezczyna)
			wynik = "syn ";
		else
			wynik = "dziecko ";
		
		wynik += InflectionPL.dopelniaczImienia(o.getOjciec());
		if ((o.getOjciec() != null) && (o.getMatka() != null))
			wynik += " i ";
		wynik += InflectionPL.dopelniaczImienia(o.getMatka());
		
			
		return wynik;
	}

	public static int[] wiek(Person osoba) {		
		return MyDate.teraz().roznica(osoba.getDataUrodzenia());
	}
	
	public static String wiekStr(Person osoba) {
		int[] wiek = wiek(osoba);
		
		if (wiek == null)
			return "";
		else
			return wiek[2] + " lat " + wiek[1] + " miesi�cy " + wiek[0] + " dni";
	}
	
	/**
	 * TODO
	 * Dla bezdzietnej osoby szerokosc = 1
	 * Dla miej�cej dzieci, ale nie wnuki szerokosc = licza dzieci
	 * Og�lnie: maksymalna liczba osob w pokoleniu potomk�w (wersja z zarastaniem i bez ma��onk�w)
	 * teraz jest inna wersja - bez zarastania 
	 * @return
	 */
	public static int szerokoscGaleziPotomkow(Person osoba){
		
		if (osoba.liczbaDzieci() > 0)
		{
			int wynik = 0;
			for (int i=0; i<osoba.liczbaDzieci(); i++)
				wynik += PersonDetails.szerokoscGaleziPotomkow(osoba.getDziecko(i));
			return wynik;
		} else
			return 1;
	}
	
}
