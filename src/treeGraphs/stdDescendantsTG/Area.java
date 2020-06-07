package treeGraphs.stdDescendantsTG;

import lombok.Getter;
import tools.Injection;
import treeGraphs.painter.Point;

public abstract class Area {
	
	@Getter private Point location = new Point(0, 0);
	@Getter private int height;
	@Getter private int width;

	protected Runnable onChangeWidth;
	protected Runnable onChangeHeight;
	protected Runnable onChangeX;
	protected Runnable onChangeY;
	
	
	public void setOnChangeWidth(Runnable onChangeWidth) {
		this.onChangeWidth = onChangeWidth;
	}
	public void setOnChangeHeight(Runnable onChangeHeight) {
		this.onChangeHeight = onChangeHeight;
	}
	public void setOnChangeX(Runnable onChangeX) {
		this.onChangeX = onChangeX;
	}
	public void setOnChangeY(Runnable onChangeY) {
		this.onChangeY = onChangeY;
	}

	
	public final void move(int x, int y) {
		setLocation(getLocation().addVector(x, y));
		if (x != 0) Injection.run(onChangeX);
		if (y != 0) Injection.run(onChangeY);
	}
	
	final void moveHorizontal(int offset) {
		if (offset == 0) return;
		
		location = location.addVector(offset, 0);
		Injection.run(onChangeX);
	}	
	final void moveVertical(int offset) {
		if (offset == 0) return;
		
		location = location.addVector(0, offset);
		Injection.run(onChangeY);
	}

	final void setLocation(Point location) {
		boolean x = this.location.getX() != location.getX();
		boolean y = this.location.getY() != location.getY();
		
		this.location = location;
		if (x) Injection.run(onChangeX);
		if (y) Injection.run(onChangeY);
	}
	
	final void setX(int x) {
		if(x == getX()) return;
		
		this.location = new Point(x, location.getY());
		Injection.run(onChangeX);
	}
	final void setY(int y) {
		if(y == getY()) return;
		
		this.location = new Point(location.getX(), y);
		Injection.run(onChangeY);
	}
	

	final void setWidth(int width) {
		if (width < 0) return;
		if (width == getWidth()) return;
		
		this.width = width;
		Injection.run(onChangeWidth);
	}
	final void setHeight(int height) {
		if (height < 0) return;
		if (height == getHeight()) return;
		
		this.height = height;
		Injection.run(onChangeHeight);
	}
	

	int getX() {
		return location.getX();
	}
	int getY() {
		return location.getY();
	}
}
