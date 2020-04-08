package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Tree {

//	List<Osoba> osoby = new ArrayList<Osoba>();
	private Map<String, Person> osoby = new TreeMap<String, Person>();
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

	
	
	public void dodajOsobe(String id, Person osoba)
	{
		osoby.put(id, osoba);
	}
	
	public Person getOsoba(String id)
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
	
	public Person losowaOsoba()
	{
		String[] idy = getIdentyfikatory();
		Random r = new Random();
		
		return osoby.get(idy[r.nextInt(idy.length)]);
	}
	
	public ArrayList<Person> liscie()
	{
		String[] idy = getIdentyfikatory();
		ArrayList<Person> wynik = new ArrayList<Person>();
		Person temp;
		
		for (int i=0; i<idy.length; i++)
		{
			temp = osoby.get(idy[i]);
			if (temp.numberOfChildren() == 0)
				wynik.add(temp);
		}
		
		return wynik;
	}

	public ArrayList<Person> korzenie()
	{
		return korzenie(false);
	}
	public ArrayList<Person> korzenie(boolean malzonekTez)
	{
		String[] idy = getIdentyfikatory();
		ArrayList<Person> wynik = new ArrayList<Person>();
		Person temp;
		boolean malKorzen;
		
		for (int i=0; i<idy.length; i++)
		{
			temp = osoby.get(idy[i]);
			if ((temp.getMother() == null) && (temp.getFather() == null))
			{
				malKorzen = true;
				if (malzonekTez)
				for (int j=0; j<temp.numberOfMarriages(); j++)
					if ((temp.getSpouse(j).getMother() != null) || (temp.getSpouse(j).getFather() != null))
					{
						malKorzen = false;
						break;
					}
				if (malKorzen) wynik.add(temp);
			}
		}
		
		return wynik;
	}

	public Person[] getWszyscy() {
		Person[] wynik = new Person[osoby.size()];

		wynik = osoby.values().toArray(wynik);
		
		return wynik;
	}
	
}
