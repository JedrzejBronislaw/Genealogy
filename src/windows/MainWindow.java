package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import model.Tree;
import treeGraphs.TreeGraph;
import treeGraphs.painter.nameDisplayers.DateAndNameDisplayer;
import treeGraphs.painter.nameDisplayers.SimpleNameDisplayer;
import treeGraphs.DrawingDescendantTreeGraph;
import treeGraphs.StdDescendantsTreeGraph;
import treeGraphs.StdAncestorsTreeGraph;
import visualComponents.Group;
import visualComponents.Field;

public class MainWindow extends JFrame implements AncestorListener, ActionListener {

	private static final long serialVersionUID = 310743672169670118L;
	Tree drzewo;
	SearchScreen wyszukiwarka;
	Field ostatnieOtwarcie = new Field("Ostatnie otwarcie");
	Field ostatniaZmiana = new Field("Ostatnia zmiana");
	Field liczbaOsob = new Field("Liczba osob");
	JPanel pNazwiska;
	
	public void setDrzewo(Tree d) {this.drzewo = d; wyszukiwarka.setDrzewo(d); wypelnijPola();}//{plutno.setGrafDrzewa(new GrafStdPotomkowie(d, d.getOsoba("34")));}//d.losowaOsoba()));}
	
	public MainWindow() {
		super("Pogologia 2.0");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		ustawKomponenty();
		
		setVisible(true);
		
	}
	
	private void test(String id)
	{
		TreeGraphWindow okno = new TreeGraphWindow();
		TreeGraph graf = null;
		
//			graf = new GrafStdPotomkowie();
//			graf = new GrafStdPrzodkowie();
			graf = new DrawingDescendantTreeGraph();
//		
//			graf.setWyswietlacz(new ZDatamiWyswietlanieNazwiska());
			graf.setNameDisplayer(new SimpleNameDisplayer());
		
		okno.setGraf(graf);
		okno.setOsoba(drzewo.getPerson(id));
	}

	private void ustawKomponenty() {

		JPanel plutno = new JPanel();
		JPanel pGlowny = new JPanel(new FlowLayout());
		JLabel tytul = new JLabel("Pogologia",JLabel.CENTER);
		wyszukiwarka = new SearchScreen();
		wyszukiwarka.setPreferredSize(new Dimension(330, 200));
		
		plutno.setLayout(new BorderLayout());
		plutno.setPreferredSize(new Dimension(350, 250));
		tytul.setFont(new Font("Times", Font.BOLD, 30));
		
		
		pGlowny.add(pInfo());
		pGlowny.add(pNazwiska());
		pGlowny.add(wyszukiwarka);
		
		plutno.add(tytul, BorderLayout.NORTH);
		plutno.add(pGlowny, BorderLayout.CENTER);
		add(plutno);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				wyszukiwarka.requestFocusInWindow();
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			
			@Override
			public void windowClosing(WindowEvent arg0) {}
			
			@Override
			public void windowClosed(WindowEvent arg0) {}

			@Override
			public void windowActivated(WindowEvent e) {}
			
		});
	}
	
	private JPanel pInfo()
	{
		Group panel = new Group();

		panel.add(ostatnieOtwarcie);
		panel.add(ostatniaZmiana);
		panel.add(liczbaOsob);
		
		return panel;
	}
	
	private JPanel pNazwiska()
	{
		pNazwiska = new Group();
		
		return pNazwiska;
	}
	
	private void wypelnijPola()
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");

		if (drzewo.getLastOpen() != null)
			ostatnieOtwarcie.set(sdf.format(drzewo.getLastOpen()));

		if (drzewo.getLastModification() != null)
			ostatniaZmiana.set(sdf.format(drzewo.getLastModification()));
		
		liczbaOsob.set(drzewo.getNumberOfPersons() + " (" + drzewo.getNumberOfPersons() + ")");
		
		pNazwiska.removeAll();
		String[] nazwiska = drzewo.getCommonSurnames();
		Field pole;
		for (int i=0; i<nazwiska.length; i++)
		{
			pole = new Field((i+1)+".");
			pole.set(nazwiska[i]);
			pNazwiska.add(pole);
		}
		test("100");//TODO wywalic
		test("392");//TODO wywalic
		test("477");//TODO wywalic
		test("58");//TODO wywalic
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
//		Object source = arg0.getSource();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

}
