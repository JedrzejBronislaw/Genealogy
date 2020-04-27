package treeGraphs.painter.graphics2DPainter;

import treeGraphs.painter.Point;

public class G2DHandleFactory {

	private ClickMap map;

	public G2DHandleFactory(ClickMap clickMap) {
		this.map = clickMap;
	}
	
	public G2DHandle createHandle(int left, int top, int right, int bottom) {
		return new G2DHandle(map, left, top, right, bottom);
	}
	
	public G2DHandle createHandle(Point topLeft, Point bottomRight) {
		return new G2DHandle(map,
				topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());
	}

	public void clearClickMap() {
		map.clear();
	}
}
