package treeGraphs.painter.nameDisplayers.markers;

import model.Person;
import treeGraphs.painter.MyColor;

public class DeadMarker extends NameMarker {

	private MyColor oldColor = MyColor.BLACK;
	
	@Override
	public boolean check(Person person) {
		return person.isDead();
	}

	@Override
	protected void prepare(Person person) {
		oldColor = painter.getColor();
		painter.setColor(new MyColor(130, 130, 130));
	}
	
	@Override
	protected void clean() {
		painter.setColor(oldColor);
	}
}
