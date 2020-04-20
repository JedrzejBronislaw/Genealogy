package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Person.LifeStatus;
import model.Person.Sex;

public class PersonTest_Equals {

	private static Person expectedPerson;
	private Person actualPerson;
	
	@BeforeClass
	public static void prepareExpected() {
		Person person = new Person();
		person.setFirstName("Adam");
		person.setLastName("Kowalski");
		person.setAlias("Daniel");
		person.setLifeStatus(LifeStatus.NO);
		person.setSex(Sex.MAN);
		person.setBirthDate(new MyDate("1.2.1908"));
		person.setDeathDate(new MyDate("3.4.2010"));
		person.setBirthPlace("Poznan");
		person.setDeathPlace("Krakow");
		person.setContact("contact");
		person.setComments("comments");
		person.setBaptismParish("parish");
		person.setBurialPlace("cemetery");
		
		expectedPerson = person;
	}

	@Before
	public void prepareActual() {
		Person person = new Person();
		person.setFirstName("Adam");
		person.setLastName("Kowalski");
		person.setAlias("Daniel");
		person.setLifeStatus(LifeStatus.NO);
		person.setSex(Sex.MAN);
		person.setBirthDate(new MyDate("1.2.1908"));
		person.setDeathDate(new MyDate("3.4.2010"));
		person.setBirthPlace("Poznan");
		person.setDeathPlace("Krakow");
		person.setContact("contact");
		person.setComments("comments");
		person.setBaptismParish("parish");
		person.setBurialPlace("cemetery");

		actualPerson = person;
	}
	
	@Test
	public void notEqualsNull() {
		assertFalse(expectedPerson.equals(null));
	}
	
	@Test
	public void notEqualsObject() {
		assertFalse(expectedPerson.equals(new Object()));
	}
	
	@Test
	public void equals() {
		assertTrue(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void equals_theSame() {
		assertTrue(expectedPerson.equals(expectedPerson));
	}
	
	@Test
	public void notEquals_firstName() {
		actualPerson.setFirstName("Robert");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_lastName() {
		actualPerson.setLastName("Nowak");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_alias() {
		actualPerson.setAlias("Leon");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_lifeStatus() {
		actualPerson.setLifeStatus(LifeStatus.UNDEFINED);
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_sex() {
		actualPerson.setSex(Sex.UNDEFINED);
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_birthDate() {
		actualPerson.setBirthDate(new MyDate("11.12.1910"));
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_deathDate() {
		actualPerson.setDeathDate(new MyDate("21.12.2010"));
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_birthPlace() {
		actualPerson.setBirthPlace("Warsaw");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_deathPlace() {
		actualPerson.setDeathPlace("Poznan");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_contact() {
		actualPerson.setContact("contact 2");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_comments() {
		actualPerson.setComments("comments 2");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_baptismParish() {
		actualPerson.setBaptismParish("parish 2");
		assertFalse(expectedPerson.equals(actualPerson));
	}
	
	@Test
	public void notEquals_burialPlace() {
		actualPerson.setBurialPlace("cemetery 2");
		assertFalse(expectedPerson.equals(actualPerson));
	}
}
