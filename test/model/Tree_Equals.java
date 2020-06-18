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

import model.familyRelations.RelationEditor;

public class Tree_Equals {

	private static final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
	
	private static Tree expectedTree;
	private Tree actualTree;
	
	@BeforeClass
	public static void prepareExpected() {
		expectedTree = prepareTree();
	}
	
	@Before
	public void prepareActual() {
		actualTree = prepareTree();
	}
	
	public static Tree prepareTree() {
		Tree tree = new Tree();
		RelationEditor relationEditor = new RelationEditor();
		
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

		mother.setFirstName("Ewa");
		mother.setLastName("Nowak");
		mother.setSex(Sex.WOMAN);

		child.setFirstName("Robert");
		child.setLastName("Kowalski");
		child.setSex(Sex.MAN);

		relationEditor.createMarriageRel(father, mother);
		relationEditor.setParentsChildRel(father, mother, child);
		
		tree.addPerson("1", father);
		tree.addPerson("2", mother);
		tree.addPerson("3", child);
		
		return tree;
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
	public void notTeSame() {
		assertFalse(expectedTree == actualTree);
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
	public void notEquals_changeLastModification() throws ParseException {
		actualTree.setLastModification(format.parse("21:16:14 20.02.2014"));
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_changeLastOpen() throws ParseException {
		actualTree.setLastOpen(format.parse("04:22:54 11.08.2015"));
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
		husband.addMarriage(wife);
		assertEquals(expectedTree, actualTree);
	}
}
