package wyswietlanie;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import dane.Osoba;

public abstract class Nazwisko {
	
	Graphics2D g;
	FontMetrics fm;
	
	public Nazwisko() {}
	public Nazwisko(Graphics2D g) {
		setGraphics(g);
	}
	
	public void setGraphics(Graphics2D g) {
		this.g = g;
		this.fm = g.getFontMetrics();
	}
	
	abstract public void wyswietl(Osoba osoba, int x, int y);

	abstract public int getWysokosc(Osoba osoba);
	abstract public int getSzerokosc(Osoba osoba);
}
