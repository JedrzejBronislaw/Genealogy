package treeGraphs.painter.service;

import java.awt.Dimension;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import tools.SwingRefresher;
import treeGraphs.TreeGraph;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;
import treeGraphs.painter.graphics2DPainter.Graphics2DPainter;
import windows.Canvas;

public class Graphics2DPainterService extends PainterService {

	private Canvas canvas;
	private SwingNode swingNode;
	private Graphics2DPainter painter = new Graphics2DPainter();
	
	public Graphics2DPainterService() {
		canvas = new Canvas();
		
		canvas.setPreferredSize(new Dimension(1000, 1000));
		canvas.setPainter(painter);
		
		swingNode = new SwingNode();
		swingNode.setContent(canvas);
	}
	
	@Override
	public void setGraph(TreeGraph graph) {
		super.setGraph(graph);
		canvas.setGrafDrzewa(graph);
	}
	
	@Override
	public Painter getPainter() {
		return painter;
	}

	@Override
	public Node getCanvas(Pane parent) {
		if (parent != null)
			canvas.setWymiary((sz, wys) -> {
				parent.setPrefHeight(wys);
				parent.setPrefWidth(sz);
			});
		
		return swingNode;
	}

	@Override
	public GraphSaver getGraphSaver() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void refreshGraph() {
		SwingRefresher.refreshGraph(swingNode);
	}

}
