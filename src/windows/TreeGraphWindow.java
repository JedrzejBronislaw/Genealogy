package windows;

import model.Person;
import model.Tree;
import treeGraphs.TreeGraph;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TreeGraphWindow extends JFrame {

	private Canvas plutno;
	private TreeGraph gd;
//	private Drzewo d;
	private Person o;
	
	public void setOsoba(Person o) {this.o = o; konstruuj();}
//	public void setDrzewo(Drzewo d) {this.d = d; konstruuj();}//plutno.setGrafDrzewa(gd.//(d, d.getOsoba("34")));}//d.losowaOsoba()));}
	public void setGraf(TreeGraph gd) {this.gd = gd; konstruuj();}
	
	private void konstruuj()
	{
		if ((gd != null) /*&& (d != null)*/ && (o != null))
		{
			plutno.setGrafDrzewa(gd);
//			gd.setDrzewo(d);
			gd.setOsobaGlowna(o);
		}
	}
	
	public TreeGraphWindow() {
		super("Graf");
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);
		ustawKomponenty();
		
		setVisible(true);
	}

	private void ustawKomponenty() {
		
		plutno = new Canvas();
		plutno.setPreferredSize(new Dimension(1000, 1000));
//		plutno.setDoubleBuffered(true);
		JScrollPane scroll = new JScrollPane(plutno);
		
		add(scroll);
	}
}
