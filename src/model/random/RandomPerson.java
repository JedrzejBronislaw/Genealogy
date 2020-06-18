package model.random;

import java.util.Random;

import model.Person;
import model.Sex;

public class RandomPerson {

	private Random random = new Random();
	
	String[] femaleNames = new String[] {
		"Zuzanna",
		"Julia",
		"Zofia",
		"Maja",
		"Hanna",
		"Lena",
		"Alicja",
		"Maria",
		"Oliwia",
		"Amelia"
	};

	String[] maleNames = new String[] {
	    "Antoni",
		"Jan",
		"Jakub",
		"Aleksander",
		"Szymon",
		"Franciszek",
		"Filip",
		"Miko³aj",
		"Wojciech",
		"Adam"
	};

	String[] surnames = new String[] {
		"Nowak",
		"Kowalski",
		"Wiœniewski",
		"Wójcik",
		"Kowalczyk",
		"Kamiñski",
		"Lewandowski",
		"Zieliñski",
		"WoŸniak",
		"Szymañski",
		"D¹browski",
		"Koz³owski",
		"Mazur",
		"Jankowski",
		"Kwiatkowski",
		"Wojciechowski",
		"Krawczyk",
		"Kaczmarek",
		"Piotrowski",
		"Grabowski"
	};

	
	public Person generate() {
		return generate(randomSex());
	}
	
	public Person generate(Sex sex) {
		Person person = new Person();
		
		person.setSex(sex);
		
		person.setFirstName(randomFirstName(sex));
		person.setLastName(randomLastName(sex));
		
		return person;
	}


	private String randomFirstName(Sex sex) {
		String[] names;
		names = (sex == Sex.MAN) ? maleNames : femaleNames;
		
		return names[random.nextInt(names.length)];
	}


	private String randomLastName(Sex sex) {
		String surname = surnames[random.nextInt(surnames.length)];
		
		if (sex == Sex.WOMAN && (surname.endsWith("ski") || surname.endsWith("cki")))
			surname = surname.substring(0, surname.length()-1).concat("a");
		
		return surname;
	}


	private Sex randomSex() {
		return (random.nextBoolean()) ? Sex.MAN : Sex.WOMAN;
	}
}
