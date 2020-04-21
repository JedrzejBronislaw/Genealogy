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
			
		if (sex == Sex.WOMAN)
			name = womanNameGenitive(name);
		
		if (sex == Sex.MAN)
			name = manNameGenitive(name);
		
		return name;
	}
	
	private static String womanNameGenitive(String name) {
		if (name.endsWith("a"))
		{
			name = cutLastChars(name,1);
			if (endsWith(name, "g", "k", "l", "ri", "gi"))	name = name + 'i'; else
			if (endsWith(name, "i", "j"))				name = cutLastChars(name,1) + 'i'; else
														name = name + 'y';
		}
		return name;
	}
	
	private static String manNameGenitive(String name) {
		if (name.endsWith("i"))		name = name + "ego"; else
		if (name.endsWith("y"))		name = cutLastChars(name, 1) + "ego"; else
		if (name.endsWith("o"))		name = cutLastChars(name, 1) + "a"; else
			
		if (endsWith(name, "k", "³", "c") && !endsWith(name, "ck")) {
			String letter = lastLetter(name);
			name = hardenEnd(cutLastChars(name, 2)) + letter + "a";
		} else
									name = softenEnd(name) + "a";
		
		return name;
	}
	
	private static String hardenEnd(String name) {
		if(name.endsWith("si")) return cutLastChars(name, 2) + "œ";
		if(name.endsWith("ci")) return cutLastChars(name, 2) + "æ";
		if(name.endsWith("ni")) return cutLastChars(name, 2) + "ñ";
		
		return name;
	}
	
	private static String softenEnd(String name) {
		if(name.endsWith("œ")) return cutLastChars(name, 1) + "si";
		if(name.endsWith("æ")) return cutLastChars(name, 1) + "ci";
		if(name.endsWith("ñ")) return cutLastChars(name, 1) + "ni";
		
		return name;
	}
	
	private static String lastLetter(String name) {
		return name.substring(name.length()-1);
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
