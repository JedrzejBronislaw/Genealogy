package nameDisplaying;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import model.Person;

public abstract class Name {
	
	Graphics2D g;
	FontMetrics fm;
	
	public Name() {}
	public Name(Graphics2D g) {
		setGraphics(g);
	}
	
	public void setGraphics(Graphics2D g) {
		this.g = g;
		this.fm = g.getFontMetrics();
	}
	
	abstract public void wyswietl(Person osoba, int x, int y);

	abstract public int getWysokosc(Person osoba);
	abstract public int getSzerokosc(Person osoba);
}
