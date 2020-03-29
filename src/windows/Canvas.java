package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

import javax.swing.JPanel;

import lombok.Setter;
import treeGraphs.TreeGraph;

public class Canvas extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -1257450015364085705L;
	TreeGraph graf;
	
	public Canvas() {
		addMouseListener(this);
		setBackground(Color.WHITE);
	}
	
	public void setGrafDrzewa(TreeGraph graf) {this.graf = graf;}
	
	@Setter
	private BiConsumer<Integer, Integer> wymiary;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
//		Color[] k = new Color[]{Color.black, Color.blue, Color.red, Color.CYAN};
		Graphics2D c = (Graphics2D)g;
		

		c.setColor(Color.BLACK);
//		c.drawString(getWidth() + "x" +getHeight(), 100, 100);

//		c.setColor(k[new Random().nextInt(k.length)]);
//		c.fillRect(100, 100, 100, 100);
		
//		setSize(getWidth(), getHeight()*2);
		
		if (graf != null) graf.rysuj(c);
		if(wymiary != null)
			wymiary.accept(graf.getSzerokosc(), graf.getWysokosc());
		
		if ((graf.getSzerokosc() != getWidth()) ||
			(graf.getWysokosc()  != getHeight()))
			setPreferredSize(new Dimension(graf.getSzerokosc(), graf.getWysokosc()));
		revalidate();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount() >= 2)
		{
			Point p = arg0.getPoint();
			graf.klik(p.x, p.y);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
