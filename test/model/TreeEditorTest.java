package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.random.RandomPerson;

public class TreeEditorTest {

	private Tree tree;
	private TreeEditor editor;
	private RandomPerson rPerson = new RandomPerson();
	
	@Before
	public void prepare() {
		tree = new Tree();
		editor = new TreeEditor(tree);
	}
	
	@Test
	public void testGetPerson_success() {
		Person person = rPerson.generate();
		tree.addPerson("5", person);
		
		assertTrue(person == editor.getPerson(5));
	}
	
	@Test
	public void testGetPerson_null() {
		Person person = rPerson.generate();
		tree.addPerson("5", person);
		
		assertNull(editor.getPerson(1));
	}

	@Test
	public void testGenerateId_5() {
		for (int i = 0; i < 5; i++)
			tree.addPerson(i+"", rPerson.generate());
		
		assertEquals("5", editor.generateId());
	}
	
	@Test
	public void testGenerateId_10() {
		for (int i = 0; i < 10; i++)
			tree.addPerson(i+"", rPerson.generate());
		
		assertEquals("10", editor.generateId());
	}
	
	@Test
	public void testGenerateId_12() {
		for (int i = 6; i < 12; i++)
			tree.addPerson(i+"", rPerson.generate());
		
		assertEquals("12", editor.generateId());
	}

	@Test
	public void testIsInTree_true() {
		Person person1 = rPerson.generate();
		Person person2 = rPerson.generate();
		@SuppressWarnings("unused")
		Person person3 = rPerson.generate();

		tree.addPerson("1", person1);
		tree.addPerson("2", person2);
		
		assertTrue(editor.isInTree(person1));
	}
	
	@Test
	public void testIsInTree_false() {
		Person person1 = rPerson.generate();
		Person person2 = rPerson.generate();
		Person person3 = rPerson.generate();

		tree.addPerson("1", person1);
		tree.addPerson("2", person2);
		
		assertFalse(editor.isInTree(person3));
	}

	@Test
	public void testAddIfIsOutside() {
		Person person1 = rPerson.generate();
		Person person2 = rPerson.generate();
		Person person3 = rPerson.generate();
		
		tree.addPerson("1", person1);
		tree.addPerson("2", person2);

		assertEquals(2, tree.numberOfPersons());
		editor.addIfIsOutside(person3);
		assertEquals(3, tree.numberOfPersons());
	}

	@Test
	public void testTreeEditor() {
		Person person1 = rPerson.generate();
		Person person2 = rPerson.generate();
		Person person3 = rPerson.generate();
		
		tree.addPerson("1", person1);
		tree.addPerson("2", person2);
		tree.addPerson("3", person3);
		
		assertEquals(3, tree.numberOfPersons());
		editor.addIfIsOutside(person3);
		assertEquals(3, tree.numberOfPersons());
	}

}
