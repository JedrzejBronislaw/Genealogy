package visualComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class Field extends JPanel {
	FieldName labelNazwa = new FieldName();
	FieldValue labelWartosc = new FieldValue();
	
	public Field() {
		ustawKomponenty();
	}
	public Field(String nazwaPola)
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
class FieldName extends JLabel
{
	public FieldName(String tekst) {
		setText(tekst);
		ustawWlasciwosci();
	}
	public FieldName() {
		ustawWlasciwosci();
	}
	
	public void  ustawWlasciwosci() {
		setForeground(Color.DARK_GRAY);
	}
}
class FieldValue extends JLabel
{
	public FieldValue(String tekst) {
		setText(tekst);
		ustawWlasciwosci();
	}
	public FieldValue() {
		ustawWlasciwosci();
	}
	
	public void  ustawWlasciwosci() {
		Font font = getFont();
		font = new Font(font.getName(), Font.BOLD, font.getSize()+2);
		setFont(font);
		setForeground(Color.BLACK);
	}
}
