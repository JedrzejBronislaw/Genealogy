package dane;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
	public enum Porownanie {POZNIEJSZA, WCZESNIESZA, ROWNE, NIEPOROWNYWALNE};
	
	
	private int dzien = 0;
	private int miesiac = 0;
	private int rok = 0;

	final String[] nazwyMiesiecy  = {"", "styczeñ", "luty", "marzec", "kwiecieñ", "maj", "czerwiec",
										 "lipiec", "sierpieñ", "wrzesieñ", "paŸdziernik", "listopad", "grudzieñ"};
	final String[] nazwyMiesiecyD = {"", "stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca",
										 "lipca", "sierpnia", "wrzesnia", "paŸdziernika", "listopada", "grudnia"};
	final int[] liczbaDniWMiesiacu = {31, 31, 29, 31, 30, 31, 30,
										  31, 31, 30, 31, 30, 31};
	
	
	public int getDzien() {
		return dzien;
	}

	public void setDzien(int dzien) {
		if ((dzien >= 0) && (dzien <= liczbaDniWMiesiacu[miesiac]))
			if ((miesiac != 2) || (dzien <= 28) || (czyRokPrzestepny(rok)))
				this.dzien = dzien;
	}

	public int getMiesiac() {
		return miesiac;
	}

	public void setMiesiac(int miesiac) {
		int tempDzien = dzien;
		if ((miesiac >= 0) && (miesiac <= 12))
		{
			this.miesiac = miesiac;
			setDzien(0);
			setDzien(tempDzien);
		}
	}

	public int getRok() {
		return rok;
	}

	public void setRok(int rok) {
		int tempDzien = dzien;
		this.rok = rok;
		setDzien(0);
		setDzien(tempDzien);
	}

	public static Data teraz()
	{
		Date date = new Date();
		int dzien, miesiac, rok;
		SimpleDateFormat formatD = new SimpleDateFormat("d");
		SimpleDateFormat formatM = new SimpleDateFormat("M");
		SimpleDateFormat formatR = new SimpleDateFormat("yyyy");

		try {dzien = Integer.parseInt(formatD.format(date));}
		catch (NumberFormatException e) {dzien = 0;}
		
		try {miesiac = Integer.parseInt(formatM.format(date));}
		catch (NumberFormatException e) {miesiac = 0;}
		
		try {rok = Integer.parseInt(formatR.format(date));}
		catch (NumberFormatException e) {rok = 0;}
		
		return new Data(dzien, miesiac, rok);		
	}
	
	public static boolean czyRokPrzestepny(int rok)
	{
		return (((rok%4 == 0) && (rok%100 != 0)) ||
				(rok%400 == 0));
	}
	
	public Data() {	}
	public Data(int dzien, int miesiac, int rok)
	{
		setDzien(dzien);
		setMiesiac(miesiac);
		setRok(rok);
	}
	public Data(String data)
	{
		String[] d = data.split("[.]");
		
		if (d.length > 0)
			try {setDzien(Integer.parseInt(d[0]));}
			catch (NumberFormatException e) {this.dzien = 0;}
		if (d.length > 1)
			try {setMiesiac(Integer.parseInt(d[1]));}
			catch (NumberFormatException e) {this.miesiac = 0;}
		if (d.length > 2)
			try {setRok(Integer.parseInt(d[2]));}
			catch (NumberFormatException e) {this.rok = 0;}
	}
	
	public Porownanie porownaj(Data data)
	{
		if ((rok != 0) && (data.rok != 0))
		{
			if (data.rok > rok) return Porownanie.POZNIEJSZA;
			if (data.rok < rok) return Porownanie.WCZESNIESZA;
			
			
			if ((miesiac != 0) && (data.miesiac != 0))
			{
				if (data.miesiac > miesiac) return Porownanie.POZNIEJSZA;
				if (data.miesiac < miesiac) return Porownanie.WCZESNIESZA;
				

				if ((dzien != 0) && (data.dzien != 0))
				{
					if (data.dzien > dzien) return Porownanie.POZNIEJSZA;
					if (data.dzien < dzien) return Porownanie.WCZESNIESZA;
					
					return Porownanie.ROWNE;
					
				} return Porownanie.NIEPOROWNYWALNE;
			} return Porownanie.NIEPOROWNYWALNE;
		} return Porownanie.NIEPOROWNYWALNE;
	}
	
	public int[] roznica(Data data)
	{
		int dni = 0, miesiace = 0, lata = 0;
		Porownanie porownanie = this.porownaj(data);
		Data wczesniejsza, pozniejsza;
		
		
		if (porownanie == Porownanie.NIEPOROWNYWALNE) return null;
		if (porownanie == Porownanie.WCZESNIESZA)
		{
			wczesniejsza = data;
			pozniejsza = this;
		} else
		{
			wczesniejsza = this;
			pozniejsza = data;
		}
		
		//juz wiemy, ze pole rok istnieje w obu, bo s¹ porównywalne
		lata = pozniejsza.rok - wczesniejsza.rok;
		
		if ((wczesniejsza.miesiac != 0) && (pozniejsza.miesiac != 0))
		{
			miesiace = pozniejsza.miesiac - wczesniejsza.miesiac;
			
			if ((dzien != 0) && (data.dzien != 0))
			{
				dni = pozniejsza.dzien - wczesniejsza.dzien;
				if (dni < 0)
				{
					miesiace--;
					dni += 30;
				}
			}
			
			if (miesiace < 0)
			{
				lata--;
				miesiace += 12;
			}
		}
		
		return new int[]{dni, miesiace, lata};
	}
	
	@Override
	public String toString() {
		String wynik = "";
		
		if (dzien != 0)
			wynik += " " + dzien;

		if (miesiac != 0)
		{
			if (dzien != 0)
				wynik += " " + nazwyMiesiecyD[miesiac];
			else
				wynik += " " + nazwyMiesiecy[miesiac];
		}
		
		if (rok != 0)
			wynik += " " + rok + " r.";

		if (wynik.isEmpty()) wynik = " ";
		
		return wynik.substring(1);
	}
}
