package treeGraphs.painter.nameDisplayers.markers;

import model.Person;
import treeGraphs.painter.Handle;

public interface Displayer {
	public Handle display(Person person, int x, int y);
}
