package treeGraphs.painter.service;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import treeGraphs.TreeGraph;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;

public abstract class PainterService {

	@Getter
	protected TreeGraph graph;

	public void setGraph(TreeGraph graph) {
		this.graph = graph;
		graph.setPainter(getPainter());
	}
	
	public abstract Painter getPainter();
	public abstract Node getCanvas(Pane parent);
	public Node getCanvas() {
		return getCanvas(null);
	}
	public abstract GraphSaver getGraphSaver();
	public abstract void refreshGraph();
	protected abstract String painterName();
}
