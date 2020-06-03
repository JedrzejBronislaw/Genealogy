package treeGraphs.painter.nameDisplayers.markers;

import model.Person;
import model.Person.Sex;
import treeGraphs.painter.MyColor;

public class SexMarker extends NameMarker {

	private MyColor oldColor = MyColor.BLACK;

	@Override
	public boolean check(Person person) {
		return person.getSex() != Sex.UNKNOWN;
	}

	@Override
	protected void prepare(Person person) {
		oldColor = painter.getColor();
		
		switch (person.getSex()) {
			case MAN:
				painter.setColor(new MyColor(0, 0, 153)); break;
			case WOMAN:
				painter.setColor(new MyColor(153, 0, 0)); break;
			default: break;
		}
	}
	
	@Override
	protected void clean() {
		painter.setColor(oldColor);
	}
}
