package treeGraphs;

import model.Person;
import other.InflectionPL;
import other.PersonDetails;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingDescendantTreeGraphCalculation {
	
	enum DlugoscGalezi {Deterministzcyna, Losowa}
	enum KatGalezi {Deterministzczny, Losowy}
	
	public class Element
	{
		private int x, y;
		private int pokolenie;
		private Person osoba;
		private List<Element> poloczenie;
		
		public Element(Person osoba, int pokolenie, int x, int y) {
			this.osoba = osoba;
			this.pokolenie = pokolenie;
			this.x = x;
			this.y = y;
			poloczenie = new ArrayList<Element>();
		}
		
		public int getPokolenie() {
			return pokolenie;
		}
		public void setPokolenie(int pokolenie) {
			this.pokolenie = pokolenie;
		}		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public Person getOsoba() {
			return osoba;
		}
		public void setOsoba(Person osoba) {
			this.osoba = osoba;
		}
		public List<Element> getPoloczenie() {
			return poloczenie;
		}
		public void setPoloczenie(List<Element> poloczenie) {
			this.poloczenie = poloczenie;
		}
		
		
	}

	private int poczatkowaOdlegloscMiedzyPokoleniami = 50;
	
	private Person osobaGlowna;
	private List<Element> wyniki;
	private float katPocz = 155;
	private float katCaly = 130;
	private int srodekOffsetX = 0, srodekOffsetY = 0;
	private boolean obliczone = false;
	private DlugoscGalezi dlugoscGalezi = DlugoscGalezi.Deterministzcyna;
	private KatGalezi katGalezi = KatGalezi.Deterministzczny;

	private float RoznicaDlugosciGaleziWzgledemRodzica = -5;

	private int wysokosc = 0;
	private int szerokosc = 0;
	private int przesuniecieX = 0;
	private int przesuniecieY = 0;
	
	
	
	public DrawingDescendantTreeGraphCalculation setRoznicaDlugosciGaleziWzgledemRodzica(int roznica) {
		RoznicaDlugosciGaleziWzgledemRodzica = roznica;
		return this;
	}
	
	public DrawingDescendantTreeGraphCalculation setPoczatkowaOdlegloscMiedzyPokoleniami(int poczatkowaOdleglosc) {
		this.poczatkowaOdlegloscMiedzyPokoleniami = poczatkowaOdleglosc;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setOdlegloscMiedzyNajmlodszymiPokoleniami(int odleglosc) {
		float x = (odleglosc-poczatkowaOdlegloscMiedzyPokoleniami)/(osobaGlowna.descendantGenerations()-1);
		
		RoznicaDlugosciGaleziWzgledemRodzica = x;
		return this;
	}
	
	public DrawingDescendantTreeGraphCalculation setDlugoscGalezi(DlugoscGalezi dlugoscGalezi) {
		this.dlugoscGalezi = dlugoscGalezi;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setKatGalezi(KatGalezi katGalezi) {
		this.katGalezi = katGalezi;
		return this;
	}
	
	public DrawingDescendantTreeGraphCalculation setKatPocz(float katWStopniach) {
		this.katPocz = katWStopniach;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setKatCaly(float katWStopniach) {
		this.katCaly = katWStopniach;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setSymetrycznyKat(float szerokoscWSt) {
		this.katCaly = szerokoscWSt;
		this.katPocz = 180-(180-katCaly)/2;
		return this;
	}
	
	
	public DrawingDescendantTreeGraphCalculation(Person osobaGlowna) {
		this.osobaGlowna = osobaGlowna;
		wyniki = new ArrayList<Element>();
	}

	public List<Element> get() {
		if (!obliczone)
			oblicz();
		
		return wyniki;
	}
	
	
	private void oblicz()
	{
//		List<Element> wyniki = new ArrayList<Element>();
		rysujGalaz(osobaGlowna, 1, 0, 0, 0);
		obliczWymiary();
		
//		this.wyniki = new Element[wyniki.size()];
//		wyniki.toArray(this.wyniki);
		for (Element e : wyniki)
		{
			e.x += srodekOffsetX + przesuniecieX;
			e.y += srodekOffsetY + przesuniecieY;
		}
		
		obliczone = true;
	}
	

	private void obliczWymiary() {

		int maxX, minX, maxY, minY;
		maxX = minX = maxY = minY = 0;
		
		
		for (Element e : wyniki){
			if (e.x > maxX) maxX = e.x; 
			else if (e.x < minX) minX = e.x;

			if (e.y > maxY) maxY = e.y; 
			else if (e.y < minY) minY = e.y;
		}

		srodekOffsetX = -minX;
		srodekOffsetY = -minY;
		
		wysokosc  = maxY-minY;
		szerokosc = maxX-minX;
		
		
//		p1 = wyznaczPunkt(odleglosc, kat)
//		
//		
//		szerokosc = osobaGlowna.liczbaPokolenPotomkow()*poczatkowaOdlegloscMiedzyPokoleniami*2;
//		
//		if(katCaly <= 180 && Math.abs(katPocz) <= (180-katCaly)/2)
//			wysokosc = osobaGlowna.liczbaPokolenPotomkow()*poczatkowaOdlegloscMiedzyPokoleniami;
//		else
//			wysokosc = osobaGlowna.liczbaPokolenPotomkow()*poczatkowaOdlegloscMiedzyPokoleniami*2;//TODO doprecyzowaæ, zawêziæ
	}
	
	public int getWysokosc() {return wysokosc;}
	public int getSzerokosc() {return szerokosc;}
	public Dimension getWymiary() {return new Dimension(szerokosc, wysokosc);}
	public Point getKorzen() {return new Point(srodekOffsetX, srodekOffsetY);}

	private Element rysujGalaz(Person osoba, double katOffset, int rodzicX, int rodzicY, int pokolenie)
	{
		int szer = PersonDetails.szerokoscGaleziPotomkow(osobaGlowna);//-1;
		int szerCzesciowa = 0;
		int szerAkt = 0;
		Person dziecko;
		Point punkt;
		double odleglosc;
		double kat = 0; 
		
		Random r = new Random();
		
			Element element = new Element(osoba, pokolenie, rodzicX, rodzicY);
			wyniki.add(element);		
//		klikMapa.dodajObszar(osoba, rodzicX-5, rodzicY-5, rodzicX+5, rodzicY+5);
		
//		rysujPromien(g, rodzicX, rodzicY, kat1, 200, new Color(150, 75, 0));
		for (int i=0; i<osoba.numberOfChildren(); i++)
		{
//			setSymetrycznyKat(90+pokolenie*20);
			dziecko = osoba.getChild(i);
			szerAkt = PersonDetails.szerokoscGaleziPotomkow(dziecko);
			odleglosc = (pokolenie+1)*poczatkowaOdlegloscMiedzyPokoleniami;
			
			if (dlugoscGalezi == DlugoscGalezi.Deterministzcyna)
				odleglosc += pokolenie*(pokolenie+1)/2*RoznicaDlugosciGaleziWzgledemRodzica ;
			else if (dlugoscGalezi == DlugoscGalezi.Losowa)
				odleglosc -= r.nextInt(poczatkowaOdlegloscMiedzyPokoleniami/2);
			
			if (katGalezi == KatGalezi.Deterministzczny)
				kat = katOffset - (szerCzesciowa+((double)szerAkt/2))/szer;
			else if (katGalezi == KatGalezi.Losowy)
				kat = katOffset - (szerCzesciowa+((double)szerAkt*(r.nextDouble()/2+0.25)))/szer;
			
			
			punkt = wyznaczPunkt(odleglosc, kat);
			

			
			
			element.getPoloczenie().add(rysujGalaz(dziecko, katOffset - (double)(szerCzesciowa)/szer, punkt.x, punkt.y, pokolenie+1));
			
			szerCzesciowa += szerAkt;
		}
		return element;
	}
	public static double stNaRad(double stopnie)
	{
		return  (stopnie/360*2*Math.PI) % (2*Math.PI);
	}
	
	public static double procOkreguNaRad(double procenty)
	{
		return procenty*2*Math.PI;
	}
	
	public double procObszaruNaRad(double procent)
	{
		procent *= katCaly/360;
		procent  = procOkreguNaRad(procent);
		procent += stNaRad(katPocz-katCaly);
		
		return procent;
	}
	
	public static Point katRadNaPunkt(double kat, double odleglosc, Point punkt)
	{
		int x = (int) (odleglosc *  Math.cos(kat)) + punkt.x;
		int y = (int) (odleglosc * -Math.sin(kat)) + punkt.y;
		
		return new Point(x,y);
	}
	
	private Point wyznaczPunkt(double odleglosc, double kat)
	{
		kat = procObszaruNaRad(kat);
		
		Point punkt = katRadNaPunkt(kat, odleglosc, new Point(srodekOffsetX, srodekOffsetY));

		return punkt;
	}

	public DrawingDescendantTreeGraphCalculation setPrzesuniecie(int przesuniecieX,
			int przesuniecieY) {

		this.przesuniecieX = przesuniecieX;
		this.przesuniecieY = przesuniecieY;

		return this;
	}
}
