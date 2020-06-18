package model.pgl.reader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Person;
import model.Tree;

public class PGLReaderTest_SmallFamily {
	private static PGLFilePreparation preparation = new PGLFilePreparation("smallFamily");
	private static String content = String.join("\n",
			"[1]",
			"imie=Adam",
			"nazwisko=Kowalski",
			"plec=1",
			"malzenstwa=1",
			"malzonek1=2",
			"dzieci=3",
			"dziecko1=3",
			"dziecko2=4",
			"dziecko3=5",
			"[2]",
			"imie=Ewa",
			"nazwisko=Nowak",
			"plec=0",
			"malzenstwa=1",
			"malzonek1=1",
			"dzieci=3",
			"dziecko1=3",
			"dziecko2=4",
			"dziecko3=5",
			"[3]",
			"imie=Jan",
			"nazwisko=Kowalski",
			"ojciec=1",
			"matka=2",
			"[4]",
			"imie=Marta",
			"nazwisko=Kowalska",
			"ojciec=1",
			"matka=2",
			"[5]",
			"imie=Pawel",
			"nazwisko=Kowalski",
			"ojciec=1",
			"matka=2"
			);
	
	private static Tree tree;
	private static Person[] persons;
	
	@BeforeClass
	public static void prepare() {
		preparation.createPGLFile(content);
		tree = preparation.loadTreeFromFile();
		persons = tree.getAll();
	}
	

	
	@Test
	public void numberOfPersons() {
		assertEquals(5, tree.getAll().length);
	}

	@Test
	public void firstNames() {
		String[] expectedFirstNames = {"Adam", "Ewa", "Jan", "Marta", "Pawel"};
		String[] actualFirstNames = new String[persons.length];

		for(int i=0; i<persons.length; i++)
			actualFirstNames[i] = persons[i].getFirstName();

		assertArrayEquals(expectedFirstNames, actualFirstNames);
	}

	@Test
	public void lastNames() {
		String[] expectedLastNames = {"Kowalski", "Nowak", "Kowalski", "Kowalska", "Kowalski"};
		String[] actualLastNames = new String[persons.length];

		for(int i=0; i<persons.length; i++)
			actualLastNames[i] = persons[i].getLastName();

		assertArrayEquals(expectedLastNames, actualLastNames);
	}

	@Test
	public void fathers() {
		Person[] expectedFathers = {null, null, persons[0], persons[0], persons[0]};
		Person[] actualFathers = new Person[persons.length];

		for(int i=0; i<persons.length; i++)
			actualFathers[i] = persons[i].getFather();

		assertArrayEquals(expectedFathers, actualFathers);
	}

	@Test
	public void mothers() {
		Person[] expectedMothers = {null, null, persons[1], persons[1], persons[1]};
		Person[] actualMothers = new Person[persons.length];

		for(int i=0; i<persons.length; i++)
			actualMothers[i] = persons[i].getMother();

		assertArrayEquals(expectedMothers, actualMothers);
	}

	@Test
	public void numberOfChildren() {
		int[] expectedChildren = {3, 3, 0, 0, 0};
		int[] actualChildren = new int[persons.length];

		for(int i=0; i<persons.length; i++)
			actualChildren[i] = persons[i].numberOfChildren();

		assertArrayEquals(expectedChildren, actualChildren);
	}

	@Test
	public void children() {
		Person[][] expectedChildren = {
				new Person[]{persons[2], persons[3], persons[4]},
				new Person[]{persons[2], persons[3], persons[4]},
				new Person[]{},
				new Person[]{},
				new Person[]{}};

		for(int i=0; i<persons.length; i++)
			assertArrayEquals(expectedChildren[i], persons[i].getChildren());
	}

	@Test
	public void numOfMarriages() {
		int[] expectedMarriages = {1, 1, 0, 0, 0};
		int[] actualMarriages = new int[persons.length];

		for(int i=0; i<persons.length; i++)
			actualMarriages[i] = persons[i].numberOfMarriages();

		assertArrayEquals(expectedMarriages, actualMarriages);
	}

	@Test
	public void firstSpouses() {
		Person[] expectedSpouses = {persons[1], persons[0], null, null, null};
		Person[] actualSpouses = new Person[persons.length];

		for(int i=0; i<persons.length; i++)
			actualSpouses[i] = persons[i].getSpouse(0);

		assertArrayEquals(expectedSpouses, actualSpouses);
	}
}
