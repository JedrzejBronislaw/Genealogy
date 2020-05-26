package treeGraphs.painter.color;

import javafx.scene.layout.Pane;
import treeGraphs.painter.fxPainter.FxPainter;

public class FxPainterTest_Color extends PainterTest_Color{

	public FxPainterTest_Color() {
		painter = new FxPainter(new Pane());
	}

}
