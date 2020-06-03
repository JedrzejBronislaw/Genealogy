package treeGraphs.painter.nameDisplayers.markers;

import model.Person;
import treeGraphs.painter.MyColor;

public class DeadMarker extends ColorMarker {

	@Override
	public boolean check(Person person) {
		return person.isDead();
	}

	@Override
	protected MyColor getColor(Person person) {
		return new MyColor(130, 130, 130);
	}
}
