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
		
		wynik = osoba.nameSurname();
		
		if (osoba.getLifeStatus() != Person.LifeStatus.NO)
		{
			daty = osoba.getBirthDate().toString();
			if (!daty.isEmpty()) wynik += " (" + osoba.getBirthDate() + ")";
		}
		else
		{
			daty = osoba.getBirthDate().toString() + " - " + osoba.getDeathDate().toString();
			if (!daty.equals(" - ")) wynik += " (" + daty + ")";
		}
		
		return wynik;
	}
}
