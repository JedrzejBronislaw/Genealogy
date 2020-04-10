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
		if (!(name.length() >= 2))
			return name;
			
		if (sex == Sex.WOMEN)
		{
			if (name.substring(name.length()-1).equals("a"))
			{
				name = name.substring(0, name.length()-1);
				String c = name.substring(name.length()-1);
				if (c.equals("g") || c.equals("i") || c.equals("j") || c.equals("k") || c.equals("l"))
					name = name + 'i'; else
					name = name + 'y';
			}
		}	
		
		if (sex == Sex.MAN)
		{
			if (name.substring(name.length()-1).equals("i"))	name = name + "ego"; else
			if (name.substring(name.length()-1).equals("y"))	name = name.substring(0,name.length()-1) + "ego"; else
			if (name.substring(name.length()-1).equals("o"))	name = name.substring(0,name.length()-1) + "a"; else
			if (name.substring(name.length()-2).equals("ek"))	name = name.substring(0,name.length()-2) + "ka"; else
																name = name + "a";
		}
		
		return name;
	}
	
}
