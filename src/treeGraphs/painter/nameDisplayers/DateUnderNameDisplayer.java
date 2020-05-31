package treeGraphs.painter.nameDisplayers;

import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.MultiHandle;
import treeGraphs.painter.Point;

public class DateUnderNameDisplayer extends NameDisplayer {

	private static final int verticalSpacing = 3;
	
	@Override
	protected Handle printPerson(Person person, int x, int y) {
		String date = generateDate(person);
		if (date != null)
		{
			Handle h1, h2;
			h1 = painter.drawText(generateName(person), new Point(x, y-verticalSpacing-painter.getTextHeight()));
			h2 = painter.drawText(date, new Point(x, y));
			return new MultiHandle(h1, h2);
		} else
			return painter.drawText(generateName(person), new Point(x, y));
	}


	@Override
	
	public int getHeight(Person person) {
		int height = painter.getTextHeight();
		return height * (generateDate(person)==null ? 1 : 2) + verticalSpacing;
	}

	@Override
	public int getWidth(Person person) {
		String date = generateDate(person);
		int nameWidth = painter.getTextWidth(generateName(person));
		int dateWidth = (date==null) ? 0 : painter.getTextWidth(generateDate(person));
		return Math.max(nameWidth, dateWidth);
	}

	private String generateName(Person person)
	{
		return person.nameSurname();
	}
	private String generateDate(Person person)
	{
		String dates;
		String outcome;
		
		outcome = null;
		
		if (person.getLifeStatus() != Person.LifeStatus.NO)
		{
			dates = person.getBirthDate().toString();
			if (!dates.isEmpty()) outcome = "(" + person.getBirthDate() + ")";
		}
		else
		{
			dates = person.getBirthDate().toString() + " - " + person.getDeathDate().toString();
			if (!dates.equals(" - ")) outcome = "(" + dates + ")";
		}
		
		return outcome;
	}
}
