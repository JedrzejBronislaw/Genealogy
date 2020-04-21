package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Person.Sex;

public class Tree_Equals {

	private static final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
	
	private static Tree expectedTree;
	private Tree actualTree;
	
	@BeforeClass
	public static void prepareExpected() {
		Tree tree = new Tree();
		try {
			tree.setLastOpen(format.parse("12:14:05 14.05.2019"));
			tree.setLastModification(format.parse("20:15:15 19.01.2020"));
		} catch (ParseException e) {
			fail("Parsing date error");
		}

		tree.addCommonSurname("Kowalski");
		tree.addCommonSurname("Nowak");
		
		Person father = new Person();
		Person mother = new Person();
		Person child = new Person();

		father.setFirstName("Adam");
		father.setLastName("Kowalski");
		father.setSex(Sex.MAN);
		father.addMarriages(mother);
		father.addChild(child);

		mother.setFirstName("Ewa");
		mother.setLastName("Nowak");
		mother.setSex(Sex.WOMAN);
		mother.addMarriages(father);
		mother.addChild(child);

		child.setFirstName("Robert");
		child.setLastName("Kowalski");
		child.setSex(Sex.MAN);
		child.setFather(father);
		child.setMother(mother);

		
		tree.addPerson("1", father);
		tree.addPerson("2", mother);
		tree.addPerson("3", child);

		
		Person husband1 = new Person();
		Person wife1 = new Person();
		Person husband2 = new Person();
		Person wife2 = new Person();
		
		husband1.setFirstName("Piotr");
		husband1.setLastName("Malinowski");
		husband1.setSex(Sex.MAN);
		
		wife1.setFirstName("Anna");
		wife1.setLastName("Kwiatkowska");
		wife1.setSex(Sex.WOMAN);
		
		husband1.addMarriages(wife1);
		wife1.addMarriages(husband1);
		husband1.addWeddingVenue(wife1, "Poznan");
		
		husband2.setFirstName("Pawel");
		husband2.setLastName("Iksinski");
		husband2.setSex(Sex.MAN);
		
		wife2.setFirstName("Julia");
		wife2.setLastName("Pawlak");
		wife2.setSex(Sex.WOMAN);
		
		husband2.addMarriages(wife2);
		wife2.addMarriages(husband2);
		husband2.addWeddingDate(wife2, "1999.01.01");


		tree.addPerson("11", husband1);
		tree.addPerson("12", wife1);
		tree.addPerson("13", husband2);
		tree.addPerson("14", wife2);

		
		Person mother1 = new Person();
		Person child1 = new Person();
		Person mother2 = new Person();
		Person child2 = new Person();
		
		mother1.setFirstName("Aleksandra");
		mother1.setLastName("Kowal");
		mother1.setSex(Sex.WOMAN);
		
		mother2.setFirstName("Kamila");
		mother2.setLastName("Bartkowiak");
		mother2.setSex(Sex.WOMAN);
		
		child1.setFirstName("Filip");
		child1.setLastName("Kowal");
		
		child2.setFirstName("Urszula");
		child2.setLastName("Bartkowiak");

		mother1.addChild(child1);
		child1.setMother(mother1);
		mother2.addChild(child2);
		child2.setMother(mother2);


		tree.addPerson("21", mother1);
		tree.addPerson("22", child1);
		tree.addPerson("23", mother2);
		tree.addPerson("24", child2);
		
		expectedTree = tree;
	}

	@Before
	public void prepareActual() {
		Tree tree = new Tree();
		try {
			tree.setLastOpen(format.parse("12:14:05 14.05.2019"));
			tree.setLastModification(format.parse("20:15:15 19.01.2020"));
		} catch (ParseException e) {
			fail("Parsing date error");
		}

		tree.addCommonSurname("Kowalski");
		tree.addCommonSurname("Nowak");
		
		Person father = new Person();
		Person mother = new Person();
		Person child = new Person();

		father.setFirstName("Adam");
		father.setLastName("Kowalski");
		father.setSex(Sex.MAN);
		father.addMarriages(mother);
		father.addChild(child);

		mother.setFirstName("Ewa");
		mother.setLastName("Nowak");
		mother.setSex(Sex.WOMAN);
		mother.addMarriages(father);
		mother.addChild(child);

		child.setFirstName("Robert");
		child.setLastName("Kowalski");
		child.setSex(Sex.MAN);
		child.setFather(father);
		child.setMother(mother);

		
		tree.addPerson("1", father);
		tree.addPerson("2", mother);
		tree.addPerson("3", child);

		
		
		Person husband1 = new Person();
		Person wife1 = new Person();
		Person husband2 = new Person();
		Person wife2 = new Person();
		
		husband1.setFirstName("Piotr");
		husband1.setLastName("Malinowski");
		husband1.setSex(Sex.MAN);
		
		wife1.setFirstName("Anna");
		wife1.setLastName("Kwiatkowska");
		wife1.setSex(Sex.WOMAN);
		
		husband1.addMarriages(wife1);
		wife1.addMarriages(husband1);
		husband1.addWeddingVenue(wife1, "Poznan");
		
		husband2.setFirstName("Pawel");
		husband2.setLastName("Iksinski");
		husband2.setSex(Sex.MAN);
		
		wife2.setFirstName("Julia");
		wife2.setLastName("Pawlak");
		wife2.setSex(Sex.WOMAN);
		
		husband2.addMarriages(wife2);
		wife2.addMarriages(husband2);
		husband2.addWeddingDate(wife2, "1999.01.01");

		
		tree.addPerson("11", husband1);
		tree.addPerson("12", wife1);
		tree.addPerson("13", husband2);
		tree.addPerson("14", wife2);


		
		Person mother1 = new Person();
		Person child1 = new Person();
		Person mother2 = new Person();
		Person child2 = new Person();
		
		mother1.setFirstName("Aleksandra");
		mother1.setLastName("Kowal");
		mother1.setSex(Sex.WOMAN);
		
		mother2.setFirstName("Kamila");
		mother2.setLastName("Bartkowiak");
		mother2.setSex(Sex.WOMAN);
		
		child1.setFirstName("Filip");
		child1.setLastName("Kowal");
		
		child2.setFirstName("Urszula");
		child2.setLastName("Bartkowiak");

		mother1.addChild(child1);
		child1.setMother(mother1);
		mother2.addChild(child2);
		child2.setMother(mother2);


		tree.addPerson("21", mother1);
		tree.addPerson("22", child1);
		tree.addPerson("23", mother2);
		tree.addPerson("24", child2);

		actualTree = tree;
	}
	
	@Test
	public void notEqualsNull() {
		assertFalse(expectedTree.equals(null));
	}
	
	@Test
	public void notEqualsObject() {
		assertFalse(expectedTree.equals(new Object()));
	}
	
	@Test
	public void equals() {
		assertTrue(expectedTree.equals(actualTree));
	}
	
	@Test
	public void equals_theSame() {
		assertEquals(expectedTree, expectedTree);
	}
	
	@Test
	public void notEquals_changeNum() {
		actualTree.setNumberOfPersons(10);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_changeOneName() {
		actualTree.getPerson("1").setFirstName("Filip");
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_addPerson() {
		Person person = new Person();
		
		actualTree.addPerson("4", person);;
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_addChild() {
		Person secondChild = new Person();

		actualTree.getPerson("1").addChild(secondChild);
		actualTree.getPerson("2").addChild(secondChild);
		
		actualTree.addPerson("4", secondChild);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_delMotherRelation() {
		actualTree.getPerson("3").setMother(null);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_delFatherRelation() {
		actualTree.getPerson("3").setFather(null);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_delChildRelation() {
		actualTree.getPerson("1").delAllChildRelation();
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_delSpouseRelation() {
		actualTree.getPerson("1").delAllSpouseRelation();
		assertNotEquals(expectedTree, actualTree);
	}
	
	

	@Test
	public void equals_delAndAddMarriage() {
		Person husband = actualTree.getPerson("1");
		Person wife = actualTree.getPerson("2");
		husband.delAllSpouseRelation();
		husband.addMarriages(wife);
		assertEquals(expectedTree, actualTree);
	}
	
	
	
	@Test
	public void notEquals_delWeddingVenue() {
		Person husband = actualTree.getPerson("11");
		Person wife = actualTree.getPerson("12");
		
		husband.delAllSpouseRelation();
		husband.addMarriages(wife);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_delWeddingDate() {
		Person husband = actualTree.getPerson("13");
		Person wife = actualTree.getPerson("14");
		
		husband.delAllSpouseRelation();
		husband.addMarriages(wife);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_changeSpouse() {
		Person husband1 = actualTree.getPerson("11");
		Person wife1 = actualTree.getPerson("12");
		Person husband2 = actualTree.getPerson("13");
		Person wife2 = actualTree.getPerson("14");
		
		husband1.delAllSpouseRelation();
		wife1.delAllSpouseRelation();
		husband2.delAllSpouseRelation();
		wife2.delAllSpouseRelation();
		
		husband1.addMarriages(wife2);
		wife2.addMarriages(husband1);
		husband2.addMarriages(wife1);
		wife1.addMarriages(husband2);
		
		assertNotEquals(expectedTree, actualTree);
	}
	
	
	

	@Test
	public void notEquals_delAndAddChildren() {
		Person mother1 = actualTree.getPerson("21");
		Person child1  = actualTree.getPerson("22");
		Person mother2 = actualTree.getPerson("23");
		Person child2  = actualTree.getPerson("24");
		
		mother1.delAllChildRelation();
		mother2.delAllChildRelation();
		child1.setMother(null);
		child2.setMother(null);
		
		mother1.addChild(child1);
		child1.setMother(mother1);
		mother2.addChild(child2);
		child2.setMother(mother2);
		
		assertEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_changeChildren() {
		Person mother1 = actualTree.getPerson("21");
		Person child1  = actualTree.getPerson("22");
		Person mother2 = actualTree.getPerson("23");
		Person child2  = actualTree.getPerson("24");
		
		mother1.delAllChildRelation();
		mother2.delAllChildRelation();
		child1.setMother(null);
		child2.setMother(null);
		
		mother1.addChild(child2);
		child2.setMother(mother1);
		mother2.addChild(child1);
		child1.setMother(mother2);
		
		assertNotEquals(expectedTree, actualTree);
	}
}
