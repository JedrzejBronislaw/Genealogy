package dane;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Osoba {
	public enum Zyje{TAK, NIE, Nieokreslono};
	public enum Plec{Kobieta, Mezczyna, Nieokreslono};
	
	private String imie;
	private String nazwisko;
	private String pseudonim;
	private Zyje zyje = Zyje.Nieokreslono;
	private Plec plec = Plec.Nieokreslono;
	private Data dataUrodzenia;
	private Data dataSmierci;
	private String miejsceUrodzenia;
	private String miejsceSmierci;
	private String kontakt;
	private String uwagi;
	private String parafiaChrztu;
	private String miejscePochowku;
	
	private Osoba ojciec;
	private Osoba matka;
	private List<Osoba> dzieci = new ArrayList<Osoba>(); 
	private List<Malzenstwo> malzenstwa = new ArrayList<Malzenstwo>(); 
	
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public String getPseudonim() {
		return pseudonim;
	}
	public void setPseudonim(String pseudonim) {
		this.pseudonim = pseudonim;
	}
	public Zyje getZyje() {
		return zyje;
	}
	public void setZyje(Zyje zyje) {
		this.zyje = zyje;
	}
	public Plec getPlec() {
		return plec;
	}
	public void setPlec(Plec plec) {
		this.plec = plec;
	}
	public Data getDataUrodzenia() {
		return dataUrodzenia;
	}
	public void setDataUrodzenia(Data dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
	}
	public Data getDataSmierci() {
		return dataSmierci;
	}
	public void setDataSmierci(Data dataSmierci) {
		this.dataSmierci = dataSmierci;
	}
	public String getMiejsceUrodzenia() {
		return miejsceUrodzenia;
	}
	public void setMiejsceUrodzenia(String miejsceUrodzenia) {
		this.miejsceUrodzenia = miejsceUrodzenia;
	}
	public String getMiejsceSmierci() {
		return miejsceSmierci;
	}
	public void setMiejsceSmierci(String miejsceSmierci) {
		this.miejsceSmierci = miejsceSmierci;
	}
	public String getKontakt() {
		return kontakt;
	}
	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}
	public String getUwagi() {
		return uwagi;
	}
	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}
	public String getParafiaChrztu() {
		return parafiaChrztu;
	}
	public void setParafiaChrztu(String parafiaChrztu) {
		this.parafiaChrztu = parafiaChrztu;
	}
	public String getMiejscePochowku() {
		return miejscePochowku;
	}
	public void setMiejscePochowku(String miejscePochowku) {
		this.miejscePochowku = miejscePochowku;
	}
	
	
	
	public Osoba getOjciec() {
		return ojciec;
	}
	public void setOjciec(Osoba ojciec) {
		this.ojciec = ojciec;
	}
	public Osoba getMatka() {
		return matka;
	}
	public void setMatka(Osoba matka) {
		this.matka = matka;
	}
	public void dodajDziecko(Osoba dziecko) {
		dzieci.add(dziecko);
	}
	public Osoba getDziecko(int nr) {
		return dzieci.get(nr);
	}
	public Osoba[] getDzieci() {
		Osoba[] wynik = new Osoba[dzieci.size()];
		wynik = dzieci.toArray(wynik);
		return wynik;
	}
	public void dodajMalzenstwo(Osoba malzonek) {
		Malzenstwo malzenstwo = new Malzenstwo();
		if (plec == Plec.Mezczyna)
		{
			malzenstwo.setMaz(this);
			malzenstwo.setZona(malzonek);
			malzenstwa.add(malzenstwo);
		} else
		if (plec == Plec.Kobieta)
		{
			malzenstwo.setMaz(malzonek);
			malzenstwo.setZona(this);
			malzenstwa.add(malzenstwo);
		}
	}
	public void dodajMalzenstwo(Osoba malzonek, String data, String miejsce) {
		Malzenstwo slob = new Malzenstwo();
		
		slob.setData(data);
		slob.setMiejsce(miejsce);
		if (plec == Plec.Mezczyna)
		{
			slob.setMaz(this);
			slob.setZona(malzonek);
			malzenstwa.add(slob);
		} else
		if (plec == Plec.Kobieta)
		{
			slob.setMaz(malzonek);
			slob.setZona(this);
			malzenstwa.add(slob);
		}
	}
	public void dodajDateSlubu(Osoba malzonek, String data)
	{
		for(Malzenstwo m : malzenstwa)
		{
			if (((plec == Plec.Mezczyna) && (m.getZona() == malzonek)) ||
				((plec == Plec.Kobieta)  && (m.getMaz()  == malzonek)))
			{
				m.setData(data);
				return;
			}
		}
	}
	public void dodajMiejsceSlubu(Osoba malzonek, String miejsce)
	{
		for(Malzenstwo m : malzenstwa)
		{
			if (((plec == Plec.Mezczyna) && (m.getZona() == malzonek)) ||
				((plec == Plec.Kobieta)  && (m.getMaz()  == malzonek)))
			{
				m.setMiejsce(miejsce);
				return;
			}
		}
	}
	
	public Malzenstwo getMalzenstwo(int nr) {
		return malzenstwa.get(nr);
	}
	
	public Osoba getMalzonek(int nr) {
//		if (malzenstwa.get(nr) == null)
//			System.out.println("malzenstwa.get(" + nr + ") == null");
		if (plec == Plec.Mezczyna)
			return malzenstwa.get(nr).getZona();
		if (plec == Plec.Kobieta)
			return malzenstwa.get(nr).getMaz();
		
		return null;
	}

	public Osoba[] getRodzenstwo() {
		Osoba[] dzieciO = (ojciec!=null) ? ojciec.getDzieci() : new Osoba[0];
		Osoba[] dzieciM = (matka !=null) ? matka.getDzieci()  : new Osoba[0];
		Osoba[] rodzenstwo;
		LinkedHashSet<Osoba> set = new LinkedHashSet<Osoba>();

		for (Osoba o:dzieciO)
			set.add(o);
		for (Osoba o:dzieciM)
			set.add(o);
		
		set.remove(this);
		
		rodzenstwo = new Osoba[set.size()];
		rodzenstwo = set.toArray(rodzenstwo);
		
		return rodzenstwo;
	}
	
	public int liczbaDzieci() {return dzieci.size();}
	public int liczbaMalzenstw() {return malzenstwa.size();}
	public String imieNazwisko() 
	{
		String wynik = "";
		if (imie != null)		wynik += getImie() + " ";
		if (nazwisko != null)	wynik += getNazwisko();
		
		return wynik.trim();
	}
	
	public int rozmiarKorzenia()
	{
		int wynik = 0;
		if (matka != null) wynik = matka.rozmiarKorzenia()+1;
		if (ojciec != null) wynik = Math.max(wynik, ojciec.rozmiarKorzenia()+1);
		
		return wynik;
	}
	
	public int liczbaPokolenPotomkow()
	{
		int wynik = 0;
		for (int i=0; i<liczbaDzieci(); i++)
			wynik = Math.max(wynik, dzieci.get(i).liczbaPokolenPotomkow()+1);
		
		return wynik;
	}
	public int zKtoregoMalzenstwa(Osoba rodzic) {
		int wynik = 0;
		Osoba rodzic2;
		
		for (int i=0; i < rodzic.liczbaMalzenstw(); i++)
		{
			rodzic2 = rodzic.getMalzonek(i);
			if ((getOjciec() == rodzic2) || (getMatka() == rodzic2))
			{
				wynik = i+1;
				break;
			}
		}
			
		if ((getMatka() != rodzic) && (getOjciec() != rodzic))
			wynik *= -1;
			
		return wynik;
	}
}
