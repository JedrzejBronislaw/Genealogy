package windows;

import model.Person;
import model.Tree;
import model.Person.Plec;
import other.Constants;
import other.Diminutives;
import other.PersonDetails;
import tools.Tools;

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

public class SearchScreen extends JPanel implements KeyListener, MouseListener {

	
	JTextField pytanie = new JTextField();
	JList<String> lista = new JList<String>();
	LinkedList<Person> wyszukane = new LinkedList<Person>();
	Tree drzewo;
	Person[] wszyscy;
	LinkedHashMap<Person, String> spisSlowKluczowych = new LinkedHashMap<Person, String>();
	String stareZapytanie = "";
	JLabel liczbaZnalezionych = new JLabel();
	Diminutives zdrobnienia = null;
	public static String sciezkaDoDodatkowychZdrobnien = Tools.sciezkaFolderuZJarem()+"zdrobnienia.ust";

	
	public void setDrzewo(Tree drzewo) {
		this.drzewo = drzewo;
		aktualizujSpisOsob();
	}
	
	public SearchScreen() {
		ustawKomponenty();
	}
	
	public SearchScreen(Tree drzewo) {
		this.drzewo = drzewo;
		aktualizujSpisOsob();
		ustawKomponenty();
	}
	
	public void aktualizujSpisOsob()
	{
		zdrobnienia = new Diminutives(sciezkaDoDodatkowychZdrobnien);
		wszyscy = drzewo.getWszyscy();
		spisSlowKluczowych.clear();
		for (Person o : wszyscy)
			spisSlowKluczowych.put(o, generujCiag(o));
	}

	private String generujCiag(Person o) {
		String ciag = o.imieNazwisko();
		
		if (o.getPlec() == Plec.Kobieta)
			for (int i=0; i<o.liczbaMalzenstw(); i++)
				ciag += " " + o.getMalzonek(i).getNazwisko();

		ciag += " " +o.getPseudonim();
		ciag += " " +Diminutives.dlaImieniaW(o.getImie());
		ciag += " " +zdrobnienia.dlaImienia(o.getImie());
		
		
		ciag = ciag.toUpperCase();		
		return Tools.usunPolskieZnaki(ciag);
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
		setBackground(Constants.jasnybrazowy);
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
						new Window(new CardScreen(wyszukane.get(indeks)));
					} else
					if (lista.getModel().getSize() == 1)
						new Window(new CardScreen(wyszukane.get(0)));
			}
			
			String pyt = pytanie.getText();
			if (pyt.equals(stareZapytanie)) return;
			stareZapytanie = pyt;
			pyt = pyt.toUpperCase();
			pyt = Tools.usunPolskieZnaki(pyt);
				
			lista.removeAll();
			DefaultListModel<String> model = new DefaultListModel<String>();
			wyszukane.clear();
			
			
			if (pyt.length() >= 1)
			{			
				String[] pytania = pyt.split(" ");
				Set<Entry<Person, String>> spis = spisSlowKluczowych.entrySet();
				String etykieta;
				
				osoba: for (Entry<Person, String> e : spis)
				{
					for (String p : pytania)
						if (!e.getValue().contains(p))
							continue osoba;
					
					etykieta = PersonDetails.czyjeDziecko(e.getKey());
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
			new Window(new CardScreen(wyszukane.get(indeks)));
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
