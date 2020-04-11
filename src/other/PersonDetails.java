package other;

import model.MyDate;
import model.Person;
import model.Person.Sex;

public class PersonDetails {
	
	public static String whoseChild(Person person)
	{
		if ((person.getFather() == null) && (person.getMother() == null)) return "";
		
		String outcome;
		if (person.getSex() == Sex.WOMEN)
			outcome = "córka ";
		else if (person.getSex() == Sex.MAN)
			outcome = "syn ";
		else
			outcome = "dziecko ";
		
		outcome += InflectionPL.nameGenitive(person.getFather());
		if ((person.getFather() != null) && (person.getMother() != null))
			outcome += " i ";
		outcome += InflectionPL.nameGenitive(person.getMother());
		
			
		return outcome;
	}

	public static int[] age(Person person) {		
		return MyDate.now().difference(person.getBirthDate());
	}
	
	public static String wiekStr(Person person) {
		int[] age = age(person);
		
		if (age == null)
			return "";
		else
			return age[2] + " lat " + age[1] + " miesiêcy " + age[0] + " dni";
	}
	
//	 childless person -> 1
//	 person with child, but without grandchildren -> number of children
	public static int descendantsBranchesWidth(Person person){
		
		if (person.numberOfChildren() > 0)
		{
			int outcome = 0;
			for (int i=0; i<person.numberOfChildren(); i++)
				outcome += PersonDetails.descendantsBranchesWidth(person.getChild(i));
			return outcome;
		} else
			return 1;
	}
	
}
