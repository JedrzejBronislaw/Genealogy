package nameDisplaying;

import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Point;

public class SimpleNameDisplaying extends Name{

	@Override
	public Handle print(Person person, int x, int y) {
		return painter.drawText(genTekst(person), new Point(x, y));
	}

	@Override
	public int getHeight(Person person) {
		return painter.getTextHeight();
	}

	@Override
	public int getWidth(Person person) {
		return painter.getTextWidth(genTekst(person));
	}
	
	private String genTekst(Person person)
	{
		return person.nameSurname();
	}

}
