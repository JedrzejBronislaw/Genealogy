package treeGraphs.painter.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import treeGraphs.TreeGraph;
import treeGraphs.TreeGraphParameters;
import treeGraphs.painter.nameDisplayers.NameDisplayer;
import treeGraphs.painter.nameDisplayers.markers.DeadMarker;
import treeGraphs.painter.nameDisplayers.markers.SexMarker;

@AllArgsConstructor
@NoArgsConstructor
public class PainterServiceBuilder {

	private TreeGraphParameters params;
	
	public PainterServiceBuilder setParameters(TreeGraphParameters parameters) {
		this.params = parameters;
		return this;
	}
	
	public boolean isReady() {
		return
			params != null &&
			params.isReady();
	}
	
	public PainterService build() {
		if (!isReady()) return null;
		
		PainterService service      = params.getPainterType().createPainterService();
		NameDisplayer nameDisplayer = params.getNameDisplayerType().createDisplayer();
		TreeGraph graph             = params.getGraphType().createGraph();
		
		graph.setNameDisplayer(nameDisplayer);
		graph.setMainPerson(params.getPerson());
		service.setGraph(graph);

		if (params.isMarkers()) {
			nameDisplayer.addMarker(new DeadMarker());
			nameDisplayer.addMarker(new SexMarker());
		}
		
		return service;
	}
	
	public static PainterService build(TreeGraphParameters parameters) {
		return new PainterServiceBuilder(parameters).build();
	}
}
