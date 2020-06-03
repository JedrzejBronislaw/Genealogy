package treeGraphs.painter.nameDisplayers.markers;

import model.Person;
import treeGraphs.painter.MyColor;

abstract public class ColorMarker extends NameMarker {

	private MyColor oldColor = MyColor.BLACK;

	@Override
	protected final void prepare(Person person) {
		oldColor = painter.changeColor(getColor(person));
	}
	
	@Override
	protected final void clean() {
		painter.setColor(oldColor);
	}
	
	abstract protected MyColor getColor(Person person);
}
