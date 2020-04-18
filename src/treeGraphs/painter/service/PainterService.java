package treeGraphs.painter.service;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.Person;
import treeGraphs.TreeGraph;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.Painter;

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
		if (graph != null && mainPerson != null) {
			graph.setMainPerson(mainPerson);
			refreshGraph();
		}
	}
	
	public abstract Painter getPainter();
	public abstract Node getCanvas(Pane parent);
	public Node getCanvas() {
		return getCanvas(null);
	}
	public abstract GraphSaver getGraphSaver();
	public abstract void refreshGraph();
}
