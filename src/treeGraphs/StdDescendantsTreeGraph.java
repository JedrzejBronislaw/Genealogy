package treeGraphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.ObjectInputStream.GetField;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import model.Person;
import model.Tree;
import nameDisplaying.DateAndNameDisplaying;

public class StdDescendantsTreeGraph extends TreeGraph{

	public enum TrybOdstepow {TylkoMiedzyRodzienstwem, MiedzyRodzenstwemIKuzynostwem};
	
	private static final int marginesX = 20;
	private static final int marginesY = 20;
	private static final int wciecieMalzonka = 20;
//	private static final int szerPokolenia = 150;
	private	static final int minDlugoscKreskiOdRodzica = 3;
	private	static final int dlugoscStrzalekDoDzieci = 15;
	private				 int szerMiedzyPokoleniami;// = 20;
	private static final int marginesLini = 5;
	private static final int odstepMiedzyMalzonkami = 4;
	private static final int miedzyRodzenstwem = 10;
	private static final int odstepMiedzyKuzynostwem = 20;
	private static final int szerGrotu = 5;
	private static final int wysGrotu  = 3;

	private Font font = new Font("Times", Font.PLAIN, 13);
	private static final Color kolorObraczek = new Color(255, 215, 0);

	private TrybOdstepow tryb = TrybOdstepow.TylkoMiedzyRodzienstwem;
//	private TrybOdstepow tryb = TrybOdstepow.MiedzyRodzenstwemIKuzynostwem;
	
	private int[] szerokosciKolumn;
	
	
	public void setTryb(TrybOdstepow tryb) {
		this.tryb = tryb;
	}
	public TrybOdstepow getTryb() {
		return tryb;
	}
	
//	@Override
//	public void setDrzewo(Drzewo drzewo) {
//		this.drzewo = drzewo;
//	}


	public StdDescendantsTreeGraph() {}
	public StdDescendantsTreeGraph(/*Drzewo d,*/ Person o) {
//		drzewo = d;
		osobaGlowna = o;
	}

//	@Override
//	public int getSzerokosc() {return szerokosc;}
//	@Override
//	public int getWysokosc()  {return wysokosc;}
	
	
	@Override
	public void rysuj(Graphics2D g) {
		
		if (osobaGlowna == null) return;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		wyswietlacz.setGraphics(g);
		klikMapa.wyczysc();
		
		szerMiedzyPokoleniami = minDlugoscKreskiOdRodzica+dlugoscStrzalekDoDzieci+marginesLini*2;
		
		szerokosc = ustalSzerokosciKolumn(g.getFontMetrics());
		if (tryb == TrybOdstepow.TylkoMiedzyRodzienstwem)
			wysokosc = rysujRodzine(g, osobaGlowna, marginesX, marginesY, 0) - miedzyRodzenstwem;
		else
			wysokosc = rysujRodzine(g, osobaGlowna, marginesX, marginesY, 0, true);
//		szerokosc = pokolen*szerPokolenia + (pokolen-1)*szerMiedzyPokoleniami;

//		g.setColor(Color.RED);
//		int sz = 0;
//		for (int i=0; i<szerokosciKolumn.length; i++)
//		{
//			sz += szerokosciKolumn[i];
//			g.drawLine(marginesX+sz, 0, marginesX+sz, wysokosc+marginesY*2);
//			sz += szerMiedzyPokoleniami;
//			g.drawLine(marginesX+sz, 0, marginesX+sz, wysokosc+marginesY*2);
//		}
//		g.drawLine(marginesX, 0, marginesX, wysokosc+marginesY*2);
//		g.drawLine(0, marginesY, szerokosc+marginesX+2, marginesY);
//		g.drawLine(0, marginesY+wysokosc, szerokosc+marginesX*2, marginesY+wysokosc);
		
		wysokosc  += marginesY*2;
		szerokosc += marginesX*2;
	}

	private int ustalSzerokosciKolumn(FontMetrics fm) {
		szerokosciKolumn = new int[osobaGlowna.descendantGenerations()+1];
		int szerokoscCalokowita = 0;
		
		//Ustalenie maksymalnych szerokosci nazwisk
		szerokosciGalezi(fm, osobaGlowna, 0);
		
		//obliczenie szerokosci calkowitej (bez marginesow)
		for (int i=0; i<szerokosciKolumn.length; i++)
			szerokoscCalokowita += szerokosciKolumn[i]+szerMiedzyPokoleniami;
		szerokoscCalokowita -= szerMiedzyPokoleniami;
		
		//w ostatniej kolumnie pozosta³a prawa granica ostatniej kolumny
		return szerokoscCalokowita;
	}	
	
	private void szerokosciGalezi(FontMetrics fm, Person osoba, int pokolenie)
	{
		int szerokosc;
		Person malzonek;

		//porónanie w³asnej szerokoœci
		szerokosc = wyswietlacz.getWidth(osoba);
		if (szerokosc > szerokosciKolumn[pokolenie])
			szerokosciKolumn[pokolenie] = szerokosc;

		//porównie szerokoœci wszystkich ma³¿onków
		for (int i=0; i<osoba.numberOfMarriages(); i++)
		{
			malzonek = osoba.getSpouse(i);
			szerokosc = wyswietlacz.getWidth(malzonek)+wciecieMalzonka;
			if (szerokosc > szerokosciKolumn[pokolenie])
				szerokosciKolumn[pokolenie] = szerokosc;			
		}
		
		//rekurencyjne wywo³anie dla wszystkich dzieci
		for (int i=0; i<osoba.numberOfChildren(); i++)
			szerokosciGalezi(fm, osoba.getChild(i), pokolenie+1);
	}
	
	private int rysujRodzine(Graphics2D g, Person o, int x, int y, int pokolenie)
	{
		int przes = 0;
		int przesMal = 0;
		int wysNazw  = wyswietlacz.getHeight(o);
		int szerNazw = wyswietlacz.getWidth(o);
		
		int liniaX;
		
		y += wysNazw;
		wyswietlacz.print(o, x, y);
		klikMapa.dodajObszar(o, x, y, x+szerNazw, y-wysNazw);
		
		for (int i=0; i<o.numberOfMarriages(); i++)
//			przesMal += rysujMalzonka(g, o.getMalzonek(i), x, y+((odstepMiedzyMalzonkami+wysNazw)*(i+1))) + odstepMiedzyMalzonkami;
			przesMal += rysujMalzonka(g, o.getSpouse(i), x, y+przesMal+odstepMiedzyMalzonkami) + odstepMiedzyMalzonkami;;//((odstepMiedzyMalzonkami+wysNazw)*(i+1))) + odstepMiedzyMalzonkami;
	
		if (o.numberOfChildren() > 0)
		{
			liniaX = x+szerokosciKolumn[pokolenie]+marginesLini+minDlugoscKreskiOdRodzica;//
			//linia od rodzica
			g.drawLine(x+szerNazw+marginesLini, y-wysNazw/2, liniaX, y-wysNazw/2);
			for (int i=0; i<o.numberOfChildren(); i++)
			{
				//linia do dziecka
				g.drawLine(liniaX, y+przes-wysNazw/2, liniaX+dlugoscStrzalekDoDzieci, y+przes-wysNazw/2);
				rysujGrot(g, liniaX+dlugoscStrzalekDoDzieci, y+przes-wysNazw/2);
				rysujNumerMalzenstwa(g, o.getChild(i), o, liniaX, y+przes, y+przes-wysNazw/2);
				//linia pionowa
				g.drawLine(liniaX, y-wysNazw/2, liniaX, y-wysNazw/2+przes);
				przes += rysujRodzine(g, o.getChild(i), liniaX+dlugoscStrzalekDoDzieci+marginesLini, y-wysNazw+przes, pokolenie+1)+miedzyRodzenstwem;
			}
//			przes -= miedzyRodzenstwem; //Mo¿na w³¹czyæ do zacieœnienia zapisu (i do mniejszenia czytlnoœci)
		}

		return Math.max(przesMal+wysNazw, przes);
	
		
	}
	private void rysujNumerMalzenstwa(Graphics2D g, Person dziecko, Person rodzic, int x1, int y1, int yLini) {
		if (rodzic.numberOfMarriages() > 1)
		{
			Color tempColor;
			FontMetrics fm = g.getFontMetrics();
			int nrMalzenstwa = dziecko.parentsMarriageNumber(rodzic);
			if (nrMalzenstwa != 0)
			{
				tempColor = g.getColor();
				g.setColor(g.getBackground());
				g.drawLine(x1+2, yLini, x1+2+fm.stringWidth(nrMalzenstwa+""), yLini);
				g.setColor(tempColor);
				g.drawString(nrMalzenstwa+"", x1+3, y1);
			}
		}
	}
	private int rysujRodzine(Graphics2D g, Person o, int x, int y, int pokolenie, boolean ostatnie)
	{
		int przes = 0;
		int przesMal = 0;
//		FontMetrics fm = g.getFontMetrics();
//		String nazwisko = o.imieNazwisko();
		int wysNazw  = wyswietlacz.getHeight(o);
		int szerNazw = wyswietlacz.getWidth(o);
//		int wysNazw  = fm.getAscent()-fm.getDescent();//getHeight();
//		int szerNazw = fm.stringWidth(nazwisko);
//		int przesuniecie = wysNazw*4/5;

		int liniaX;
		
		y += wysNazw;
		wyswietlacz.print(o, x, y);
//		g.drawString(nazwisko, x, y);
		klikMapa.dodajObszar(o, x, y, x+szerNazw, y-wysNazw);
		
		for (int i=0; i<o.numberOfMarriages(); i++)
//			przesMal += rysujMalzonka(g, o.getMalzonek(i), x, y+((odstepMiedzyMalzonkami+wysNazw)*(i+1))) + odstepMiedzyMalzonkami;
			przesMal += rysujMalzonka(g, o.getSpouse(i), x, y+przesMal+odstepMiedzyMalzonkami) + odstepMiedzyMalzonkami;//((odstepMiedzyMalzonkami+wysNazw)*(i+1))) + odstepMiedzyMalzonkami;
	
		if (o.numberOfChildren() > 0)
		{
			liniaX = x+szerokosciKolumn[pokolenie]+marginesLini+minDlugoscKreskiOdRodzica;//szerPokolenia;
			//linia od rodzica
			g.drawLine(x+szerNazw+marginesLini, y-wysNazw/2, liniaX, y-wysNazw/2);
			for (int i=0; i<o.numberOfChildren(); i++)
			{
				//linia do dziecka
				g.drawLine(liniaX, y+przes-wysNazw/2, liniaX+dlugoscStrzalekDoDzieci, y+przes-wysNazw/2);
				rysujGrot(g, liniaX+dlugoscStrzalekDoDzieci, y+przes-wysNazw/2);
				rysujNumerMalzenstwa(g, o.getChild(i), o, liniaX, y+przes, y+przes-wysNazw/2);
				//linia pionowa
				g.drawLine(liniaX, y-wysNazw/2, liniaX, y-wysNazw/2+przes);
				przes += rysujRodzine(g, o.getChild(i), liniaX+dlugoscStrzalekDoDzieci+marginesLini, y-wysNazw+przes, pokolenie+1, i==o.numberOfChildren()-1);
			}
		}

		if (ostatnie)
			return Math.max(przesMal+wysNazw, przes);
		
		if (o.numberOfChildren() == 0)
			return przesMal+wysNazw+miedzyRodzenstwem;
		
		return Math.max(przesMal+wysNazw+miedzyRodzenstwem, przes+odstepMiedzyKuzynostwem);
	}
	
	private int rysujMalzonka(Graphics2D g, Person o, int x, int y)
	{
//		String nazwisko = o.imieNazwisko();
//		FontMetrics fm = g.getFontMetrics();
//		int wysNazw = fm.getAscent()-fm.getDescent();
//		int szerNazw = fm.stringWidth(nazwisko);
		int wysNazw = wyswietlacz.getHeight(o);
		int szerNazw = wyswietlacz.getWidth(o);
		
//		g.drawString(nazwisko, x+wciecieMalzonka, y);
		wyswietlacz.print(o, x+wciecieMalzonka, y+wysNazw);
		klikMapa.dodajObszar(o, x, y+wysNazw, x+wciecieMalzonka+szerNazw, y);
		rysujObraczki(g, x, y, wciecieMalzonka, wysNazw);
		
		return wysNazw;
	}

	private void rysujObraczki(Graphics2D g, int x, int y, int szer, int wys) {
		Color kolor = g.getColor();
		final float czescWspolna = (float)0.3; 
//		final int wspolne;
		float propObraczek = (float) (2.0-czescWspolna);
		float propObszaru  = szer/wys;
		int wysObraczki, szerObraczki;
		int szerObraczek;
		int x2;
		
//		g.setColor(Color.RED);
//		g.drawRect(x, y, szer, wys);
		
		
		if (propObraczek > propObszaru)
		{
			//ograniczeniem jest szer
			szerObraczek = szer;
			szerObraczki = wysObraczki = (int) (szerObraczek/(2-czescWspolna));
		}
		else
		{
			//ograniczeniem jest wys
			szerObraczki = wysObraczki = wys;
			szerObraczek = (int) (szerObraczki*(2-czescWspolna));
		}
		
//		int wymiar;
		
		g.setColor(kolorObraczek);
//		wymiar = Math.min(szer, wys);
//		wymiar = Math.min((int)(szer*(2-czescWspolna)), wys);
//		
//		int margX = (szer-wymiar)/2;
//		int margY = (wys -wymiar)/2;
		
		x += (szer-szerObraczek)/2;//(szer-wymiar)/2;
		y += (wys -wysObraczki) /2;//(wys -wymiar)/2;
		x2 = (int)(x+(szerObraczki*(1-czescWspolna)));
		

		g.drawOval(x , y, szerObraczki, wysObraczki);
		g.drawOval(x2, y, szerObraczki, wysObraczki);
		
		g.setColor(kolor);
	}
	
	private void rysujGrot(Graphics2D g, int x, int y)
	{
		g.drawLine(x, y, x-szerGrotu, y-wysGrotu);
		g.drawLine(x, y, x-szerGrotu, y+wysGrotu);
	}
	
//	@Override
//	public Dimension getWymiary() {
//		// TODO 
//		return new Dimension(szerokosc, wysokosc);
//	}

}
