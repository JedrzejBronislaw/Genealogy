package dane;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Drzewo {

//	List<Osoba> osoby = new ArrayList<Osoba>();
	private Map<String, Osoba> osoby = new TreeMap<String, Osoba>();
	private Date ostatnieOtwarcie;
	private Date ostatniaZmiana;
	private int liczbaOsob;
	private List<String> glowneNazwiska = new ArrayList<String>();
	
	public Date getOstatnieOtwarcie() {
		return ostatnieOtwarcie;
	}

	public void setOstatnieOtwarcie(Date ostatnieOtwarcie) {
		this.ostatnieOtwarcie = ostatnieOtwarcie;
	}

	public Date getOstatniaZmiana() {
		return ostatniaZmiana;
	}

	public void setOstatniaZmiana(Date ostatniaZmiana) {
		this.ostatniaZmiana = ostatniaZmiana;
	}

	public int getLiczbaOsob() {
		return liczbaOsob;
	}

	public void setLiczbaOsob(int liczbaOsob) {
		this.liczbaOsob = liczbaOsob;
	}

	public String[] getGlowneNazwiska() {
		String[] wynik = new String[glowneNazwiska.size()];
		wynik = glowneNazwiska.toArray(wynik);
		return wynik;
	}

	public void dodajGlowneNazwisko(String glowneNazwisko) {
		glowneNazwiska.add(glowneNazwisko);
	}

	
	
	public void dodajOsobe(String id, Osoba osoba)
	{
		osoby.put(id, osoba);
	}
	
	public Osoba getOsoba(String id)
	{
		return osoby.get(id);
	}
	
	public int liczbaOsob()
	{
		return osoby.size();
	}
	
	public String[] getIdentyfikatory()
	{
		String[] wynik = new String[osoby.size()];
		return osoby.keySet().toArray(wynik);
	}
	
	public Osoba losowaOsoba()
	{
		String[] idy = getIdentyfikatory();
		Random r = new Random();
		
		return osoby.get(idy[r.nextInt(idy.length)]);
	}
	
	public ArrayList<Osoba> liscie()
	{
		String[] idy = getIdentyfikatory();
		ArrayList<Osoba> wynik = new ArrayList<Osoba>();
		Osoba temp;
		
		for (int i=0; i<idy.length; i++)
		{
			temp = osoby.get(idy[i]);
			if (temp.liczbaDzieci() == 0)
				wynik.add(temp);
		}
		
		return wynik;
	}

	public ArrayList<Osoba> korzenie()
	{
		return korzenie(false);
	}
	public ArrayList<Osoba> korzenie(boolean malzonekTez)
	{
		String[] idy = getIdentyfikatory();
		ArrayList<Osoba> wynik = new ArrayList<Osoba>();
		Osoba temp;
		boolean malKorzen;
		
		for (int i=0; i<idy.length; i++)
		{
			temp = osoby.get(idy[i]);
			if ((temp.getMatka() == null) && (temp.getOjciec() == null))
			{
				malKorzen = true;
				if (malzonekTez)
				for (int j=0; j<temp.liczbaMalzenstw(); j++)
					if ((temp.getMalzonek(j).getMatka() != null) || (temp.getMalzonek(j).getOjciec() != null))
					{
						malKorzen = false;
						break;
					}
				if (malKorzen) wynik.add(temp);
			}
		}
		
		return wynik;
	}

	public Osoba[] getWszyscy() {
		Osoba[] wynik = new Osoba[osoby.size()];

		wynik = osoby.values().toArray(wynik);
		
		return wynik;
	}
	
}
