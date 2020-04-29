package treeGraphs.painter.graphics2DPainter;

import treeGraphs.painter.Handle;
import treeGraphs.painter.graphics2DPainter.ClickMap.ClickArea;

public class G2DHandle extends Handle {

	private ClickArea area;
	
	G2DHandle(ClickMap map, int left, int top, int right, int bottom) {
		area = map.addArea(top, left, bottom, right);
	}
	
	@Override
	public void setOnMouseDoubleClick(Runnable action) {
		area.setOnMouseDoubleClick(action);
	}

}
