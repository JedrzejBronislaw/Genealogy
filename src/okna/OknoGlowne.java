package okna;

import grafyDrzewa.GrafDrzewa;
import grafyDrzewa.GrafPotomkowieRysunek;
import grafyDrzewa.GrafStdPotomkowie;
import grafyDrzewa.GrafStdPrzodkowie;

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

import wyswietlanie.ProsteWyswietlanieNazwiska;
import wyswietlanie.ZDatamiWyswietlanieNazwiska;

import komponentyWizualne.Grupa;
import komponentyWizualne.Pole;
import dane.Drzewo;

public class OknoGlowne extends JFrame implements AncestorListener, ActionListener {

	private static final long serialVersionUID = 310743672169670118L;
	Drzewo drzewo;
	EkranWyszukiwarka wyszukiwarka;
	Pole ostatnieOtwarcie = new Pole("Ostatnie otwarcie");
	Pole ostatniaZmiana = new Pole("Ostatnia zmiana");
	Pole liczbaOsob = new Pole("Liczba osob");
	JPanel pNazwiska;
	
	public void setDrzewo(Drzewo d) {this.drzewo = d; wyszukiwarka.setDrzewo(d); wypelnijPola();}//{plutno.setGrafDrzewa(new GrafStdPotomkowie(d, d.getOsoba("34")));}//d.losowaOsoba()));}
	
	public OknoGlowne() {
		super("Pogologia 2.0");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		ustawKomponenty();
		
		setVisible(true);
		
	}
	
	private void test(String id)
	{
		OknoGrafuDrzewa okno = new OknoGrafuDrzewa();
		GrafDrzewa graf = null;
		
//			graf = new GrafStdPotomkowie();
//			graf = new GrafStdPrzodkowie();
			graf = new GrafPotomkowieRysunek();
//		
//			graf.setWyswietlacz(new ZDatamiWyswietlanieNazwiska());
			graf.setWyswietlacz(new ProsteWyswietlanieNazwiska());
		
		okno.setGraf(graf);
		okno.setOsoba(drzewo.getOsoba(id));
	}

	private void ustawKomponenty() {

		JPanel plutno = new JPanel();
		JPanel pGlowny = new JPanel(new FlowLayout());
		JLabel tytul = new JLabel("Pogologia",JLabel.CENTER);
		wyszukiwarka = new EkranWyszukiwarka();
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
		Grupa panel = new Grupa();

		panel.add(ostatnieOtwarcie);
		panel.add(ostatniaZmiana);
		panel.add(liczbaOsob);
		
		return panel;
	}
	
	private JPanel pNazwiska()
	{
		pNazwiska = new Grupa();
		
		return pNazwiska;
	}
	
	private void wypelnijPola()
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");

		if (drzewo.getOstatnieOtwarcie() != null)
			ostatnieOtwarcie.set(sdf.format(drzewo.getOstatnieOtwarcie()));

		if (drzewo.getOstatniaZmiana() != null)
			ostatniaZmiana.set(sdf.format(drzewo.getOstatniaZmiana()));
		
		liczbaOsob.set(drzewo.getLiczbaOsob() + " (" + drzewo.getLiczbaOsob() + ")");
		
		pNazwiska.removeAll();
		String[] nazwiska = drzewo.getGlowneNazwiska();
		Pole pole;
		for (int i=0; i<nazwiska.length; i++)
		{
			pole = new Pole((i+1)+".");
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
