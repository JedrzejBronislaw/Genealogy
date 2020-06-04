package treeGraphs.painter.nameDisplayers;

import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;
import treeGraphs.painter.Point;
import treeGraphs.painter.nameDisplayers.markers.MarkerList;
import treeGraphs.painter.nameDisplayers.markers.NameMarker;

public abstract class NameDisplayer {
	
	protected Painter painter;
	
	private MarkerList markerList = new MarkerList(this::printPerson);
	
	public void setPainter(Painter painter) {
		this.painter = painter;
		markerList.setPainter(painter);
	}
	
	public NameDisplayer() {}
	public NameDisplayer(Painter painter) {
		setPainter(painter);
	}

	public Handle print(Person person, int x, int y) {
		return (person == null) ? null : markerList.print(person, x, y);
	}
	public Handle print(Person person, Point point) {
		return print(person, point.getX(), point.getY());
	};

	abstract protected Handle printPerson(Person person, int x, int y);
	abstract public int getHeight(Person person);
	abstract public int getWidth(Person person);
	
	public void addMarker(NameMarker marker) {
		markerList.add(marker);
	}
}
