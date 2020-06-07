package treeGraphs.painter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Point {

	@Getter
	private final int x, y;
	
	public Point addVector(int x, int y) {
		return new Point(this.x + x, this.y + y);
		
	}
	
	public boolean equals(int x, int y) {
		return
			this.x == x &&
			this.y == y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) return false;
		Point p = (Point) obj;
		
		return equals(p.x, p.y);
	}

	public static Point middle(Point topLeft, Point bottomRight) {
		int dx = bottomRight.getX() - topLeft.getX();
		int dy = bottomRight.getY() - topLeft.getY();

		int x = topLeft.getX() + dx/2;
		int y = topLeft.getY() + dy/2;
		
		return new Point(x, y);
	}
	
	@Override
	public String toString() {
		return "[x=" + x + "; y=" + y + "]";
	}
}
