package treeGraphs.painter.nameDisplayers;

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

	public Handle print(Person person, int x, int y) {
		return (person == null) ? null : printPerson(person, x, y);
	}
	public Handle print(Person person, Point point) {
		return print(person, point.getX(), point.getY());
	};

	abstract protected Handle printPerson(Person person, int x, int y);
	abstract public int getHeight(Person person);
	abstract public int getWidth(Person person);
}
