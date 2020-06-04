package treeGraphs.painter.nameDisplayers;

import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Point;

public class SimpleNameDisplayer extends NameDisplayer{

	@Override
	protected Handle printPerson(Person person, int x, int y) {
		return painter.drawText(genText(person), new Point(x, y));
	}

	@Override
	protected int height(Person person) {
		return painter.getTextHeight();
	}

	@Override
	protected int width(Person person) {
		return painter.getTextWidth(genText(person));
	}
	
	private String genText(Person person) {
		return person.nameSurname();
	}

}
