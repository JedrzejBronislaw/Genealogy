package visualComponents;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import other.Constants;

public class Group extends JPanel {
	

	public Group(int wys, int szer)
	{
		ustawienia(wys, szer);
	}
	public Group()
	{
		ustawienia(0, 1);
	}
	private void ustawienia(int wys, int szer) {
		setLayout(new GridLayout(wys, szer));
		setBackground(Constants.brazowy);
		setBorder(new EmptyBorder(2, 2, 2, 2));
	}
	
}