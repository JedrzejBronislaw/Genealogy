package treeGraphs.painter.service;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import treeGraphs.TreeGraph;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphParameters.TreeGraphParametersBuilder;
import treeGraphs.TreeGraphType;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;
import treeGraphs.painter.PainterServiceType;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;

public abstract class PainterService {

	@Getter
	protected TreeGraph graph;

	public void setGraph(TreeGraph graph) {
		this.graph = graph;
		graph.setPainter(getPainter());
	}
	
	public TreeGraphParameters getParameters() {
		TreeGraphParametersBuilder builder = TreeGraphParameters.builder()
				.painterType(PainterServiceType.valueOf(painterName()));
		
		if (graph != null) builder
			.person(graph.getMainPerson())
			.graphType(TreeGraphType.get(graph.getClass()))
			.nameDisplayerType(NameDisplayerType.get(graph.getNameDisplayer().getClass()));

		return builder.build();
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
