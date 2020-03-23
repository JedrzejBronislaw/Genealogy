package okna;

import inne.OOsobie;
import inne.Stale;
import inne.Zdrobnienia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import narzedzia.Narzedzia;
import dane.Drzewo;
import dane.Osoba;
import dane.Osoba.Plec;

public class EkranWyszukiwarka extends JPanel implements KeyListener, MouseListener {

	
	JTextField pytanie = new JTextField();
	JList<String> lista = new JList<String>();
	LinkedList<Osoba> wyszukane = new LinkedList<Osoba>();
	Drzewo drzewo;
	Osoba[] wszyscy;
	LinkedHashMap<Osoba, String> spisSlowKluczowych = new LinkedHashMap<Osoba, String>();
	String stareZapytanie = "";
	JLabel liczbaZnalezionych = new JLabel();
	Zdrobnienia zdrobnienia = null;
	public static String sciezkaDoDodatkowychZdrobnien = Narzedzia.sciezkaFolderuZJarem()+"zdrobnienia.ust";

	
	public void setDrzewo(Drzewo drzewo) {
		this.drzewo = drzewo;
		aktualizujSpisOsob();
	}
	
	public EkranWyszukiwarka() {
		ustawKomponenty();
	}
	
	public EkranWyszukiwarka(Drzewo drzewo) {
		this.drzewo = drzewo;
		aktualizujSpisOsob();
		ustawKomponenty();
	}
	
	public void aktualizujSpisOsob()
	{
		zdrobnienia = new Zdrobnienia(sciezkaDoDodatkowychZdrobnien);
		wszyscy = drzewo.getWszyscy();
		spisSlowKluczowych.clear();
		for (Osoba o : wszyscy)
			spisSlowKluczowych.put(o, generujCiag(o));
	}

	private String generujCiag(Osoba o) {
		String ciag = o.imieNazwisko();
		
		if (o.getPlec() == Plec.Kobieta)
			for (int i=0; i<o.liczbaMalzenstw(); i++)
				ciag += " " + o.getMalzonek(i).getNazwisko();

		ciag += " " +o.getPseudonim();
		ciag += " " +Zdrobnienia.dlaImieniaW(o.getImie());
		ciag += " " +zdrobnienia.dlaImienia(o.getImie());
		
		
		ciag = ciag.toUpperCase();		
		return Narzedzia.usunPolskieZnaki(ciag);
	}

	private void ustawKomponenty() {
		JLabel tytul = new JLabel("Szukaj:");
		JScrollPane scroll = new JScrollPane(lista);
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 200));
		
		
		lista.setFont(new Font(lista.getFont().getName(),Font.PLAIN, 10));
		
		pytanie.addKeyListener(this);
		lista.addMouseListener(this);
//		lista.addListSelectionListener(this);
//		lista.addKeyListener(this);
		
		add(tytul, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(dolnyPanel(), BorderLayout.SOUTH);
		
//		setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setBackground(Stale.jasnybrazowy);
	}
	
	private JPanel dolnyPanel()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);

		panel.add(panelSzczegoly(), BorderLayout.CENTER);
		panel.add(panelPytanie(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel panelSzczegoly()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,2));
		panel.setOpaque(false);

		panel.add(new JLabel("Znaleziono:"));
		panel.add(liczbaZnalezionych);
		
		return panel;
	}
	
	private JPanel panelPytanie()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout(2,1));
		panel.setOpaque(false);
		
		panel.add(new JLabel("Szukaj:"), BorderLayout.WEST);
		panel.add(pytanie, BorderLayout.CENTER);
		
		return panel;
	}

	@Override
	public void requestFocus() {
		super.requestFocus();
		pytanie.requestFocus();
	}
	
	@Override
	public boolean requestFocusInWindow() {
		// TODO Auto-generated method stub
//		return super.requestFocusInWindow();
//		pytanie.requestFocus();
		return pytanie.requestFocusInWindow();
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getSource() == pytanie)
		{
			if (lista.getModel().getSize() > 0)
			{
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
					if (lista.getSelectedIndex() < lista.getModel().getSize()-1)
						lista.setSelectedIndex(lista.getSelectedIndex()+1);

				if (arg0.getKeyCode() == KeyEvent.VK_UP)
					if (lista.getSelectedIndex() > 0)
						lista.setSelectedIndex(lista.getSelectedIndex()-1);
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
					if ((lista.getSelectedIndex() > -1) && (lista.getSelectedIndex() < lista.getModel().getSize()))
					{
						int indeks = lista.getSelectedIndex();
						new Okno(new EkranWizytowka(wyszukane.get(indeks)));
					} else
					if (lista.getModel().getSize() == 1)
						new Okno(new EkranWizytowka(wyszukane.get(0)));
			}
			
			String pyt = pytanie.getText();
			if (pyt.equals(stareZapytanie)) return;
			stareZapytanie = pyt;
			pyt = pyt.toUpperCase();
			pyt = Narzedzia.usunPolskieZnaki(pyt);
				
			lista.removeAll();
			DefaultListModel<String> model = new DefaultListModel<String>();
			wyszukane.clear();
			
			
			if (pyt.length() >= 1)
			{			
				String[] pytania = pyt.split(" ");
				Set<Entry<Osoba, String>> spis = spisSlowKluczowych.entrySet();
				String etykieta;
				
				osoba: for (Entry<Osoba, String> e : spis)
				{
					for (String p : pytania)
						if (!e.getValue().contains(p))
							continue osoba;
					
					etykieta = OOsobie.czyjeDziecko(e.getKey());
					if (!etykieta.equals(""))
						etykieta = e.getKey().imieNazwisko() + ", " + etykieta;
					else
						etykieta = e.getKey().imieNazwisko();
					model.addElement(etykieta);
					wyszukane.add(e.getKey());
				}
			}
			liczbaZnalezionych.setText(model.size()+"");
			lista.setModel(model);
		}		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {	}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if ((arg0.getSource() == lista) &&
			(arg0.getClickCount() == 2))
		{
			int indeks = lista.getSelectedIndex();
			new Okno(new EkranWizytowka(wyszukane.get(indeks)));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
