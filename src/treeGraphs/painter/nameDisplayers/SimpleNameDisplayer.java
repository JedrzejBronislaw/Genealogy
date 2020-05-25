package treeGraphs.painter.nameDisplayers;

import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Point;

public class SimpleNameDisplayer extends NameDisplayer{

	@Override
	public Handle print(Person person, int x, int y) {
		return painter.drawText(genText(person), new Point(x, y));
	}

	@Override
	public int getHeight(Person person) {
		return painter.getTextHeight();
	}

	@Override
	public int getWidth(Person person) {
		return painter.getTextWidth(genText(person));
	}
	
	private String genText(Person person)
	{
		return person.nameSurname();
	}

}
