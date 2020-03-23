package nameDisplaying;

import model.Person;

public class SimpleNameDisplaying extends Name{

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
		return osoba.imieNazwisko();
	}

}
