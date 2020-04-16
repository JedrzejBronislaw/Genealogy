package nameDisplaying;

import lombok.Setter;
import model.Person;
import treeGraphs.painter.Painter;
import treeGraphs.painter.Point;

public abstract class Name {
	
	@Setter
	protected Painter painter;
	
	public Name() {}
	public Name(Painter painter) {
		setPainter(painter);
	}

	abstract public void print(Person person, int x, int y);
	public void print(Person person, Point point) {
		print(person, point.getX(), point.getY());
	};

	abstract public int getHeight(Person person);
	abstract public int getWidth(Person person);
}
