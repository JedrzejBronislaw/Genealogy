package nameDisplaying;

import model.Person;

public class DateAndNameDisplaying extends Name{
	
	@Override
	public void print(Person person, int x, int y) {
		g.drawString(genTekst(person), x, y);
	}

	@Override
	public int getHeight(Person person) {
		return fm.getAscent()-fm.getDescent();
	}

	@Override
	public int getWidth(Person person) {
		return fm.stringWidth(genTekst(person));
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
