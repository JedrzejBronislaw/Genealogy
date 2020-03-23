package komponentyWizualne;

import inne.Stale;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Grupa extends JPanel {
	

	public Grupa(int wys, int szer)
	{
		ustawienia(wys, szer);
	}
	public Grupa()
	{
		ustawienia(0, 1);
	}
	private void ustawienia(int wys, int szer) {
		setLayout(new GridLayout(wys, szer));
		setBackground(Stale.brazowy);
		setBorder(new EmptyBorder(2, 2, 2, 2));
	}
	
}