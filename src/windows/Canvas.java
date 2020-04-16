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
import treeGraphs.painter.Graphics2DPainter;

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
	
	@Setter
	Graphics2DPainter painter;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D c = (Graphics2D)g;
		
		if (painter != null)
			painter.setGraphics(c);

		c.setColor(Color.BLACK);
		
		if (graf != null) graf.draw();
		if(wymiary != null)
			wymiary.accept(graf.getWidth(), graf.getHeight());
		
		if ((graf.getWidth() != getWidth()) ||
			(graf.getHeight()  != getHeight()))
			setPreferredSize(new Dimension(graf.getWidth(), graf.getHeight()));
		revalidate();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount() >= 2)
		{
			Point p = arg0.getPoint();
			graf.clik(p.x, p.y);
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
