package treeGraphs;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import model.Person;
import model.Tree;

public class StdAncestorsTreeGraph extends TreeGraph {


	private static int marginesX = 20;
	private static int marginesY = 20;
	private static int wysWiersza = 40;
	private static int szerKolumny = 177;

	private static int odstepPionowy = 15;
	private static int odstepPoziomy = 50;
	
	private int y2 = 0;
	private int[] szerokosciKolumn;

	private Font font = new Font("Times", Font.PLAIN, 13);
	
//	@Override
//	public void setDrzewo(Drzewo drzewo) {
//		this.drzewo = drzewo;
//	}
	
	public StdAncestorsTreeGraph() {}
	public StdAncestorsTreeGraph(/*Drzewo drzewo, */Person osoba) {
//		this.drzewo = drzewo;
		this.osobaGlowna = osoba;
	}
	
	@Override
	public void rysuj(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		Font f = g.getFont();
//		g.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+3));
		
//		Font f = g.getFont();
//		System.out.println("name: "+f.getName() + " | size: " + f.getSize());
		
		g.setFont(font);
		wyswietlacz.setGraphics(g);
		
		klikMapa.wyczysc();
		if (/*(drzewo == null) || */(osobaGlowna == null))
			return;
		
		szerokosc = ustalSzerokosciKolumn(g.getFontMetrics());//+odstepPoziomy*osobaGlowna.rozmiarKorzenia();
		
		y2 = marginesY;
		rysujKorzen(g, osobaGlowna, 0, 0);//marginesY);
		wysokosc  = y2-odstepPionowy;
//		szerokosc = (osobaGlowna.rozmiarKorzenia()+1) * szerKolumny;
		
		wysokosc  += marginesY;
		szerokosc += marginesX;
	}
	
	private int ustalSzerokosciKolumn(FontMetrics fm) {
		szerokosciKolumn = new int[osobaGlowna.rozmiarKorzenia()+2];
		
		//Ustalenie maksymalnych szerokosci nazwisk
		szerokosciKolumn[0] = wyswietlacz.getSzerokosc(osobaGlowna);//fm.stringWidth(osobaGlowna.imieNazwisko());
		szerokoscRodzicow(fm, osobaGlowna, 1);
		
		//obieczenie prawej granicy (z miejscem na strza³ki)
		szerokosciKolumn[0] += odstepPoziomy;
		for (int i=1; i<szerokosciKolumn.length; i++)
			szerokosciKolumn[i] += szerokosciKolumn[i-1]+odstepPoziomy;
		
		//ustalenie lewej granicy (prawej granicy sasiada z lewej strony)
		for (int i=szerokosciKolumn.length-1; i>0; i--)
			szerokosciKolumn[i] = szerokosciKolumn[i-1];
		szerokosciKolumn[0] = 0;
		
		//przesuniecie ca³oœci o margines
		for (int i=0; i<szerokosciKolumn.length; i++)
			szerokosciKolumn[i] += marginesX;

		//w ostatniej kolumnie pozosta³a prawa granica ostatniej kolumny
		return szerokosciKolumn[szerokosciKolumn.length-1]-odstepPoziomy;
	}
	
	private int szerokoscRodzicow(FontMetrics fm, Person osoba, int pokolenie)
	{
		int matka  = (osoba.getMatka()  == null) ? 0 : wyswietlacz.getSzerokosc(osoba.getMatka());//fm.stringWidth(osoba.getMatka().imieNazwisko());
		int ojciec = (osoba.getOjciec() == null) ? 0 : wyswietlacz.getSzerokosc(osoba.getOjciec());//fm.stringWidth(osoba.getOjciec().imieNazwisko());
		int szersze = (ojciec > matka) ? ojciec : matka;
		
		if (szerokosciKolumn[pokolenie] < szersze)
			szerokosciKolumn[pokolenie] = szersze;

		if (osoba.getMatka()  != null) szerokoscRodzicow(fm, osoba.getMatka(),  pokolenie+1);
		if (osoba.getOjciec() != null) szerokoscRodzicow(fm, osoba.getOjciec(), pokolenie+1);
		
		return szersze;
	}

	private int rysujKorzen(Graphics2D g, Person osoba, int pokolenie, int y1)
	{		
		int wysNazw  = wyswietlacz.getWysokosc(osoba);
		int szerNazw = wyswietlacz.getSzerokosc(osoba);

		Person ojciec = osoba.getOjciec();
		Person matka  = osoba.getMatka();
		int oy, my;
		int x,y;
		
		if ((matka != null) || (ojciec != null))
		{
			if (ojciec != null)
				oy = rysujKorzen(g, ojciec, pokolenie+1, y2);
			else {
				oy = y1;
				y2 = y1+wysWiersza;
			}
			if (matka != null)
				my = rysujKorzen(g, matka, pokolenie+1, y2);
			else {
				my = oy+wysWiersza;
				y2 = y2+wysWiersza;
			}
			
			x = szerokosciKolumn[pokolenie];
			y = ((my-oy) / 2) + oy;
			wyswietlacz.wyswietl(osoba, x, y+wysNazw);
			klikMapa.dodajObszar(osoba, x, y+wysNazw, x+szerNazw, y);
			
			//grot
			g.drawLine(x+szerNazw+10, y+wysNazw/2, x+szerNazw+20, y+wysNazw/2 -3);
			g.drawLine(x+szerNazw+10, y+wysNazw/2, x+szerNazw+20, y+wysNazw/2 +3);
			//linia do dziecka
			g.drawLine(x+szerNazw+10, y+wysNazw/2, szerokosciKolumn[pokolenie+1]-15, y+wysNazw/2);
			//linia pionowa
			g.drawLine(szerokosciKolumn[pokolenie+1]-15, oy+wysNazw/2, szerokosciKolumn[pokolenie+1]-15, my+wysNazw/2);
			//linia do ojca
			g.drawLine(szerokosciKolumn[pokolenie+1]-15, oy+wysNazw/2, szerokosciKolumn[pokolenie+1]-5, oy+wysNazw/2);
			//linia do matki
			g.drawLine(szerokosciKolumn[pokolenie+1]-15, my+wysNazw/2, szerokosciKolumn[pokolenie+1]-5, my+wysNazw/2);
			
			return y;
		} else
		{
			x = szerokosciKolumn[pokolenie];
			wyswietlacz.wyswietl(osoba, x, y1+wysNazw);
			//klikdrzewo
			klikMapa.dodajObszar(osoba, x, y1+wysNazw, szerokosciKolumn[pokolenie+1]-odstepPoziomy, y1);
			y2 = y1+wysNazw/*przesuniecie*/+odstepPionowy;//wysWiersza;
			return y1;
		}
	}

//	private int rysujKorzen(Graphics2D g, Osoba osoba, int x1, int y1)
//	{		
//		
//		FontMetrics fm = g.getFontMetrics();
//		String nazwisko = osoba.imieNazwisko();
//		int wysNazw  = fm.getAscent();
//		int przesuniecie = wysNazw*4/5;
//		int szerNazw = fm.stringWidth(nazwisko);
//		
//		Osoba ojciec = osoba.getOjciec();
//		Osoba matka  = osoba.getMatka();
//		int oy, my;
//		int x,y;
//		
//		if ((matka != null) || (ojciec != null))
//		{
//			if (ojciec != null)
//				oy = rysujKorzen(g, ojciec, x1+szerKolumny, y2);
//			else {
//				oy = y1;
//				y2 = y1+wysWiersza;
//			}
//			if (matka != null)
//				my = rysujKorzen(g, matka, x1+szerKolumny, y2);
//			else {
//				my = oy+wysWiersza;
//				y2 = y2+wysWiersza;
//			}
//			
//			x = x1;
//			y = ((my-oy) / 2) + oy;
//			g.drawString(nazwisko, x, y+przesuniecie);
//			klikMapa.dodajObszar(osoba, x, y+przesuniecie, x+szerNazw, y+przesuniecie-wysNazw);
//			//TODO daty
//			
//			//grot
//			g.drawLine(x+szerNazw+10, y+wysNazw/2, x+szerNazw+20, y+wysNazw/2 -3);
//			g.drawLine(x+szerNazw+10, y+wysNazw/2, x+szerNazw+20, y+wysNazw/2 +3);
//			//linia do dziecka
//			g.drawLine(x+szerNazw+10, y+wysNazw/2, x+szerKolumny-15, y+wysNazw/2);
//			//linia pionowa
//			g.drawLine(x+szerKolumny-15, oy+wysNazw/2, x+szerKolumny-15, my+wysNazw/2);
//			//linia do ojca
//			g.drawLine(x+szerKolumny-15, oy+wysNazw/2, x+szerKolumny-5, oy+wysNazw/2);
//			//linia do matki
//			g.drawLine(x+szerKolumny-15, my+wysNazw/2, x+szerKolumny-5, my+wysNazw/2);
//			
//			return y;
//		} else
//		{
//			g.drawString(nazwisko, x1, y1+przesuniecie);
//			//TODO klikdrzewo
//			klikMapa.dodajObszar(osoba, x1, y1+przesuniecie, x1+szerNazw, y1+przesuniecie-wysNazw);
//			//TODO daty
//			y2 = y1+przesuniecie+odstepPionowy;//wysWiersza;
//			return y1;
//		}
//	}

//	@Override
//	public Dimension getWymiary() {
//		// TODO Auto-generated method stub
//		return new Dimension(szerokosc, wysokosc);
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
