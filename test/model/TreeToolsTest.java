package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Person.LifeStatus;
import model.Person.Sex;
import model.familyRelations.Editor;
import model.random.RandomPerson;

public class TreeToolsTest {

	private Tree tree;
	private TreeTools tools;
	private Person mother, father, child1, child2;
	
	private Person prepareFullFieldPerson() {
		Person person = new Person();
		person.setFirstName("Jan");
		person.setLastName("Kowalski");
		person.setBirthDate(new MyDate(2, 4, 1930));
		person.setBirthPlace("Poznan");
		person.setDeathDate(new MyDate(5, 7, 2000));
		person.setDeathPlace("Krakow");
		person.setLifeStatus(LifeStatus.NO);
		person.setSex(Sex.MAN);
		person.setAlias("Adam");
		person.setBaptismParish("sw. Jana z Murami");
		person.setBurialPlace("Poznan Junikowo");
		person.setContact("Poznan ul. Poznanska 1");
		person.setComments("professor of physics");
		
		return person;
	}
	
	@Before
	public void prepare() {
		tree = new Tree();
		tools = new TreeTools(tree);
		RandomPerson rPerson = new RandomPerson();
		Editor relationsEditor = new Editor();
		
		mother = rPerson.generate(Sex.WOMAN);
		father = rPerson.generate(Sex.MAN);
		child1 = rPerson.generate();
		child2 = rPerson.generate();
		
		tree.addPerson("1",  father);
		tree.addPerson("2",  mother);
		tree.addPerson("11", child1);
		tree.addPerson("12", child2);
		
		relationsEditor.createMarriageRel(mother, father);
		relationsEditor.setParentsChildRel(father, mother, child1);
		relationsEditor.setParentsChildRel(father, mother, child2);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testTreeTools_nullTree() {
		new TreeTools(null);
	}
	
	//-----personToString
	
	@Test
	public void testPersonToString() {
		assertEquals("1",  tools.personToString(father));
		assertEquals("2",  tools.personToString(mother));
		assertEquals("11", tools.personToString(child1));
		assertEquals("12", tools.personToString(child2));
	}
	
	@Test
	public void testPersonToString_null() {
		assertNull(tools.personToString(null));
	}
	
	@Test
	public void testPersonToString_wrongPerson() {
		Person person = new Person();
		person.setFirstName("fn");
		person.setLastName("ln");
		assertNull(tools.personToString(person));
	}
	
	@Test
	public void testPersonToString_copyPerson() {
		Person person = tools.copyPerson(father);
		assertNull(tools.personToString(person));
	}
	
	//-----stringToPerson

	@Test
	public void testStringToPerson() {
		assertTrue(father == tools.stringToPerson("1"));
		assertTrue(mother == tools.stringToPerson("2"));
		assertTrue(child1 == tools.stringToPerson("11"));
		assertTrue(child2 == tools.stringToPerson("12"));
	}
	
	@Test
	public void testStringToPerson_null() {
		assertNull(tools.stringToPerson(null));
	}
	
	@Test
	public void testStringToPerson_emptyString() {
		assertNull(tools.stringToPerson(""));
	}
	
	@Test
	public void testStringToPerson_wrongString() {
		assertNull(tools.stringToPerson("3"));
	}
	
	@Test
	public void testStringToPerson_stringWithSpaceA() {
		assertNull(tools.stringToPerson("1 "));
	}
	
	@Test
	public void testStringToPerson_stringWithSpaceB() {
		assertNull(tools.stringToPerson(" 1"));
	}
	
	@Test
	public void testStringToPerson_twoStrings() {
		assertNull(tools.stringToPerson("1" + TreeTools.LINESEPARATOR + "2"));
	}

	//-----personsToString
	
	@Test
	public void testPersonsToString() {
		List<Person> list = Arrays.asList(mother, child1);
		
		String stringPersons = tools.personsToString(list);
		String expectedA = "2" + TreeTools.LINESEPARATOR + "11";
		String expectedB = "11" + TreeTools.LINESEPARATOR + "2";
		
		assertTrue(stringPersons.equals(expectedA)
				|| stringPersons.equals(expectedB));
	}

	
	@Test
	public void testPersonsToString_oneWrongPerson() {
		List<Person> list = Arrays.asList(mother, child1, prepareFullFieldPerson());

		assertNull(tools.personsToString(list));
	}
	
	@Test
	public void testPersonsToString_null() {
		assertNull(tools.personsToString(null));
	}
	
	@Test
	public void testPersonsToString_empty() {
		assertEquals("", tools.personsToString(new ArrayList<>()));
	}
	
	//-----two-way
	
	@Test
	public void testPersonsToString_stringToPersons() {
		List<Person> expectedList = Arrays.asList(tree.getAll());

		String personsString = tools.personsToString(expectedList);
		List<Person> actualList = tools.stringToPersons(personsString);
		
		assertEquals(expectedList, actualList);
	}
	
	//-----stringToPersons

	@Test
	public void testStringToPersons() {
		String stringPersons = String.join(
				TreeTools.LINESEPARATOR,
				"11", "2", "1", "12");
		
		List<Person> list = tools.stringToPersons(stringPersons);
		List<Person> expectedList = Arrays.asList(child1, mother, father, child2);

		assertEquals(expectedList, list);
	}

	@Test
	public void testStringToPersons_wrongOneString() {
		String stringPersons = String.join(
				TreeTools.LINESEPARATOR,
				"11", "2", "3", "12");
		
		List<Person> list = tools.stringToPersons(stringPersons);
		
		assertNull(list);
	}

	@Test
	public void testStringToPersons_null() {
		List<Person> list = tools.stringToPersons(null);

		assertNull(list);
	}

	@Test
	public void testStringToPersons_emptyString() {
		List<Person> list = tools.stringToPersons("");

		assertEquals(0, list.size());
	}

	@Test
	public void testStringToPersons_emptyLine() {
		String stringPersons = String.join(
				TreeTools.LINESEPARATOR,
				"11", "2", "", "1", "12", "");
		
		List<Person> list = tools.stringToPersons(stringPersons);
		List<Person> expectedList = Arrays.asList(child1, mother, father, child2);

		assertEquals(expectedList, list);
	}
	
	//-----COPY PERSON-----

	@Test
	public void testCopyPerson_equalSign() {
		Person original = prepareFullFieldPerson();
		Person copy = tools.copyPerson(original);
		
		assertFalse(original == copy);
	}
	
	@Test
	public void testCopyPerson_equals() {
		Person original = prepareFullFieldPerson();
		Person copy = tools.copyPerson(original);
		
		assertEquals(original, copy);
	}
	
	@Test
	public void testCopyPerson_null() {
		assertNull(tools.copyPerson(null));
	}
	
	@Test
	public void testCopyPerson_empty() {
		assertEquals(new Person(), tools.copyPerson(new Person()));
	}
}
