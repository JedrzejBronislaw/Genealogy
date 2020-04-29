package treeGraphs.painter.fxPainter;

import javafx.scene.Node;
import tools.Injection;
import treeGraphs.painter.Handle;

public class FxHandle extends Handle {

	private Node node;

	public FxHandle(Node node) {
		this.node = node;
	}
	
	@Override
	public void setOnMouseDoubleClick(Runnable action) {
		node.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) Injection.run(action);
		});
	}

}
