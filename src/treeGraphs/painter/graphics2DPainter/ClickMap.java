package treeGraphs.painter.graphics2DPainter;

import java.util.ArrayList;

import lombok.Setter;
import tools.Injection;
import treeGraphs.painter.Point;
import treeGraphs.painter.Rectangle;

public class ClickMap {

	public static class ClickArea {
		private int left, top, right, bottom;

		@Setter
		private Runnable onMouseDoubleClick;

		public ClickArea(int left, int top, int right, int bottom) {
			Point point1 = new Point(left, top);
			Point point2 = new Point(right, bottom);
			
			saveCoordinates(new Rectangle(point1, point2));
		}
		public ClickArea(Point point1, Point point2) {
			saveCoordinates(new Rectangle(point1, point2));
		}
		public ClickArea(Rectangle rectangle) {
			saveCoordinates(rectangle);
		}

		private void saveCoordinates(Rectangle rect) {
			this.left   = rect.getLeft();
			this.right  = rect.getRight();
			this.top    = rect.getTop();
			this.bottom = rect.getBottom();
		}
		
		public void doubleClick() {
			Injection.run(onMouseDoubleClick);
		}
	}
	
	private ArrayList<ClickArea> areas = new ArrayList<ClickArea>();
	
	public int size() {
		return areas.size();
	}
	
	public ClickArea getArea(int index){
		
		if (index < 0 || index >= areas.size())
			throw new IllegalArgumentException("Index out of range");

		return areas.get(index);
	}
	
	public ClickArea addArea(int top, int left, int bottom, int right)
	{
		ClickArea area = new ClickArea(left, top, right, bottom);
		areas.add(area);
		return area;
	}
	
	public ClickArea getArea(int x, int y)
	{
		for (ClickArea o: areas)
			if ((o.left <= x) && (o.right >= x) &&
				(o.top <= y) && (o.bottom >= y))
				return o;
		return null;
	}

	public void clear() {
		areas.clear();
	}
	
	public String areaList()
	{
		StringBuffer sb = new StringBuffer();
		
		for (ClickArea area: areas)
		{
			sb.append(area.left + "," + area.top + " | " + area.right + "," + area.bottom + "\n");
		}
		
		return sb.toString();
	}
}
