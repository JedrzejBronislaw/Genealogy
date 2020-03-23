package komponentyWizualne;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ProstePoleTekstowe extends JPanel {
	
	private JTextArea area = new JTextArea(7,25);
	private JScrollPane scroll = new JScrollPane(area);
	private NazwaPola nazwa;
	
	public ProstePoleTekstowe(String nazwaPola) {
		setLayout(new BorderLayout());
		
		nazwa = new NazwaPola(nazwaPola);
		
		
		area.setText("Prosty przyk³adowy tekst.");
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		
		add(nazwa, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}
	
	public void set(String tekst)
	{
		area.setText(tekst);
	}
}
