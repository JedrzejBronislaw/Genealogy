package model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PGLFileTest_SinglePerson {
	private static PGLFilePreparation preparation = new PGLFilePreparation("singlePerson");
	private static String content = String.join("\n",
			"[1]",
			"imie=Jan",
			"nazwisko=Kowalski",
			"miejur=Poznan",
			"miejsm=Krakow"
			);
	
	private static Tree tree;
	private static Person person;
	
	@BeforeClass
	public static void prepare() {
		preparation.createPGLFile(content);
		tree = preparation.loadTreeFromFile();
		person = tree.getAll()[0];
	}
	

	
	@Test
	public void numberOfPersons() {
		assertEquals(1, tree.getAll().length);
	}

	@Test
	public void firstName() {
		assertEquals("Jan", person.getFirstName());
	}

	@Test
	public void lastName() {
		assertEquals("Kowalski", person.getLastName());
	}

	@Test
	public void birthPlace() {
		assertEquals("Poznan", person.getBirthPlace());
	}

	@Test
	public void deathPlace() {
		assertEquals("Krakow", person.getDeathPlace());
	}

}
