package model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Person.Plec;
import model.Person.Zyje;
import tools.Tools;

public class PGLFile {

	private class sekcjaINI
	{
		String nazwa;
		private Map<String, String> pola = new HashMap<String, String>();
		
		public sekcjaINI(String nazwa) {
			this.nazwa = nazwa;
		}
		
		public void dodajPole(String klucz, String warosc)
		{
			pola.put(klucz, warosc);
		}
		
		public String getWartosc(String klucz)
		{
			return pola.get(klucz);
		}
	}
	private static class Relacja {
		public enum Typ {Ojciec, Matka, Dziecko, Malzonek};
		String podmiot;
		Typ relacja;
		String dopelnienie;
		int numer;
		String data;
		String miejsce;

		public Relacja(String podmiot, Typ relacja, String dopelnienie) {
			this.podmiot = podmiot;
			this.relacja = relacja;
			this.dopelnienie = dopelnienie;
		}
		public Relacja(String podmiot, Typ relacja, String dopelnienie, int numer) {
			this.podmiot = podmiot;
			this.relacja = relacja;
			this.dopelnienie = dopelnienie;
			this.numer = numer;
		}


		static void dodajDateDoRelacji(List<Relacja> lista, String dopelnienie, Typ relacja, int numer, String data)
		{
			for (Relacja r : lista)
				if ((r.dopelnienie.equals(dopelnienie)) && (r.relacja.equals(relacja)) && (r.numer == numer))
				{
					r.data = data;
					return;
				}
		}

		static void dodajMiejsceDoRelacji(List<Relacja> lista, String dopelnienie, Typ relacja, int numer, String miejsce)
		{
			for (Relacja r : lista)
				if ((r.dopelnienie.equals(dopelnienie)) && (r.relacja.equals(relacja)) && (r.numer == numer))
				{
					r.miejsce = miejsce;
					return;
				}
		}
	}
	
	private DataInputStream plik;
	//private bufferedreader brplik = new 
	BufferedReader brPlik;
    
	
	public PGLFile(String sciezka) throws FileNotFoundException {
		otworzPlik(sciezka);
	}
	
	private void otworzPlik(String sciezka) throws FileNotFoundException
	{
		FileInputStream fis;
		DataInputStream dis;
		
		if (sciezka.charAt(1) != ':')
			sciezka = Tools.sciezkaFolderuZJarem() + sciezka;
		
		fis = new FileInputStream(sciezka);
		dis = new DataInputStream(fis);
		
		plik = dis;		
		brPlik = new BufferedReader(new InputStreamReader(dis));
	}
	
	
	public boolean laduj(Tree d)// throws IOException
	{
		String linia;
		String[] podzial;
		sekcjaINI sekcja = null;
		Person o = null;
		List<Relacja> relacje = new ArrayList<Relacja>();
		
		try{
			linia = brPlik.readLine();
			while (linia != null) {
				linia = linia.trim();
				if	((linia.length() >= 2) &&
					((linia.charAt(0) == '[') && (linia.charAt(linia.length()-1) == ']')))
				{
					//zapis sekcji
					if (sekcja != null)
						if (sekcja.nazwa.toUpperCase().equals("MAIN"))
							ladujMetaDaneDoDrzewa(d, sekcja);
						else
							ladujDaneDoDrzewa(d, sekcja, relacje);
					
					//otwarcie sekcji
					sekcja = new sekcjaINI(linia.substring(1, linia.length()-1));
					
				} else if (sekcja != null)
				{
					podzial = linia.split("=", 2);
					if (podzial.length == 2)
						sekcja.dodajPole(podzial[0], podzial[1]);
				}
				linia = brPlik.readLine();
			}
			ladujDaneDoDrzewa(d, sekcja, relacje);
			
		} catch (IOException e)
		{
			return false;
		}
		
		for (Relacja r:relacje)
		{
			if (r.relacja == Relacja.Typ.Ojciec)	d.getOsoba(r.dopelnienie).setOjciec(d.getOsoba(r.podmiot)); else
			if (r.relacja == Relacja.Typ.Matka)		d.getOsoba(r.dopelnienie).setMatka(d.getOsoba(r.podmiot)); else
			if (r.relacja == Relacja.Typ.Dziecko)	d.getOsoba(r.dopelnienie).dodajDziecko(d.getOsoba(r.podmiot)); else
			if (r.relacja == Relacja.Typ.Malzonek)	
			{
				d.getOsoba(r.dopelnienie).dodajMalzenstwo(d.getOsoba(r.podmiot));
				d.getOsoba(r.dopelnienie).dodajDateSlubu(d.getOsoba(r.podmiot), r.data);
				d.getOsoba(r.dopelnienie).dodajMiejsceSlubu(d.getOsoba(r.podmiot), r.miejsce);
			}
		}
		
		return true;
	}

	private void ladujMetaDaneDoDrzewa(Tree d, sekcjaINI sekcja)
	{
		String w;
		
		w = sekcja.getWartosc("ost_otw");
		if (w != null)
			d.setOstatnieOtwarcie(loadDate(w));
		
		w = sekcja.getWartosc("wersja");
		if (w != null)
			d.setOstatniaZmiana(loadDate(w));
		
		w = sekcja.getWartosc("ile");
		if (w != null)
			try {d.setLiczbaOsob(Integer.parseInt(w));} catch (NumberFormatException e) {}
		
		for (int i=1; i<=10; i++)
			{w = sekcja.getWartosc("nazw"+i);	if ((w != null) && (!w.equals(""))) d.dodajGlowneNazwisko(w);}
	}

	private Date loadDate(String w) {

		final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		
		Date date;
		
		date = tryLoadDate(w, sdf1);
		if(date == null)
			date = tryLoadDate(w, sdf2);

		return date;
	}

	private Date tryLoadDate(String textDate, SimpleDateFormat format) {
		Date date;
		try {
			date = format.parse(textDate);
		} catch (ParseException e) {
			date = null;
		}
		
		return date;
	}
	
	private void ladujDaneDoDrzewa(Tree d, sekcjaINI sekcja, List<Relacja> relacje)
	{
		Person o =  new Person();
		String w;
		int lDzieci=0, lMalzenstw=0;
		
		w = sekcja.getWartosc("imie");			if (w != null) o.setImie(w);
		w = sekcja.getWartosc("nazwisko");		if (w != null) o.setNazwisko(w);
		w = sekcja.getWartosc("datur");			if (w != null) o.setDataUrodzenia(new MyDate(w));
		w = sekcja.getWartosc("datsm");			if (w != null) o.setDataSmierci(new MyDate(w));
		w = sekcja.getWartosc("miejur");		if (w != null) o.setMiejsceUrodzenia(w);
		w = sekcja.getWartosc("miejsm");		if (w != null) o.setMiejsceSmierci(w);
		w = sekcja.getWartosc("zyje");			if (w != null) o.setZyje(w.equals("0")?Zyje.NIE:Zyje.TAK);
		w = sekcja.getWartosc("plec");			if (w != null) o.setPlec(w.equals("0")?Plec.Kobieta:Plec.Mezczyna);
		w = sekcja.getWartosc("ps");			if (w != null) o.setPseudonim(w);
		w = sekcja.getWartosc("parafia");		if (w != null) o.setParafiaChrztu(w);
		w = sekcja.getWartosc("mpoch");			if (w != null) o.setMiejscePochowku(w);

		w = sekcja.getWartosc("kontakt");		if (w != null) o.setKontakt(w.replace("$", "\n"));
		w = sekcja.getWartosc("uwagi");			if (w != null) o.setUwagi(w.replace("$", "\n"));
		

		w = sekcja.getWartosc("ojciec");		if (w != null) relacje.add(new Relacja(w, Relacja.Typ.Ojciec, sekcja.nazwa));
		w = sekcja.getWartosc("matka");			if (w != null) relacje.add(new Relacja(w, Relacja.Typ.Matka, sekcja.nazwa));
		w = sekcja.getWartosc("dzieci");		if (w != null) lDzieci = strToInt(w,0);
		w = sekcja.getWartosc("malzenstwa");	if (w != null) lMalzenstw = strToInt(w,0);
		for (int i=1; i<=lDzieci; i++)
			{w = sekcja.getWartosc("dziecko"+i);	if (w != null) relacje.add(new Relacja(w, Relacja.Typ.Dziecko, sekcja.nazwa, i));}
		for (int i=1; i<=lMalzenstw; i++)
			{w = sekcja.getWartosc("malzonek"+i);	if (w != null) relacje.add(new Relacja(w, Relacja.Typ.Malzonek, sekcja.nazwa, i));}
		for (int i=1; i<=lMalzenstw; i++)
			{w = sekcja.getWartosc("malzdata"+i);	if (w != null) Relacja.dodajDateDoRelacji(relacje, sekcja.nazwa, Relacja.Typ.Malzonek, i, w);}
		for (int i=1; i<=lMalzenstw; i++)
			{w = sekcja.getWartosc("malzmjsc"+i);	if (w != null) Relacja.dodajMiejsceDoRelacji(relacje, sekcja.nazwa, Relacja.Typ.Malzonek, i, w);}

		d.dodajOsobe(sekcja.nazwa, o);		
	}
	
	
	
	private int strToInt(String s, int wartDomyslna)
	{
		int i;
		try{
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			i = wartDomyslna;
		}
		
		return i;
	}
}
