package okna;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Okno extends JFrame {

	JPanel ekran;

	public Okno() {
		super("");
		ustawKomponenty();
	}
	
	public Okno(JPanel ekran) {
		super("");
		this.ekran = ekran;
		ustawieniaOkna();
		ustawKomponenty();
	}

	public void setEkran(JPanel ekran)
	{
		this.ekran = ekran;
		ustawKomponenty();
	}
	
	private void ustawieniaOkna() {
		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);
		ustawKomponenty();
		
		setVisible(true);
	}

	private void ustawKomponenty() {
		if (ekran != null)
			add(ekran);
	}
}
