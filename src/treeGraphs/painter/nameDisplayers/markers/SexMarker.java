package treeGraphs.painter.nameDisplayers.markers;

import model.Person;
import model.Sex;
import treeGraphs.painter.MyColor;

public class SexMarker extends ColorMarker {

	@Override
	public boolean check(Person person) {
		return person.getSex() != Sex.UNKNOWN;
	}

	@Override
	protected MyColor getColor(Person person) {
		switch (person.getSex()) {
			case MAN:
				return new MyColor(0, 0, 153);
			case WOMAN:
				return new MyColor(153, 0, 0);
			default:
				return null;
		}
	}
}
