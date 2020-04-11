package other;

import model.Person;
import model.Person.Sex;

public class InflectionPL {
	
	
	public static String nameGenitive(Person o)
	{
		if (o == null) return "";
		return nameGenitive(o.getFirstName(), o.getSex());
		
	}
	public static String nameGenitive(String name, Sex sex)
	{
		if (name == null)
			return "";
		if (name.length() < 2)
			return name;
			
		if (sex == Sex.WOMEN)
			name = womanNameGenitive(name);
		
		if (sex == Sex.MAN)
			name = manNameGenitive(name);
		
		return name;
	}
	
	private static String womanNameGenitive(String name) {
		if (name.endsWith("a"))
		{
			name = cutLastChars(name,1);
			if (endsWith(name, "g", "i", "j", "k", "l"))
				name = name + 'i'; else
				name = name + 'y';
		}
		return name;
	}
	
	private static String manNameGenitive(String name) {
		if (name.endsWith("i"))		name = name + "ego"; else
		if (name.endsWith("y"))		name = cutLastChars(name, 1) + "ego"; else
		if (name.endsWith("o"))		name = cutLastChars(name, 1) + "a"; else
		if (name.endsWith("ek"))	name = cutLastChars(name, 2) + "ka"; else
									name = name + "a";
		return name;
	}
	
	private static String cutLastChars(String name, int numOfChars) {
		return name.substring(0,name.length()-numOfChars);
	}
	
	private static boolean endsWith(String name, String... ends) {

		for(int i=0; i<ends.length; i++)
			if(name.endsWith(ends[i]))
				return true;
		
		return false;
	}
	
}
