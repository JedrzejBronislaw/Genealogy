package wyswietlanie;

import dane.Osoba;

public class ZDatamiPodSpodemWyswietlanieNazwiska extends Nazwisko {

	private static final int odstep = 3;
	
	@Override
	public void wyswietl(Osoba osoba, int x, int y) {
		String data = genDate(osoba);
		if (data != null)
		{
			g.drawString(genNazwisko(osoba), x, y-odstep-(fm.getAscent()-fm.getDescent()));
			g.drawString(data, x, y);
		} else
			g.drawString(genNazwisko(osoba), x, y);
	}

	@Override
	public int getWysokosc(Osoba osoba) {
//		System.out.println((fm.getAscent()-fm.getDescent()) * (genDate(osoba).equals("")?1:2) + odstep);
		return (fm.getAscent()-fm.getDescent()) * (genDate(osoba)==null?1:2) + odstep;
	}

	@Override
	public int getSzerokosc(Osoba osoba) {
		String data = genDate(osoba);
		return Math.max(fm.stringWidth(genNazwisko(osoba)), (data==null)?0:fm.stringWidth(genDate(osoba)));
	}

	private String genNazwisko(Osoba osoba)
	{
		return osoba.imieNazwisko();
	}
	private String genDate(Osoba osoba)
	{
		String daty;
		String wynik;
		
		wynik = null;
		
		if (osoba.getZyje() != Osoba.Zyje.NIE)
		{
			daty = osoba.getDataUrodzenia().toString();
			if (!daty.isEmpty()) wynik = "(" + osoba.getDataUrodzenia() + ")";
		}
		else
		{
			daty = osoba.getDataUrodzenia().toString() + " - " + osoba.getDataSmierci().toString();
			if (!daty.equals(" - ")) wynik = "(" + daty + ")";
		}
		
		return wynik;
	}
}
