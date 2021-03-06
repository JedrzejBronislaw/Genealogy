package other;

import model.MyDate;
import model.Person;
import model.Sex;

public class PersonDetails {
	
	public static String whoseChild(Person person)
	{
		Person father = person.getFather();
		Person mother = person.getMother();

		if ((father == null || father.getFirstName() == null || father.getFirstName().isEmpty()) 
		&& (mother == null || mother.getFirstName() == null || mother.getFirstName().isEmpty())) return "";
		
		String outcome, fatherName, motherName;
		if (person.getSex() == Sex.WOMAN)
			outcome = "c�rka ";
		else if (person.getSex() == Sex.MAN)
			outcome = "syn ";
		else
			outcome = "dziecko ";
		
		fatherName = InflectionPL.nameGenitive(father);
		motherName = InflectionPL.nameGenitive(mother);
		
		if (fatherName.isEmpty() || motherName.isEmpty())
			outcome += fatherName + motherName;
		else
			outcome += fatherName + " i " + motherName;
			
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
			return age[2] + " lat " + age[1] + " miesi�cy " + age[0] + " dni";
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
	
	public static String LifeDates(Person person)
	{
		String outcome = "";
		MyDate birthDate = person.getBirthDate();
		MyDate deathDate = person.getDeathDate();
		
		if (!person.isDead()) {
			if (!MyDate.isEmpty(birthDate))
				outcome = birthDate.toString();
		} else {
			if (!MyDate.isEmpty(birthDate) ||
				!MyDate.isEmpty(deathDate))
				outcome = birthDate + " - " + deathDate;
		}
		
		return outcome;
	}
}
