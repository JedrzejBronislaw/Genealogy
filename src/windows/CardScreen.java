package windows;

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

import model.Marriage;
import model.Person;
import model.Person.Plec;
import nameDisplaying.SimpleNameDisplaying;
import other.PersonDetails;
import treeGraphs.TreeGraph;
import treeGraphs.ClosestTreeGraph;
import treeGraphs.DrawingDescendantTreeGraph;
import treeGraphs.StdDescendantsTreeGraph;
import treeGraphs.StdAncestorsTreeGraph;
import nameDisplaying.DateAndNameDisplaying;
import visualComponents.Group;
import visualComponents.Field;
import visualComponents.SimpleTextField;

public class CardScreen extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6349934676842553270L;

	Person osoba;

	JButton pPotomkowie, pPrzodkowie, pPotRysunek;
	JCheckBox datyNaGrafie = new JCheckBox("Daty");
	JPanel panelGlowny = new JPanel();
	JScrollPane scroll;
	
	//TODO temp
	JLabel szerokosc = new JLabel("sz: ");
	
	Field imie = new Field("Imiê");
	Field nazwisko = new Field("Nazwisko");
	Field pseudonim = new Field("Pseudonim");
	Field dataUr = new Field("Data urodzenia");
	Field dataSm = new Field("Data œmierci");
	Field miejsceUr = new Field("Miejsce urodzenia");
	Field miejsceSm = new Field("Miejsce œmierci");
	Field parafiaChrztu = new Field("Parafia chrztu");
	Field miejscePochowku = new Field("Miejsce pochówku");
	JPanel pSluby;
	SimpleTextField uwagi = new SimpleTextField("Uwagi");
	SimpleTextField kontakt = new SimpleTextField("Kontakt");
	
	Canvas plutnoGrafu;
	ClosestTreeGraph grafMiniDrzewo = new ClosestTreeGraph();
	
	Field wiek = new Field();
	
	
	public CardScreen(Person osoba) {
		this.osoba = osoba;
		
		ustawKomponenty();
		wypelnijPola();
	}
	
	public void setOsoba(Person osoba)
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
		Group pOgolny = new Group();
		
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
		Group panel = new Group();
		
		panel.add(dataUr);
		panel.add(miejsceUr);
		panel.add(parafiaChrztu);
		panel.add(dataSm);
		panel.add(miejsceSm);
		panel.add(miejscePochowku);
		
		return panel;
	}
	
	private JPanel pPrzyciski() {
		Group panel = new Group();//(3,1);
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
		pSluby = new Group();
		return pSluby;
	}
	
	private void wypelnijSluby() {
		Field[] sluby = new Field[osoba.liczbaMalzenstw()];
		Marriage m;

		pSluby.removeAll();
		for (int i=0; i<osoba.liczbaMalzenstw(); i++)
		{
			m = osoba.getMalzenstwo(i);
			sluby[i] = new Field();
			sluby[i].setNazwa(osoba.getMalzonek(i).imieNazwisko());
			sluby[i].set(m.getData() + " " + m.getMiejsce());
			pSluby.add(sluby[i]);
		}
	}

	private JPanel pDodatkoweInformacje() {
		Group panel = new Group();
		
		
		panel.add(wiek);
		
		return panel;
	}
	
	private JPanel pUwagi()
	{
		Group panel = new Group(1,1);
		panel.add(uwagi);
		return panel;
	}
	
	private JPanel pKontakt()
	{
		Group panel = new Group(1,1);
		panel.add(kontakt);
		return panel;
	}
	
	private JPanel pMiniDrzewo() {
		Group panel = new Group(1,1);
		plutnoGrafu = new Canvas();
		
		plutnoGrafu.setGrafDrzewa(grafMiniDrzewo);
		
		panel.add(plutnoGrafu);
		
		return panel;
	}

	private void wypelnijPola() {
		szerokosc.setText("sz: "+PersonDetails.szerokoscGaleziPotomkow(osoba));
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


		if (osoba.getZyje() != Person.Zyje.NIE)
			wiek.setNazwa("¯yje: ");
		else
		if (osoba.getPlec() == Plec.Kobieta)
			wiek.setNazwa("¯y³a: ");
		else
			wiek.setNazwa("¯y³: ");
		wiek.set(PersonDetails.wiekStr(osoba));
		
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
			TreeGraphWindow okno = new TreeGraphWindow();
			TreeGraph graf = null;
			
			if (source == pPotomkowie)
				graf = new StdDescendantsTreeGraph();
			else if (source == pPrzodkowie)
				graf = new StdAncestorsTreeGraph();
			else if (source == pPotRysunek)
				graf = new DrawingDescendantTreeGraph();
//			
			if (datyNaGrafie.isSelected())
				graf.setWyswietlacz(new DateAndNameDisplaying());
			else
				graf.setWyswietlacz(new SimpleNameDisplaying());
			
			okno.setGraf(graf);
			okno.setOsoba(osoba);
		}
	}
}
