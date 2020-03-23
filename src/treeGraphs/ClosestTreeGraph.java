package treeGraphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import model.Person;

public class ClosestTreeGraph extends TreeGraph {


	private static final int marginesX = 20;
	private static final int marginesY = 20;
	private static final int marginesLini = 5;
	private static final int minSzerMiedzyRodzicami = 50;
	private static final int odstepOdRodzicow = 30;
	private static final int odstepMiedzyRodzenstwem = 7;
	private static final int odstepMiedzyMalzonkami = 7;
	private static final int odstepMiedzyDzieci = 7;
	private static final int wciecieMalzonkow = 20;
	private static final int wciecieDzieci = 20;
	private static final int odstepPrzedDziecmi = 30;
	private static final int minOdstepMiedzyKolumnami = 30; //miêdzy ma³¿onkami i dzieæmi a rodzeñstwem
	private static final int szerGrotu = 3;
	private static final int wysGrotu  = 5;

	private static final Font fontOsobaGlowna = new Font("Times", Font.BOLD, 25);
	private static final Font fontInneOsoby   = new Font("Arial", Font.BOLD, 12);


	private int szerokoscObszaru;
	private int xNazw, yNazw;
	private int szerNazw, wysNazw;
	private int szerNazwM, szerNazwO;
	private int wysTekstu;
	private int szerRodzenstwa, wysRodzenstwa;
	private int szerMalzonkow, wysMalzonkow;
	private int szerDzieci, wysDzieci;
	
	boolean wykonajObliczenia = true;
	
	@Override
	public void setOsobaGlowna(Person osoba) {
		this.osobaGlowna = osoba;
		wykonajObliczenia = true;
	}
	
	public void setWykonajObliczenia(boolean wykonajObliczenia) {
		this.wykonajObliczenia = wykonajObliczenia;
	}
	
	public ClosestTreeGraph() {}
	public ClosestTreeGraph(Person osoba) {
		this.osobaGlowna = osoba;
	}
	
	@Override
	public void rysuj(Graphics2D g) {
		g.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		klikMapa.wyczysc();
		if (osobaGlowna == null)
			return;
		
		if(wykonajObliczenia)
		{
			obliczenia(g);
			wykonajObliczenia = false;
		}
		
		String nazwisko = osobaGlowna.imieNazwisko();
		Person[] rodzenstwo = osobaGlowna.getRodzenstwo();
		
		
		g.setFont(fontOsobaGlowna);
		g.drawString(nazwisko, xNazw, yNazw);
		
		g.setFont(fontInneOsoby);

		rysujKreski(g, rodzenstwo.length > 0);
		rysujRodzicow(g, szerokoscObszaru);
		rysujRodzenstwo(g, rodzenstwo, marginesX+szerokoscObszaru-szerRodzenstwa, yNazw);
		rysujMalzonkow(g, xNazw+wciecieMalzonkow, yNazw);
		rysujDzieci(g, xNazw+wciecieDzieci, yNazw+odstepPrzedDziecmi-odstepMiedzyDzieci+wysMalzonkow);


		//ramka
//		g.setColor(Color.gray);
//		g.drawLine(marginesX, marginesY, szerokosc-marginesX, marginesY);
//		g.drawLine(marginesX, marginesY, marginesX, wysokosc-marginesY);
//		g.drawLine(szerokosc-marginesX, wysokosc-marginesY, szerokosc-marginesX, marginesY);
//		g.drawLine(szerokosc-marginesX, wysokosc-marginesY, marginesX, wysokosc-marginesY);
		
		
	}
	
	private void obliczenia(Graphics2D g)
	{
		Person matka = osobaGlowna.getMatka();
		Person ojciec = osobaGlowna.getOjciec();
		Person[] rodzenstwo = osobaGlowna.getRodzenstwo();
		String nazwisko = osobaGlowna.imieNazwisko();
		String matkaIN  = (matka != null)  ? matka.imieNazwisko()  : "";
		String ojciecIN = (ojciec != null) ? ojciec.imieNazwisko() : "";
		
		
		g.setFont(fontOsobaGlowna);
		FontMetrics fm = g.getFontMetrics();
		wysNazw  = fm.getAscent()-fm.getDescent();
		szerNazw = fm.stringWidth(nazwisko);
		
		xNazw = marginesX;
		yNazw = marginesY+wysNazw;
		if ((ojciec != null) || (matka != null))
		yNazw += odstepOdRodzicow;
		
		g.setFont(fontInneOsoby);
		fm = g.getFontMetrics();
		wysTekstu = fm.getAscent()-fm.getDescent();
		szerNazwM = fm.stringWidth(matkaIN);
		szerNazwO = fm.stringWidth(ojciecIN);

		szerRodzenstwa = szerRodzenstwa(g, rodzenstwo);
		wysRodzenstwa  = rodzenstwo.length * (wysTekstu+odstepMiedzyRodzenstwem);
		szerMalzonkow = szerMalzonkow(g);
		wysMalzonkow  = osobaGlowna.liczbaMalzenstw() * (wysTekstu+odstepMiedzyMalzonkami);
		szerDzieci = szerDzieci(g);
		wysDzieci  = osobaGlowna.liczbaDzieci() * (wysTekstu+odstepMiedzyDzieci)-odstepMiedzyDzieci;
		
		szerokoscObszaru = szerNazw;// + 100;
		if (szerokoscObszaru < szerNazwO+szerNazwM+minSzerMiedzyRodzicami)
			szerokoscObszaru = szerNazwO+szerNazwM+minSzerMiedzyRodzicami;
		if (szerokoscObszaru < wciecieMalzonkow+szerMalzonkow+minOdstepMiedzyKolumnami+szerRodzenstwa)
			szerokoscObszaru = wciecieMalzonkow+szerMalzonkow+minOdstepMiedzyKolumnami+szerRodzenstwa;
		if (szerokoscObszaru < wciecieDzieci+szerDzieci+minOdstepMiedzyKolumnami+szerRodzenstwa)
			szerokoscObszaru = wciecieDzieci+szerDzieci+minOdstepMiedzyKolumnami+szerRodzenstwa;
		
		wysokosc = yNazw+wysRodzenstwa;
		if (wysokosc < yNazw+wysMalzonkow)
			wysokosc = yNazw+wysMalzonkow;
		if ((osobaGlowna.liczbaDzieci() > 0) && (wysokosc < yNazw+wysMalzonkow+odstepPrzedDziecmi+wysDzieci))
			wysokosc = yNazw+wysMalzonkow+odstepPrzedDziecmi+wysDzieci;
		szerokosc = szerokoscObszaru;//szerNazw;
		
		wysokosc  += marginesY;	//nie *2 bo jest ju¿ w yNazw
		szerokosc += marginesX*2;
	}
	
	private void rysujKreski(Graphics2D g, boolean maRodzenstwo)
	{
		Person ojciec = osobaGlowna.getOjciec();
		Person matka  = osobaGlowna.getMatka();
		
		//linia miêdzy rodzicami
		int xOdOjca = marginesX+szerNazwO+marginesLini;
		int xOdMatki= marginesX+szerokoscObszaru-szerNazwM-marginesLini;
		int xLini = xOdOjca + (xOdMatki - xOdOjca)/2;
		int yLiniG = marginesY+wysTekstu/2;
		int yLiniD = marginesY+odstepOdRodzicow-marginesLini;
		if (ojciec != null)
			g.drawLine(xOdOjca, yLiniG, xLini, yLiniG);
		if (matka != null)
			g.drawLine(xOdMatki, yLiniG, xLini, yLiniG);
		//linia od rodziców
		if ((ojciec != null) || (matka != null))
		{
			g.drawLine(xLini, yLiniG, xLini, yLiniD);
			rysujGrot(g, xLini, yLiniD);
		}
		
		//linia do rodzeñstwa
		if (maRodzenstwo)
		{
			int xNadRodzenstwem = marginesX+szerokoscObszaru-szerRodzenstwa/2;
			if (xNadRodzenstwem < marginesX+szerNazw+(szerokoscObszaru-szerNazw)/2)
				xNadRodzenstwem = marginesX+szerNazw+(szerokoscObszaru-szerNazw)/2;
			int yNadRodzenstwem = yNazw+odstepMiedzyRodzenstwem-marginesLini;
			g.drawLine(xLini, yLiniG+(yLiniD-yLiniG)/2, xNadRodzenstwem, yLiniG+(yLiniD-yLiniG)/2);
			g.drawLine(xNadRodzenstwem, yLiniG+(yLiniD-yLiniG)/2, xNadRodzenstwem, yNadRodzenstwem);
			rysujGrot(g, xNadRodzenstwem, yNadRodzenstwem);
		}
		
		//linia do dzieci
		if (osobaGlowna.liczbaDzieci() > 0)
		{
			int xDoDzieci = marginesX+wciecieDzieci+szerDzieci/2;
			int yDoDzieciG = yNazw+wysMalzonkow+marginesLini;
			int yDoDzieciD = yNazw+wysMalzonkow+odstepPrzedDziecmi-marginesLini;
			g.drawLine(xDoDzieci, yDoDzieciG, xDoDzieci, yDoDzieciD);
			rysujGrot(g, xDoDzieci, yDoDzieciD);
		}
	}
	
	private void rysujGrot(Graphics2D g, int x, int y)
	{
		g.drawLine(x, y, x-szerGrotu, y-wysGrotu);
		g.drawLine(x, y, x+szerGrotu, y-wysGrotu);
	}
	
	private void rysujRodzicow(Graphics2D g,int szerokoscObszaru)
	{
		Person matka = osobaGlowna.getMatka();
		Person ojciec = osobaGlowna.getOjciec();
		String matkaIN  = (matka != null)  ? matka.imieNazwisko()  : "";
		String ojciecIN = (ojciec != null) ? ojciec.imieNazwisko() : "";

		
		g.drawString(ojciecIN, marginesX, marginesY+wysTekstu);
		g.drawString(matkaIN,  marginesX+szerokoscObszaru-szerNazwM, marginesY+wysTekstu);
		if (ojciec != null)
			klikMapa.dodajObszar(ojciec, marginesX, marginesY+wysTekstu, marginesX+szerNazwO, marginesY);
		if (matka != null)
			klikMapa.dodajObszar(matka, marginesX+szerokoscObszaru-szerNazwM, marginesY+wysTekstu, marginesX+szerokoscObszaru, marginesY);
	}
	
	private void rysujRodzenstwo(Graphics2D g, Person[] rodzenstwo, int x, int y)
	{
		Font f = g.getFont();
		g.setFont(new Font(f.getName(), Font.PLAIN, f.getSize()));
		
		FontMetrics fm = g.getFontMetrics();
		int wys = fm.getAscent()-fm.getDescent();
		int szer;
		
		for (int i=0; i<rodzenstwo.length; i++)
		{
			szer = fm.stringWidth(rodzenstwo[i].imieNazwisko());
			g.drawString(rodzenstwo[i].imieNazwisko(), x, y+(i+1)*(wys+odstepMiedzyRodzenstwem));
			klikMapa.dodajObszar(rodzenstwo[i], x, y+(i+1)*(wys+odstepMiedzyRodzenstwem), x+szer, y+(i+1)*(wys+odstepMiedzyRodzenstwem)-wys);
		}
		
		g.setFont(f);
	}
	
	private void rysujMalzonkow(Graphics2D g, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int wys = fm.getAscent()-fm.getDescent();
		int szer;
		Person malzonek;
		
		for (int i=0; i<osobaGlowna.liczbaMalzenstw(); i++)
		{
			malzonek = osobaGlowna.getMalzonek(i);
			szer = fm.stringWidth(malzonek.imieNazwisko());
			g.drawString(malzonek.imieNazwisko(), x, y+(i+1)*(wys+odstepMiedzyMalzonkami));
			klikMapa.dodajObszar(malzonek, x, y+(i+1)*(wys+odstepMiedzyMalzonkami), x+szer, y+(i+1)*(wys+odstepMiedzyMalzonkami)-wys);
		}		
	}
	
	private void rysujDzieci(Graphics2D g, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int wys = fm.getAscent()-fm.getDescent();
		int szer;
		Person dziecko;
		
		for (int i=0; i<osobaGlowna.liczbaDzieci(); i++)
		{
			dziecko = osobaGlowna.getDziecko(i);
			szer = fm.stringWidth(dziecko.imieNazwisko());
			g.drawString(dziecko.imieNazwisko(), x, y+(i+1)*(wys+odstepMiedzyDzieci));
			klikMapa.dodajObszar(dziecko, x, y+(i+1)*(wys+odstepMiedzyDzieci), x+szer, y+(i+1)*(wys+odstepMiedzyDzieci)-wys);
		}		
	}

	private int szerRodzenstwa(Graphics2D g, Person[] rodzenstwo) {
		Font f = g.getFont();
		g.setFont(new Font(f.getName(), Font.PLAIN, f.getSize()));
		
		FontMetrics fm = g.getFontMetrics();
		int maksSzer = 0;
		
		for(Person o: rodzenstwo)
			if (maksSzer < fm.stringWidth(o.imieNazwisko()))
				maksSzer = fm.stringWidth(o.imieNazwisko());

		
		g.setFont(f);
		return maksSzer;
	}

	private int szerMalzonkow(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		int maksSzer = 0;
		Person o;
		
		for (int i=0; i<osobaGlowna.liczbaMalzenstw(); i++)
		{
			o = osobaGlowna.getMalzonek(i);
			if (maksSzer < fm.stringWidth(o.imieNazwisko()))
				maksSzer = fm.stringWidth(o.imieNazwisko());
		}
		return maksSzer;
	}

	private int szerDzieci(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		int maksSzer = 0;
		Person o;
		
		for (int i=0; i<osobaGlowna.liczbaDzieci(); i++)
		{
			o = osobaGlowna.getDziecko(i);
			if (maksSzer < fm.stringWidth(o.imieNazwisko()))
				maksSzer = fm.stringWidth(o.imieNazwisko());
		}
		return maksSzer;
	}

//	@Override
//	public Dimension getWymiary() {
//		// TODO Auto-generated method stub
//		return new Dimension(szerokosc, wysokosc);
//	}
//
//	@Override
//	public Osoba getOsobaGlowna() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getSzerokosc() {
//		// TODO Auto-generated method stub
//		return szerokosc;
//	}
//
//	@Override
//	public int getWysokosc() {
//		// TODO Auto-generated method stub
//		return wysokosc;
//	}
}
