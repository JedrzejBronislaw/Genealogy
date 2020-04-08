package other;

import model.Person;
import model.Person.Sex;

public class InflectionPL {
	
	
	public static String dopelniaczImienia(Person o)
	{
		if (o == null) return "";
		return dopelniaczImienia(o.getFirstName(), o.getSex());
		
	}
	public static String dopelniaczImienia(String imie, Sex plec)
	{
		if (imie == null)
			return "";
		if (!(imie.length() >= 2))
			return imie;
			
		//Odmiana imienia ¿eñskiego
		if (plec == Sex.WOMEN)
		{
			if (imie.substring(imie.length()-1).equals("a"))
			{
				imie = imie.substring(0, imie.length()-1);//Copy(matka_,1,length(matka_)-1);
				String c = imie.substring(imie.length()-1);
				if (c.equals("g") || c.equals("i") || c.equals("j") || c.equals("k") || c.equals("l"))
					imie = imie + 'i'; else
					imie = imie + 'y';
			}
		}	
		
		//Odmiana imienia mêskiego
		if (plec == Sex.MAN)
		{
			if (imie.substring(imie.length()-1).equals("i"))	imie = imie + "ego"; else
			if (imie.substring(imie.length()-1).equals("y"))	imie = imie.substring(0,imie.length()-1) + "ego"; else
			if (imie.substring(imie.length()-1).equals("o"))	imie = imie.substring(0,imie.length()-1) + "a"; else
			if (imie.substring(imie.length()-2).equals("ek"))	imie = imie.substring(0,imie.length()-2) + "ka"; else
																imie = imie + "a";
		}
		
		return imie;
	}
	
}
