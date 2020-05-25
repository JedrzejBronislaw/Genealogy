package nameDisplayers;

import lombok.Setter;
import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;
import treeGraphs.painter.Point;

public abstract class NameDisplayer {
	
	@Setter
	protected Painter painter;
	
	public NameDisplayer() {}
	public NameDisplayer(Painter painter) {
		setPainter(painter);
	}

	abstract public Handle print(Person person, int x, int y);
	public Handle print(Person person, Point point) {
		return print(person, point.getX(), point.getY());
	};

	abstract public int getHeight(Person person);
	abstract public int getWidth(Person person);
}
