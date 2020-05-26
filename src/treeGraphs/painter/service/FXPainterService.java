package treeGraphs.painter.service;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;
import treeGraphs.painter.fxPainter.FxGraphSaver;
import treeGraphs.painter.fxPainter.FxPainter;

public class FXPainterService extends PainterService {
	
	private AnchorPane graphPane = new AnchorPane();
	private FxPainter painter = new FxPainter(graphPane);
	private FxGraphSaver graphSaver = new FxGraphSaver(graphPane);
	
	private Pane parent;
	
	@Override
	public Painter getPainter() {
		return painter;
	}

	@Override
	public Node getCanvas(Pane parent) {
		if (parent != null)
			this.parent = parent;
		return graphPane;
	}

	@Override
	public GraphSaver getGraphSaver() {
		return graphSaver;
	}

	@Override
	public void refreshGraph() {
		graphPane.getChildren().clear();
		graph.draw();
		if(parent != null) {
			parent.setPrefHeight(graph.getHeight());
			parent.setPrefWidth(graph.getWidth());
		}
	}

	@Override
	protected String painterName() {
		return "FX";
	}
}
