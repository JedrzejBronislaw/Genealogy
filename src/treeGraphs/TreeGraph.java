package treeGraphs;

import model.Person;
import nameDisplaying.Name;
import nameDisplaying.SimpleNameDisplaying;
import tools.Injection;
import treeGraphs.ClickMap.Obszar;
import windows.CardScreen;
import windows.Window;
import nameDisplaying.DateAndNameDisplaying;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.function.Consumer;

import lombok.Setter;

public abstract class TreeGraph {

	protected Person osobaGlowna;
//	protected Drzewo drzewo;
	protected int wysokosc;
	protected int szerokosc;
	protected Name wyswietlacz = new SimpleNameDisplaying();
	
	Window oknoDlaWizytowki = null;
	CardScreen wizytowka = null;
	
	protected ClickMap klikMapa = new ClickMap();
	
	public abstract void rysuj(Graphics2D g);
//	public abstract Dimension getWymiary();
//	public abstract int getSzerokosc();
//	public abstract int getWysokosc();
	
	@Setter
	private Consumer<Person> personClickAction;
	
	public Person getOsobaGlowna(){
		return osobaGlowna;
	}
//	public abstract void setDrzewo(Drzewo drzewo);
	public void setOsobaGlowna(Person osoba) {
		this.osobaGlowna = osoba;
	}
	public void setWyswietlacz(Name wyswietlacz) {
		this.wyswietlacz = wyswietlacz;
	}
	
	public void setOknoDlaWizytowki(Window okno) {oknoDlaWizytowki = okno;}
	public void setWizytowka(CardScreen wizytowka) {this.wizytowka = wizytowka;}
	
	public void klik(int x, int y) {
		Person osoba = klikMapa.ktoTuJest(x, y);
		if (osoba != null)
		{
			if (oknoDlaWizytowki != null)
				oknoDlaWizytowki.setEkran(new CardScreen(osoba));
			else
			if (wizytowka != null)
				wizytowka.setOsoba(osoba);
			else
				Injection.run(personClickAction, osoba);
		}
	}
	
	protected void rysujKlikObszary(Graphics2D g)
	{
		int n = klikMapa.liczbaObszarow();
		Obszar o;
		Color staryKolor = g.getColor();
		Stroke staraKreska = g.getStroke();

		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1));
		
		for (int i=0; i<n; i++) {
			o = klikMapa.getObszar(i);
			g.drawRect(o.x1, o.y1, o.x2-o.x1, o.y2-o.y1);
		}

		g.setColor(staryKolor);
		g.setStroke(staraKreska);
	}
	
	public Dimension getWymiary() {
		// TODO Auto-generated method stub
		return new Dimension(szerokosc, wysokosc);
	}

	public int getSzerokosc() {
		// TODO Auto-generated method stub
		return szerokosc;
	}

	public int getWysokosc() {
		// TODO Auto-generated method stub
		return wysokosc;
	}
}
