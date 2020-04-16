package treeGraphs.painter;

import lombok.Getter;
import lombok.NonNull;

public class Rectangle {

	@Getter
	@NonNull
	private Point topLeft, bottomRight;
	
	public Rectangle(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		
		order();
	}

	private void order() {
		int x1 = topLeft.getX();
		int y1 = topLeft.getY();
		int x2 = bottomRight.getX();
		int y2 = bottomRight.getY();
		
		int top    = Math.min(y1, y2);
		int bottom = Math.max(y1, y2);
		int left   = Math.min(x1, x2);
		int right  = Math.max(x1, x2);
		
		topLeft = new Point(left, top);
		bottomRight = new Point(right, bottom);
	}
	
	public int getTop()    {return topLeft.getY();}
	public int getBottom() {return bottomRight.getY();}
	public int getLeft()   {return topLeft.getX();}
	public int getRight()  {return bottomRight.getX();}

	public int width() {
		return getRight()-getLeft();
	}

	public int height() {
		return getBottom()-getTop();
	}
}
