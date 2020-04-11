package nameDisplaying;

import model.Person;

public class DateUnderNameDisplaying extends Name {

	private static final int verticalSpacing = 3;
	
	@Override
	public void print(Person person, int x, int y) {
		String date = generateDate(person);
		if (date != null)
		{
			g.drawString(generateName(person), x, y-verticalSpacing-(fm.getAscent()-fm.getDescent()));
			g.drawString(date, x, y);
		} else
			g.drawString(generateName(person), x, y);
	}

	@Override
	
	public int getHeight(Person person) {
		return (fm.getAscent()-fm.getDescent()) * (generateDate(person)==null?1:2) + verticalSpacing;
	}

	@Override
	public int getWidth(Person person) {
		String date = generateDate(person);
		return Math.max(fm.stringWidth(generateName(person)), (date==null)?0:fm.stringWidth(generateDate(person)));
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
