package okna;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.io.FileFilter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import narzedzia.Akcja;
import narzedzia.Ustawienia;

public class OknoWyborPliku extends JFrame implements ActionListener, ListSelectionListener {

	JButton wybierzPlik;
	JLabel sciezka;
	JList ostatnioOtwarte;
	String wybranaSciezka;
	Akcja coPo;
	
	public String getSciezka() {return wybranaSciezka;}
	public void setCoPo(Akcja coPo) {this.coPo = coPo;}
	
	public void getSci(Method m)
	{
		try {
			m.invoke(null, null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public OknoWyborPliku() {
		super("Wybierz plik");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(200,200);
		setLocationRelativeTo(null);
		
		ustawKomponenty();
		
		setVisible(true);
	}

	private void ustawKomponenty() {
		wybierzPlik = new JButton("Wybierz plik");
		sciezka = new JLabel("sciezka");
		ostatnioOtwarte = new JList(Ustawienia.getOstatnioOtwarte());
		
		
		ostatnioOtwarte.addListSelectionListener(this);
		wybierzPlik.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(ostatnioOtwarte);
		add(wybierzPlik);
		add(sciezka);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		
		if (source == wybierzPlik)
		{
			JFileChooser jc = new JFileChooser();
			jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filtr = new FileFilter() {
				
				@Override
				public boolean accept(File f) {
					String nazwa = f.getName();
					if (f.isDirectory()) return true;
					if (nazwa.length() < 3) return false;
					return nazwa.substring(nazwa.length()-3).equals("pgl");
				}

				@Override
				public String getDescription() {
					return "Pliki PGL";
				}
			};
//			jc.setFileFilter(filtr);
			jc.setCurrentDirectory(new File("."));
			
			if (jc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				sciezka.setText(jc.getSelectedFile().getAbsolutePath());
				wybranaSciezka = jc.getSelectedFile().getAbsolutePath();
			} else
			{
				sciezka.setText("niezatwierdzone");
				wybranaSciezka = null;
			}
			if (coPo != null) coPo.start();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (arg0.getSource() == ostatnioOtwarte)
		{
			sciezka.setText((String) ostatnioOtwarte.getSelectedValue());
		}
	}
}
