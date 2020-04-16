package nameDisplaying;

import model.Person;
import treeGraphs.painter.Point;

public class DateAndNameDisplaying extends Name{

	@Override
	public void print(Person person, int x, int y) {
		painter.drawText(genTekst(person), new Point(x, y));
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
		String dates;
		String outcome;
		
		outcome = person.nameSurname();
		
		if (person.getLifeStatus() != Person.LifeStatus.NO)
		{
			dates = person.getBirthDate().toString();
			if (!dates.isEmpty()) outcome += " (" + person.getBirthDate() + ")";
		}
		else
		{
			dates = person.getBirthDate().toString() + " - " + person.getDeathDate().toString();
			if (!dates.equals(" - ")) outcome += " (" + dates + ")";
		}
		
		return outcome;
	}
}
