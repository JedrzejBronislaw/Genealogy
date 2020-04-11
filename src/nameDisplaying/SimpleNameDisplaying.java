package nameDisplaying;

import model.Person;

public class SimpleNameDisplaying extends Name{

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
		return person.nameSurname();
	}

}
