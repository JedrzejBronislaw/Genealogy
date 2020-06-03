package treeGraphs.painter.service;

import lombok.Setter;
import model.Person;
import treeGraphs.TreeGraph;
import treeGraphs.TreeGraphParameters;
import treeGraphs.TreeGraphType;
import treeGraphs.painter.PainterServiceType;
import treeGraphs.painter.nameDisplayers.NameDisplayer;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;
import treeGraphs.painter.nameDisplayers.markers.DeadMarker;
import treeGraphs.painter.nameDisplayers.markers.SexMarker;

@Setter
public class PainterServiceBuilder {

	private TreeGraphType graphType;
	private Person person;
	private NameDisplayerType nameDisplayerType;
	private PainterServiceType painterServiceType;
	
	public PainterServiceBuilder setParameters(TreeGraphParameters parameters) {
		graphType = parameters.getGraphType();
		person = parameters.getPerson();
		nameDisplayerType = parameters.getNameDisplayerType();
		painterServiceType = parameters.getPainterType();
		
		return this;
	}
	
	public boolean isReady() {
		return	graphType != null &&
				person != null &&
				nameDisplayerType != null &&
				painterServiceType != null;
	}
	
	public PainterService build() {
		if (!isReady()) return null;
		
		PainterService service = painterServiceType.createPainterService();
		NameDisplayer nameDisplayer = nameDisplayerType.createDisplayer();
		TreeGraph graph = graphType.createGraph();
		
		graph.setNameDisplayer(nameDisplayer);
		graph.setMainPerson(person);
		service.setGraph(graph);

		nameDisplayer.addMarker(new DeadMarker());
		nameDisplayer.addMarker(new SexMarker());
		
		return service;
	}
}
