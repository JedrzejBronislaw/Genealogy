package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;

public class MyDate {
	public enum Comparison {LATER, EARLIER, EQUAL, INCOMPARABLE};
	
	
	@Getter private int day = 0;
	@Getter private int month = 0;
	@Getter private int year = 0;

	final String[] monthNames  = {"", "styczeñ", "luty", "marzec", "kwiecieñ", "maj", "czerwiec",
										 "lipiec", "sierpieñ", "wrzesieñ", "paŸdziernik", "listopad", "grudzieñ"};
	final String[] monthNamesGenitive = {"", "stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca",
										 "lipca", "sierpnia", "wrzesnia", "paŸdziernika", "listopada", "grudnia"};
	final int[] monthSize = {31, 31, 29, 31, 30, 31, 30,
										  31, 31, 30, 31, 30, 31};
	

	public void setDay(int day) {
		if ((day >= 0) && (day <= monthSize[month]))
			if ((month != 2) || (day <= 28) || (isLeapYear(year)))
				this.day = day;
	}

	public void setMonth(int month) {
		int tempDay = day;
		if ((month >= 0) && (month <= 12))
		{
			this.month = month;
			setDay(0);
			setDay(tempDay);
		}
	}

	public void setYear(int year) {
		int tempDay = day;
		this.year = year;
		setDay(0);
		setDay(tempDay);
	}

	public static MyDate now()
	{
		Date date = new Date();
		int day, month, year;
		SimpleDateFormat formatD = new SimpleDateFormat("d");
		SimpleDateFormat formatM = new SimpleDateFormat("M");
		SimpleDateFormat formatY = new SimpleDateFormat("yyyy");

		try {day = Integer.parseInt(formatD.format(date));}
		catch (NumberFormatException e) {day = 0;}
		
		try {month = Integer.parseInt(formatM.format(date));}
		catch (NumberFormatException e) {month = 0;}
		
		try {year = Integer.parseInt(formatY.format(date));}
		catch (NumberFormatException e) {year = 0;}
		
		return new MyDate(day, month, year);		
	}
	
	public static boolean isLeapYear(int year)
	{
		return (((year%4 == 0) && (year%100 != 0)) ||
				(year%400 == 0));
	}
	
	public MyDate() {	}
	public MyDate(int day, int month, int year)
	{
		setDay(day);
		setMonth(month);
		setYear(year);
	}
	public MyDate(String date)
	{
		String[] d = date.split("[.]");
		
		if (d.length > 0)
			try {setDay(Integer.parseInt(d[0]));}
			catch (NumberFormatException e) {this.day = 0;}
		if (d.length > 1)
			try {setMonth(Integer.parseInt(d[1]));}
			catch (NumberFormatException e) {this.month = 0;}
		if (d.length > 2)
			try {setYear(Integer.parseInt(d[2]));}
			catch (NumberFormatException e) {this.year = 0;}
	}
	
	public Comparison compare(MyDate date)
	{
		if ((year != 0) && (date.year != 0))
		{
			if (date.year > year) return Comparison.LATER;
			if (date.year < year) return Comparison.EARLIER;
			
			
			if ((month != 0) && (date.month != 0))
			{
				if (date.month > month) return Comparison.LATER;
				if (date.month < month) return Comparison.EARLIER;
				

				if ((day != 0) && (date.day != 0))
				{
					if (date.day > day) return Comparison.LATER;
					if (date.day < day) return Comparison.EARLIER;
					
					return Comparison.EQUAL;
					
				} return Comparison.INCOMPARABLE;
			} return Comparison.INCOMPARABLE;
		} return Comparison.INCOMPARABLE;
	}
	
	public int[] difference(MyDate date)
	{
		int days = 0, months = 0, years = 0;
		Comparison comparasion = this.compare(date);
		MyDate earlier, later;
		
		
		if (comparasion == Comparison.INCOMPARABLE) return null;
		if (comparasion == Comparison.EARLIER)
		{
			earlier = date;
			later = this;
		} else
		{
			earlier = this;
			later = date;
		}
		
		//it is known that years are set because dates are not incomparable
		years = later.year - earlier.year;
		
		if ((earlier.month != 0) && (later.month != 0))
		{
			months = later.month - earlier.month;
			
			if ((day != 0) && (date.day != 0))
			{
				days = later.day - earlier.day;
				if (days < 0)
				{
					months--;
					days += 30;
				}
			}
			
			if (months < 0)
			{
				years--;
				months += 12;
			}
		}
		
		return new int[]{days, months, years};
	}
	
	public String toString_() {
		String outcome = "";
		
		if (day != 0)
			outcome += " " + day;

		if (month != 0)
		{
			if (day != 0)
				outcome += " " + monthNamesGenitive[month];
			else
				outcome += " " + monthNames[month];
		}
		
		if (year != 0)
			outcome += " " + year + " r.";

		if (outcome.isEmpty()) outcome = " ";
		
		return outcome.substring(1);
	}
	
	public MyDate copy() {
		return new MyDate(getDay(), getMonth(), getYear());
	}
	
	@Override
	public String toString() {
		String outcome = "";
		
		if (day != 0)   outcome += day;
		outcome += ".";
		if (month != 0) outcome += month;
		outcome += ".";
		if (year != 0)  outcome += year;

		return outcome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof MyDate)) return false;

		MyDate date = (MyDate) obj;

		if (day != date.getDay()) return false;
		if (month != date.getMonth()) return false;
		if (year != date.getYear()) return false;

		return true;
	}
}
