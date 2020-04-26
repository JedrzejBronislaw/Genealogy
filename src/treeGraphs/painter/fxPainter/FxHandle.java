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
	public void setOnMouseClick(Runnable action) {
		node.setOnMouseClicked(e -> Injection.run(action));
	}

}
