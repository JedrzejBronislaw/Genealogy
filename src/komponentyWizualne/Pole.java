package komponentyWizualne;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class Pole extends JPanel {
	NazwaPola labelNazwa = new NazwaPola();
	WartoscPola labelWartosc = new WartoscPola();
	
	public Pole() {
		ustawKomponenty();
	}
	public Pole(String nazwaPola)
	{
		labelNazwa.setText(nazwaPola);
		ustawKomponenty();
	}
	
	private void ustawKomponenty()
	{
		add(labelNazwa);
		add(labelWartosc);
	}
	
	public void setNazwa(String wartosc)
	{
		labelNazwa.setText(wartosc);
	}
	
	public void set(String wartosc)
	{
		labelWartosc.setText(wartosc);
	}
}
class NazwaPola extends JLabel
{
	public NazwaPola(String tekst) {
		setText(tekst);
		ustawWlasciwosci();
	}
	public NazwaPola() {
		ustawWlasciwosci();
	}
	
	public void  ustawWlasciwosci() {
		setForeground(Color.DARK_GRAY);
	}
}
class WartoscPola extends JLabel
{
	public WartoscPola(String tekst) {
		setText(tekst);
		ustawWlasciwosci();
	}
	public WartoscPola() {
		ustawWlasciwosci();
	}
	
	public void  ustawWlasciwosci() {
		Font font = getFont();
		font = new Font(font.getName(), Font.BOLD, font.getSize()+2);
		setFont(font);
		setForeground(Color.BLACK);
	}
}
