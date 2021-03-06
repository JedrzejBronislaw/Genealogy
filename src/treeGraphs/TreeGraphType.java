package treeGraphs;

import java.lang.reflect.InvocationTargetException;

import treeGraphs.drawingDesTG.DrawingDesTG;

public enum TreeGraphType {
	ancestors(StdAncestorsTreeGraph.class),
	descendants(StdDescendantsTreeGraph.class),
	drawnig(DrawingDesTG.class);
	
	private Class<? extends TreeGraph> graphClass;
	
	private TreeGraphType(Class<? extends TreeGraph> graph) {
		graphClass = graph;
	}
	
	public TreeGraph createGraph() {
		try {
			return (TreeGraph) graphClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException | NoSuchMethodException e) {

			e.printStackTrace();
			return null;
		}
	}

	private boolean ifMatch(Class<? extends TreeGraph> graphClass) {
		return this.graphClass == graphClass;
	}

	public static TreeGraphType get(Class<? extends TreeGraph> graphClass) {
		TreeGraphType[] values = values();
		
		for (TreeGraphType type : values)
			if (type.ifMatch(graphClass)) return type;
		
		return null;
	}
}
