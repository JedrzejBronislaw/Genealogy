package model.pgl.loader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Person;
import model.Sex;
import model.Tree;
import model.pgl.PGLFields;

public class PGLLoaderTest_SmallFamily {
	
	private static String content = String.join("\n",
			"[1]",
			PGLFields.firstName + "=Adam",
			PGLFields.lastName +  "=Kowalski",
			PGLFields.sex +       "=" + Sex.MAN,
			PGLFields.marriages + "=1",
			PGLFields.spouse(1) + "=2",
			PGLFields.children +  "=3",
			PGLFields.child(1) +  "=3",
			PGLFields.child(2) +  "=4",
			PGLFields.child(3) +  "=5",
			"[2]",
			PGLFields.firstName + "=Ewa",
			PGLFields.lastName +  "=Nowak",
			PGLFields.sex +       "=" + Sex.WOMAN,
			PGLFields.marriages + "=1",
			PGLFields.spouse(1) + "=1",
			PGLFields.children +  "=3",
			PGLFields.child(1) +  "=3",
			PGLFields.child(2) +  "=4",
			PGLFields.child(3) +  "=5",
			"[3]",
			PGLFields.firstName + "=Jan",
			PGLFields.lastName +  "=Kowalski",
			PGLFields.father +    "=1",
			PGLFields.mother +    "=2",
			"[4]",
			PGLFields.firstName + "=Marta",
			PGLFields.lastName +  "=Kowalska",
			PGLFields.father +    "=1",
			PGLFields.mother +    "=2",
			"[5]",
			PGLFields.firstName + "=Pawel",
			PGLFields.lastName +  "=Kowalski",
			PGLFields.father +    "=1",
			PGLFields.mother +    "=2"
			);
	
	private static Tree tree;
	private static Person[] persons;
	
	
	@BeforeClass
	public static void prepare() {
		tree = new TempFile(content).loadTree();
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

	@Test
	public void sex() {
		Sex[] expectedSexes = {Sex.MAN, Sex.WOMAN, Sex.UNKNOWN, Sex.UNKNOWN, Sex.UNKNOWN};
		Sex[] actualSexes = new Sex[persons.length];

		for(int i=0; i<persons.length; i++)
			actualSexes[i] = persons[i].getSex();

		assertArrayEquals(expectedSexes, actualSexes);
	}
}
