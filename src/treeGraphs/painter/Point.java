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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) return false;
		Point p = (Point) obj;
		
		return (p.x == x && p.y == y);
	}
}
