package treeGraphs;

import model.Person;
import other.PersonDetails;
import tools.Gradient;
import treeGraphs.DrawingDescendantTreeGraphCalculation;
import treeGraphs.DrawingDescendantTreeGraphCalculation.DlugoscGalezi;
import treeGraphs.DrawingDescendantTreeGraphCalculation.Element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.List;

public class DrawingDescendantTreeGraph extends TreeGraph {

	private float katPocz = 155;
	private float katCaly = 130;
	
	public void setKatPocz(float katWStopniach) {
		this.katPocz = katWStopniach;
	}
	public void setKatCaly(float katWStopniach) {
		this.katCaly = katWStopniach;
	}
	public void setSymetrycznyKat(float szerokoscWSt) {
		this.katCaly = szerokoscWSt;
		this.katPocz = 180-(180-katCaly)/2;
	}
	
	public DrawingDescendantTreeGraph() {
		setSymetrycznyKat(150);
	}
	
	@Override
	public void rysuj(Graphics2D g) {
		int marginesX = 50, marginesY = 50;
		
		klikMapa.wyczysc();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawString(osobaGlowna.nameSurname(), 10, 20);
		g.drawString("Pokoleñ potomków: " + osobaGlowna.descendantGenerations()+"", 10, 30);
		g.drawString("Szerokoœæ: " + PersonDetails.descendantsBranchesWidth(osobaGlowna)+"", 10, 40);
		g.drawString("Liczba dzieci: " + osobaGlowna.numberOfChildren(), 10, 50);

//		srodekX = osobaGlowna.liczbaPokolenPotomkow()*50 + 50;
//		srodekY = osobaGlowna.liczbaPokolenPotomkow()*50 + 50;
//
//		szerokosc = osobaGlowna.liczbaPokolenPotomkow()*100+100;
//		wysokosc = osobaGlowna.liczbaPokolenPotomkow()*50 + 100;
		
		
		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(150, 75, 0));
//		rysujGalaz(g, osobaGlowna, 1/*katPocz*/, srodekX, srodekY, 0);
		
		DrawingDescendantTreeGraphCalculation obliczenia = new DrawingDescendantTreeGraphCalculation(osobaGlowna)
				.setSymetrycznyKat(katCaly)
				.setOdlegloscMiedzyNajmlodszymiPokoleniami(15)
//				.setRoznicaDlugosciGaleziWzgledemRodzica(-10)
//				.setDlugoscGalezi(DlugoscGalezi.Losowa)
				.setPrzesuniecie(marginesX, marginesY);
		List<Element> plan = obliczenia.get();

//		Gradient grad = new Gradient(new Color(75, 38, 0), Color.GREEN);
		Gradient grad = new Gradient(Color.BLACK, new Color(150, 75, 0));
		
		for (Element e : plan)
			for (Element e2 : e.getPoloczenie())
			{
				g.setColor(grad.getKolorPosredni((float)e.getPokolenie()/osobaGlowna.descendantGenerations()));
				g.setStroke(new BasicStroke(5-5*e.getPokolenie()/osobaGlowna.descendantGenerations(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g.drawLine(e.getX(), e.getY(), e2.getX(), e2.getY());
				g.setStroke(new BasicStroke(1));
			}
		
		for (Element e : plan) {
			if (e.getPoloczenie().size() == 0)
				rysujPunkt(g, e.getX(), e.getY(), Color.GREEN);
			klikMapa.dodajObszar(e.getOsoba(), e.getX() - 5, e.getY() - 5, e.getX() + 5, e.getY() + 5);
		}

		Dimension wymiary = obliczenia.getWymiary();
		Point korzen = obliczenia.getKorzen();
		
//		g.setColor(Color.RED);
//		g.drawRect(marginesX, marginesX, wymiary.width, wymiary.height);

		wysokosc  = wymiary.height + marginesY * 2;
		szerokosc = wymiary.width  + marginesX * 2;

//		rysujPunkt(g, korzen.x+marginesX, korzen.y+marginesY, Color.BLACK);		
//		g.drawArc(50, 50, szerokosc-100, wysokosc*2-200, (int)katPocz, (int)-katCaly);
//		rysujPromien(g, srodekX, srodekY, 0, 200, Color.BLACK);
//		rysujPromien(g, srodekX, srodekY, 1, 200, Color.BLACK);	
	}
	

	private void rysujPunkt(Graphics2D g, int x, int y, Color kolor)
	{
		Color kolorPoprzedni = g.getColor();
		Stroke strokePoprzedni = g.getStroke();
		
		g.setColor(kolor);
		g.setStroke(new BasicStroke(1));
		g.fillOval(x-5, y-5, 10, 10);
		g.fillRect(x, y-5, 5, 5);
//		g.drawLine(x-5, y-5, x+5, y+5);
//		g.drawLine(x-5, y+5, x+5, y-5);
		
		g.setColor(kolorPoprzedni);
		g.setStroke(strokePoprzedni);
	}
	


}
