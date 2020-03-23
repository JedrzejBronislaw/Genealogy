package wyswietlanie;

import dane.Osoba;

public class ProsteWyswietlanieNazwiska extends Nazwisko{

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
		return osoba.imieNazwisko();
	}

}
