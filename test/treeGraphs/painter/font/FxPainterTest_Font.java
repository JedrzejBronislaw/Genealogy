package treeGraphs.painter.font;

import javafx.scene.layout.Pane;
import treeGraphs.painter.fxPainter.FxPainter;

public class FxPainterTest_Font extends PainterTest_Font{

	public FxPainterTest_Font() {
		painter = new FxPainter(new Pane());
	}

}
