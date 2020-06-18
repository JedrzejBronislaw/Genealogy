package model.tools;

import lombok.Getter;
import model.Person;
import model.Sex;

public class ManWoman {

	@Getter private Person man;
	@Getter private Person woman;
	private boolean success;
	
	public boolean success() {
		return success;
	}
	
	public ManWoman(Person person1, Person person2) {
		Person man = null;
		Person woman = null;

		if (person1 == null || person2 == null) {
			success = false;
			return;
		}
		
		Person[] persons = new Person[]{person1, person2};
		
		for(Person person : persons) {
			if (person.getSex() == Sex.MAN) man = person;
			if (person.getSex() == Sex.WOMAN)  woman = person;
		}
		
		success = (man == null || woman == null) ? false : true;
		
		if (success) {
			this.man = man;
			this.woman = woman;
		}
	}
}
