package nameDisplaying;

import model.Person;

public class DateAndNameDisplaying extends Name{
	
	@Override
	public void wyswietl(Person osoba, int x, int y) {
		g.drawString(genTekst(osoba), x, y);
	}

	@Override
	public int getWysokosc(Person osoba) {
		return fm.getAscent()-fm.getDescent();
	}

	@Override
	public int getSzerokosc(Person osoba) {
		return fm.stringWidth(genTekst(osoba));
	}
	
	private String genTekst(Person osoba)
	{
		String daty;
		String wynik;
		
		wynik = osoba.imieNazwisko();
		
		if (osoba.getZyje() != Person.Zyje.NIE)
		{
			daty = osoba.getDataUrodzenia().toString();
			if (!daty.isEmpty()) wynik += " (" + osoba.getDataUrodzenia() + ")";
		}
		else
		{
			daty = osoba.getDataUrodzenia().toString() + " - " + osoba.getDataSmierci().toString();
			if (!daty.equals(" - ")) wynik += " (" + daty + ")";
		}
		
		return wynik;
	}
}
