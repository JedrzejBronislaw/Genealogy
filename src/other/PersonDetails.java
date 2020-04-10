package other;

import model.MyDate;
import model.Person;
import model.Person.Sex;

public class PersonDetails {
	
	public static String czyjeDziecko(Person o)
	{
		if ((o.getFather() == null) && (o.getMother() == null)) return "";
		
		String wynik;
		if (o.getSex() == Sex.WOMEN)
			wynik = "córka ";
		else if (o.getSex() == Sex.MAN)
			wynik = "syn ";
		else
			wynik = "dziecko ";
		
		wynik += InflectionPL.dopelniaczImienia(o.getFather());
		if ((o.getFather() != null) && (o.getMother() != null))
			wynik += " i ";
		wynik += InflectionPL.dopelniaczImienia(o.getMother());
		
			
		return wynik;
	}

	public static int[] wiek(Person osoba) {		
		return MyDate.now().difference(osoba.getBirthDate());
	}
	
	public static String wiekStr(Person osoba) {
		int[] wiek = wiek(osoba);
		
		if (wiek == null)
			return "";
		else
			return wiek[2] + " lat " + wiek[1] + " miesiêcy " + wiek[0] + " dni";
	}
	
	/**
	 * TODO
	 * Dla bezdzietnej osoby szerokosc = 1
	 * Dla miej¹cej dzieci, ale nie wnuki szerokosc = licza dzieci
	 * Ogólnie: maksymalna liczba osob w pokoleniu potomków (wersja z zarastaniem i bez ma³¿onków)
	 * teraz jest inna wersja - bez zarastania 
	 * @return
	 */
	public static int szerokoscGaleziPotomkow(Person osoba){
		
		if (osoba.numberOfChildren() > 0)
		{
			int wynik = 0;
			for (int i=0; i<osoba.numberOfChildren(); i++)
				wynik += PersonDetails.szerokoscGaleziPotomkow(osoba.getChild(i));
			return wynik;
		} else
			return 1;
	}
	
}
