package treeGraphs.painter.nameDisplayers;

import java.util.LinkedList;
import java.util.List;

import lombok.Setter;
import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;
import treeGraphs.painter.Point;
import treeGraphs.painter.nameDisplayers.markers.NameMarker;

public abstract class NameDisplayer {
	
	@Setter
	protected Painter painter;
	
	protected List<NameMarker> markers = new LinkedList<>();
	
	public NameDisplayer() {}
	public NameDisplayer(Painter painter) {
		setPainter(painter);
	}

	public Handle print(Person person, int x, int y) {
		if  (person == null) return null;
		
		Handle handle = null;
		
		for (NameMarker marker : markers)
			if (marker.check(person)) {
				handle = marker.print(person, x, y);
				break;
			}
		
		if (handle == null)
			handle = printPerson(person, x, y);
		
		return handle;
	}
	public Handle print(Person person, Point point) {
		return print(person, point.getX(), point.getY());
	};

	abstract protected Handle printPerson(Person person, int x, int y);
	abstract public int getHeight(Person person);
	abstract public int getWidth(Person person);
	
	public void addMarker(NameMarker marker) {
		marker.setPainter(painter);
		marker.setDisplayer(this::printPerson);
		markers.add(marker);
	}
}
