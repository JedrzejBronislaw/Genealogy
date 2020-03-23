package okna;

import grafyDrzewa.GrafDrzewa;
import grafyDrzewa.GrafMiniDrzewo;
import grafyDrzewa.GrafPotomkowieRysunek;
import grafyDrzewa.GrafStdPotomkowie;
import grafyDrzewa.GrafStdPrzodkowie;
import inne.OOsobie;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import komponentyWizualne.Grupa;
import komponentyWizualne.Pole;
import komponentyWizualne.ProstePoleTekstowe;
import wyswietlanie.ProsteWyswietlanieNazwiska;
import wyswietlanie.ZDatamiWyswietlanieNazwiska;
import dane.Malzenstwo;
import dane.Osoba;
import dane.Osoba.Plec;

public class EkranWizytowka extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6349934676842553270L;

	Osoba osoba;

	JButton pPotomkowie, pPrzodkowie, pPotRysunek;
	JCheckBox datyNaGrafie = new JCheckBox("Daty");
	JPanel panelGlowny = new JPanel();
	JScrollPane scroll;
	
	//TODO temp
	JLabel szerokosc = new JLabel("sz: ");
	
	Pole imie = new Pole("Imiê");
	Pole nazwisko = new Pole("Nazwisko");
	Pole pseudonim = new Pole("Pseudonim");
	Pole dataUr = new Pole("Data urodzenia");
	Pole dataSm = new Pole("Data œmierci");
	Pole miejsceUr = new Pole("Miejsce urodzenia");
	Pole miejsceSm = new Pole("Miejsce œmierci");
	Pole parafiaChrztu = new Pole("Parafia chrztu");
	Pole miejscePochowku = new Pole("Miejsce pochówku");
	JPanel pSluby;
	ProstePoleTekstowe uwagi = new ProstePoleTekstowe("Uwagi");
	ProstePoleTekstowe kontakt = new ProstePoleTekstowe("Kontakt");
	
	Plutno plutnoGrafu;
	GrafMiniDrzewo grafMiniDrzewo = new GrafMiniDrzewo();
	
	Pole wiek = new Pole();
	
	
	public EkranWizytowka(Osoba osoba) {
		this.osoba = osoba;
		
		ustawKomponenty();
		wypelnijPola();
	}
	
	public void setOsoba(Osoba osoba)
	{
		this.osoba = osoba;
		wypelnijPola();		
	}

	private void ustawKomponenty() {
		
		panelGlowny = new JPanel(new FlowLayout());
		scroll = new JScrollPane(panelGlowny);
		setLayout(new BorderLayout());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent e) {
				ustawWelkoscPanelu();
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		grafMiniDrzewo.setWizytowka(this);
		
		panelGlowny.add(szerokosc);
		panelGlowny.add(pOgolny());
		panelGlowny.add(pDaty());
		panelGlowny.add(pMiniDrzewo());
		panelGlowny.add(pPrzyciski());
		panelGlowny.add(pSluby());
		panelGlowny.add(pDodatkoweInformacje());
		panelGlowny.add(pUwagi());
		panelGlowny.add(pKontakt());
		
		add(scroll, BorderLayout.CENTER);
		ustawWelkoscPanelu();
	}
	
	private JPanel pOgolny() {
		Grupa pOgolny = new Grupa();
		
		pOgolny.add(imie);
		pOgolny.add(nazwisko);
		pOgolny.add(pseudonim);
		
		pOgolny.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent arg0) {}
			@Override
			public void componentMoved(ComponentEvent arg0) {
				ustawWelkoscPanelu();
			}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		return pOgolny;
	}
	
	private JPanel pDaty() {
		Grupa panel = new Grupa();
		
		panel.add(dataUr);
		panel.add(miejsceUr);
		panel.add(parafiaChrztu);
		panel.add(dataSm);
		panel.add(miejsceSm);
		panel.add(miejscePochowku);
		
		return panel;
	}
	
	private JPanel pPrzyciski() {
		Grupa panel = new Grupa();//(3,1);
		pPotomkowie = new JButton("Potomkowie");
		pPrzodkowie = new JButton("Przodkowie");
		pPotRysunek = new JButton("Potomkowie Rysunek");
		
		pPotomkowie.addActionListener(this);
		pPrzodkowie.addActionListener(this);
		pPotRysunek.addActionListener(this);
		
		panel.add(pPotomkowie);
		panel.add(pPrzodkowie);
		panel.add(pPotRysunek);
		panel.add(datyNaGrafie);
		
		return panel;
	}
	
	private JPanel pSluby() {
		pSluby = new Grupa();
		return pSluby;
	}
	
	private void wypelnijSluby() {
		Pole[] sluby = new Pole[osoba.liczbaMalzenstw()];
		Malzenstwo m;

		pSluby.removeAll();
		for (int i=0; i<osoba.liczbaMalzenstw(); i++)
		{
			m = osoba.getMalzenstwo(i);
			sluby[i] = new Pole();
			sluby[i].setNazwa(osoba.getMalzonek(i).imieNazwisko());
			sluby[i].set(m.getData() + " " + m.getMiejsce());
			pSluby.add(sluby[i]);
		}
	}

	private JPanel pDodatkoweInformacje() {
		Grupa panel = new Grupa();
		
		
		panel.add(wiek);
		
		return panel;
	}
	
	private JPanel pUwagi()
	{
		Grupa panel = new Grupa(1,1);
		panel.add(uwagi);
		return panel;
	}
	
	private JPanel pKontakt()
	{
		Grupa panel = new Grupa(1,1);
		panel.add(kontakt);
		return panel;
	}
	
	private JPanel pMiniDrzewo() {
		Grupa panel = new Grupa(1,1);
		plutnoGrafu = new Plutno();
		
		plutnoGrafu.setGrafDrzewa(grafMiniDrzewo);
		
		panel.add(plutnoGrafu);
		
		return panel;
	}

	private void wypelnijPola() {
		szerokosc.setText("sz: "+OOsobie.szerokoscGaleziPotomkow(osoba));
		imie.set(osoba.getImie());
		nazwisko.set(osoba.getNazwisko());
		pseudonim.set(osoba.getPseudonim());
		dataUr.set(osoba.getDataUrodzenia().toString());
		miejsceUr.set(osoba.getMiejsceUrodzenia());
		dataSm.set(osoba.getDataSmierci().toString());
		miejsceSm.set(osoba.getMiejsceSmierci());
		wypelnijSluby();
		uwagi.set(osoba.getUwagi());
		kontakt.set(osoba.getKontakt());
		parafiaChrztu.set(osoba.getParafiaChrztu());
		miejscePochowku.set(osoba.getMiejscePochowku());

		grafMiniDrzewo.setOsobaGlowna(osoba);
		plutnoGrafu.repaint();


		if (osoba.getZyje() != Osoba.Zyje.NIE)
			wiek.setNazwa("¯yje: ");
		else
		if (osoba.getPlec() == Plec.Kobieta)
			wiek.setNazwa("¯y³a: ");
		else
			wiek.setNazwa("¯y³: ");
		wiek.set(OOsobie.wiekStr(osoba));
		
//		pseudonim.setVisible(osoba.getPseudonim() != null);
//		dataUr.setVisible(osoba.getDataUrodzenia() != null);
//		miejsceUr.setVisible(osoba.getMiejsceUrodzenia() != null);
//		parafiaChrztu.setVisible(osoba.getParafiaChrztu() != null);
//		dataSm.setVisible(osoba.getDataSmierci() != null);
//		miejsceSm.setVisible(osoba.getMiejsceSmierci() != null);
//		miejscePochowku.setVisible(osoba.getMiejscePochowku() != null);
	}


	
	private void ustawWelkoscPanelu()
	{
		if ((panelGlowny != null) &&(scroll != null) && (panelGlowny.getComponentCount() > 0))
		{
			Component lastComponent = panelGlowny.getComponent(panelGlowny.getComponentCount()-1);
			panelGlowny.setPreferredSize(new Dimension(scroll.getViewport().getWidth(), lastComponent.getY() + lastComponent.getHeight()));
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();

		if ((source == pPrzodkowie) || (source == pPotomkowie) || (source == pPotRysunek))
		{
			OknoGrafuDrzewa okno = new OknoGrafuDrzewa();
			GrafDrzewa graf = null;
			
			if (source == pPotomkowie)
				graf = new GrafStdPotomkowie();
			else if (source == pPrzodkowie)
				graf = new GrafStdPrzodkowie();
			else if (source == pPotRysunek)
				graf = new GrafPotomkowieRysunek();
//			
			if (datyNaGrafie.isSelected())
				graf.setWyswietlacz(new ZDatamiWyswietlanieNazwiska());
			else
				graf.setWyswietlacz(new ProsteWyswietlanieNazwiska());
			
			okno.setGraf(graf);
			okno.setOsoba(osoba);
		}
	}
}
