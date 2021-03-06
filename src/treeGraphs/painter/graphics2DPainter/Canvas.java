package treeGraphs.painter.graphics2DPainter;

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
import treeGraphs.painter.graphics2DPainter.ClickMap.ClickArea;

public class Canvas extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -1257450015364085705L;

	private TreeGraph graph;
	
	public Canvas() {
		addMouseListener(this);
		setBackground(Color.WHITE);
	}
	
	public void setTreeGraph(TreeGraph graph) {this.graph = graph;}
	
	@Setter
	private BiConsumer<Integer, Integer> dimensions;
	
	@Setter
	private Graphics2DPainter painter;
	
	@Setter
	private ClickMap clickMap;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D c = (Graphics2D)g;
		
		if (painter != null)
			painter.setGraphics(c);

		c.setColor(Color.BLACK);
		
		if (graph != null) {
			graph.draw();

			if (dimensions != null)
				dimensions.accept(graph.getWidth(), graph.getHeight());

			if (sizeChanged())
				setPreferredSize(new Dimension(graph.getWidth(), graph.getHeight()));
			
			revalidate();
		}
	}

	private boolean sizeChanged() {
		return
			(graph.getWidth()  != getWidth()) ||
			(graph.getHeight() != getHeight());
	}

	@Override
	public void mouseClicked(MouseEvent click) {
		Point point = click.getPoint();
		ClickArea area = clickMap.getArea(point.x, point.y);
		
		if (area != null) {
			if (click.getClickCount() == 1) area.singleClick();
			if (click.getClickCount() >= 2) area.doubleClick();
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
