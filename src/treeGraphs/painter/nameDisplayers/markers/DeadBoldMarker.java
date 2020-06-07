package treeGraphs.painter.nameDisplayers.markers;

import model.Person;

public class DeadBoldMarker extends NameMarker {

	private int oldSize;
	
	@Override
	public boolean check(Person person) {
		return person.isDead();
	}

	@Override
	protected void prepare(Person person) {
		painter.bold();
		oldSize = painter.changeTextSize(20);
	}

	@Override
	protected void clean() {
		painter.unbold();
		painter.setTextSize(oldSize);
	}
}
