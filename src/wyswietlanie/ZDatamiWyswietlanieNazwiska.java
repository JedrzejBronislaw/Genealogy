package wyswietlanie;

import dane.Osoba;

public class ZDatamiWyswietlanieNazwiska extends Nazwisko{
	
	@Override
	public void wyswietl(Osoba osoba, int x, int y) {
		g.drawString(genTekst(osoba), x, y);
	}

	@Override
	public int getWysokosc(Osoba osoba) {
		return fm.getAscent()-fm.getDescent();
	}

	@Override
	public int getSzerokosc(Osoba osoba) {
		return fm.stringWidth(genTekst(osoba));
	}
	
	private String genTekst(Osoba osoba)
	{
		String daty;
		String wynik;
		
		wynik = osoba.imieNazwisko();
		
		if (osoba.getZyje() != Osoba.Zyje.NIE)
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
