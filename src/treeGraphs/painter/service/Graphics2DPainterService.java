package treeGraphs.painter.service;

import java.awt.Dimension;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import treeGraphs.TreeGraph;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;
import treeGraphs.painter.graphics2DPainter.Canvas;
import treeGraphs.painter.graphics2DPainter.ClickMap;
import treeGraphs.painter.graphics2DPainter.G2DHandleFactory;
import treeGraphs.painter.graphics2DPainter.Graphics2DGraphSaver;
import treeGraphs.painter.graphics2DPainter.Graphics2DPainter;
import utils.SwingRefresher;

public class Graphics2DPainterService extends PainterService {

	private Canvas canvas;
	private ClickMap clickMap = new ClickMap();
	private SwingNode swingNode;
	private Graphics2DPainter painter = new Graphics2DPainter();
	private Graphics2DGraphSaver graphSaver;
	
	public Graphics2DPainterService() {
		canvas = new Canvas();
		
		canvas.setPreferredSize(new Dimension(1000, 1000));
		canvas.setPainter(painter);
		
		swingNode = new SwingNode();
		swingNode.setContent(canvas);
		
		graphSaver = new Graphics2DGraphSaver(canvas);
		
		canvas.setClickMap(clickMap);
		painter.setHandleFactory(new G2DHandleFactory(clickMap));
	}
	
	@Override
	public void setGraph(TreeGraph graph) {
		super.setGraph(graph);
		canvas.setTreeGraph(graph);
	}
	
	@Override
	public Painter getPainter() {
		return painter;
	}

	@Override
	public Node getCanvas(Pane parent) {
		if (parent != null)
			canvas.setDimensions((width, height) -> {
				parent.setPrefHeight(height);
				parent.setPrefWidth(width);
			});
		
		return swingNode;
	}

	@Override
	public GraphSaver getGraphSaver() {
		return graphSaver;
	}


	@Override
	public void refreshGraph() {
		SwingRefresher.refreshGraph(swingNode);
	}

	@Override
	protected String painterName() {
		return "Graphics2D";
	}
}
