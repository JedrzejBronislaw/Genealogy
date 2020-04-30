package treeGraphs.painter.fxPainter;

import javafx.scene.Node;
import tools.Injection;
import treeGraphs.painter.Handle;

public class FxHandle extends Handle {

	private Node node;
	private Runnable singleClick = null;
	private Runnable doubleClick = null;

	public FxHandle(Node node) {
		this.node = node;
	}
	
	private void setEvents() {
		node.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) Injection.run(singleClick);
			if (e.getClickCount() == 2) Injection.run(doubleClick);
		});
	}
	
	@Override
	public void setOnMouseSingleClick(Runnable action) {
		singleClick = action;
		setEvents();
	}
	
	@Override
	public void setOnMouseDoubleClick(Runnable action) {
		doubleClick = action;
		setEvents();
	}

}
