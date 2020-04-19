package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PGLFileTest_SinglePerson extends PGLFileTest {
	
	@Override
	protected String content() {
		return String.join("\n",
				"[1]",
				"imie=Jan",
				"nazwisko=Kowalski",
				"miejur=Poznan",
				"miejsm=Krakow"
							);
	}

	@Test
	public void numberOfPersons() {
		createPGLFile();

		Tree tree = loadTreeFromFile();
		Person[] persons = tree.getAll();
		
		assertEquals(1, persons.length);
	}

	@Test
	public void firstName() {
		createPGLFile();

		Tree tree = loadTreeFromFile();
		Person[] persons = tree.getAll();
		Person person = persons[0];
		
		assertEquals("Jan", person.getFirstName());
	}

	@Test
	public void lastName() {
		createPGLFile();

		Tree tree = loadTreeFromFile();
		Person[] persons = tree.getAll();
		Person person = persons[0];
		
		assertEquals("Kowalski", person.getLastname());
	}

	@Test
	public void birthPlace() {
		createPGLFile();

		Tree tree = loadTreeFromFile();
		Person[] persons = tree.getAll();
		Person person = persons[0];
		
		assertEquals("Poznan", person.getBirthPlace());
	}

	@Test
	public void deathPlace() {
		createPGLFile();

		Tree tree = loadTreeFromFile();
		Person[] persons = tree.getAll();
		Person person = persons[0];
		
		assertEquals("Krakow", person.getDeathPlace());
	}

}
