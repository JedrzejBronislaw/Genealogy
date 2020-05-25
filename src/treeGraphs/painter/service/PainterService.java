package treeGraphs.painter.service;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.Person;
import treeGraphs.TreeGraph;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;

public abstract class PainterService {

	protected TreeGraph graph;
	protected Person mainPerson;

	public void setGraph(TreeGraph graph) {
		this.graph = graph;
		graph.setPainter(getPainter());
		linkGraphWithPerson();
	}
	
	public void setMainPerson(Person mainPerson) {
		this.mainPerson = mainPerson;
		linkGraphWithPerson();
	}
	
	private void linkGraphWithPerson() {
		if (graph != null && mainPerson != null)
			graph.setMainPerson(mainPerson);
	}
	
	public TreeGraphParameters getParameters() {
		return TreeGraphParameters.builder()
				.person(mainPerson)
				.graphType(TreeGraphType.get(graph.getClass()))
				.nameDisplayerType(NameDisplayerType.get(graph.getNameDisplayer().getClass()))
				.painterType(painterName())
				.build();
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
