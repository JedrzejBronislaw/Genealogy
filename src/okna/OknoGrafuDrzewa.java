package okna;

import grafyDrzewa.GrafDrzewa;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import dane.Drzewo;
import dane.Osoba;

public class OknoGrafuDrzewa extends JFrame {

	private Plutno plutno;
	private GrafDrzewa gd;
//	private Drzewo d;
	private Osoba o;
	
	public void setOsoba(Osoba o) {this.o = o; konstruuj();}
//	public void setDrzewo(Drzewo d) {this.d = d; konstruuj();}//plutno.setGrafDrzewa(gd.//(d, d.getOsoba("34")));}//d.losowaOsoba()));}
	public void setGraf(GrafDrzewa gd) {this.gd = gd; konstruuj();}
	
	private void konstruuj()
	{
		if ((gd != null) /*&& (d != null)*/ && (o != null))
		{
			plutno.setGrafDrzewa(gd);
//			gd.setDrzewo(d);
			gd.setOsobaGlowna(o);
		}
	}
	
	public OknoGrafuDrzewa() {
		super("Graf");
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);
		ustawKomponenty();
		
		setVisible(true);
	}

	private void ustawKomponenty() {
		
		plutno = new Plutno();
		plutno.setPreferredSize(new Dimension(1000, 1000));
//		plutno.setDoubleBuffered(true);
		JScrollPane scroll = new JScrollPane(plutno);
		
		add(scroll);
	}
}
