package inne;

import dane.Osoba;
import dane.Osoba.Plec;

public class OdmianaSlow {
	
	
	public static String dopelniaczImienia(Osoba o)
	{
		if (o == null) return "";
		return dopelniaczImienia(o.getImie(), o.getPlec());
		
	}
	public static String dopelniaczImienia(String imie, Plec plec)
	{
		if (imie == null)
			return "";
		if (!(imie.length() >= 2))
			return imie;
			
		//Odmiana imienia ¿eñskiego
		if (plec == Plec.Kobieta)
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
		if (plec == Plec.Mezczyna)
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
