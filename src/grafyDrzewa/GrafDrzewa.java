package grafyDrzewa;

import grafyDrzewa.MapaKlikania.Obszar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Stroke;

import okna.EkranWizytowka;
import okna.Okno;
import wyswietlanie.Nazwisko;
import wyswietlanie.ProsteWyswietlanieNazwiska;
import wyswietlanie.ZDatamiWyswietlanieNazwiska;
import dane.Osoba;

public abstract class GrafDrzewa {

	protected Osoba osobaGlowna;
//	protected Drzewo drzewo;
	protected int wysokosc;
	protected int szerokosc;
	protected Nazwisko wyswietlacz = new ProsteWyswietlanieNazwiska();
	
	Okno oknoDlaWizytowki = null;
	EkranWizytowka wizytowka = null;
	
	protected MapaKlikania klikMapa = new MapaKlikania();
	
	public abstract void rysuj(Graphics2D g);
//	public abstract Dimension getWymiary();
//	public abstract int getSzerokosc();
//	public abstract int getWysokosc();
	
	public Osoba getOsobaGlowna(){
		return osobaGlowna;
	}
//	public abstract void setDrzewo(Drzewo drzewo);
	public void setOsobaGlowna(Osoba osoba) {
		this.osobaGlowna = osoba;
	}
	public void setWyswietlacz(Nazwisko wyswietlacz) {
		this.wyswietlacz = wyswietlacz;
	}
	
	public void setOknoDlaWizytowki(Okno okno) {oknoDlaWizytowki = okno;}
	public void setWizytowka(EkranWizytowka wizytowka) {this.wizytowka = wizytowka;}
	
	public void klik(int x, int y) {
		Osoba osoba = klikMapa.ktoTuJest(x, y);
		if (osoba != null)
		{
			if (oknoDlaWizytowki != null)
				oknoDlaWizytowki.setEkran(new EkranWizytowka(osoba));
			else
			if (wizytowka != null)
				wizytowka.setOsoba(osoba);
			else
				new Okno(new EkranWizytowka(osoba));
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
