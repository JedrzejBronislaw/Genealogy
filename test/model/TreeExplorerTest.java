package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Person.Sex;
import model.familyRelations.Editor;
import model.random.RandomPerson;

public class TreeExplorerTest {

	private TreeExplorer explorer;
	private Person grand3mother;
	private Person grandgrandfather, grandgrandmother;
	private Person grandfatherA, grandmotherA;
	private Person grandfatherB, grandmotherB;
	private Person father, mother;
	private Person child1, child2, child3;
	private Person aunt, cousin;
	
	@Before
	public void prepare() {
		Tree tree = new Tree();
		explorer = new TreeExplorer(tree);
		Editor editor = new Editor(tree);
		RandomPerson rPerson = new RandomPerson();
		
		//persons
		grand3mother = rPerson.generate(Sex.WOMAN);
		
		grandgrandfather = rPerson.generate(Sex.MAN);
		grandgrandmother = rPerson.generate(Sex.WOMAN);
		
		grandfatherA = rPerson.generate(Sex.MAN);
		grandmotherA = rPerson.generate(Sex.WOMAN);
		grandfatherB = rPerson.generate(Sex.MAN);
		grandmotherB = rPerson.generate(Sex.WOMAN);

		father = rPerson.generate(Sex.MAN);
		mother = rPerson.generate(Sex.WOMAN);
		
		child1 = rPerson.generate();
		child2 = rPerson.generate();
		child3 = rPerson.generate();

		aunt   = rPerson.generate(Sex.WOMAN);
		cousin = rPerson.generate();

		//relations
		editor.createMarriageRel(grandgrandfather, grandgrandmother);
		editor.createMarriageRel(grandfatherA, grandmotherA);
		editor.createMarriageRel(grandfatherB, grandmotherB);
		editor.createMarriageRel(father, mother);

		editor.setMotherChildRel(grand3mother, grandgrandfather);
		
		editor.setFatherChildRel(grandgrandfather, grandfatherA);
		editor.setMotherChildRel(grandgrandmother, grandfatherA);
		
		editor.setFatherChildRel(grandfatherA, father);
		editor.setMotherChildRel(grandmotherA, father);
		
		editor.setFatherChildRel(grandfatherB, mother);
		editor.setMotherChildRel(grandmotherB, mother);
		
		editor.setFatherChildRel(father, child1);
		editor.setFatherChildRel(father, child2);
		editor.setFatherChildRel(father, child3);
		editor.setMotherChildRel(mother, child1);
		editor.setMotherChildRel(mother, child2);
		editor.setMotherChildRel(mother, child3);
		
		editor.setMotherChildRel(grandmotherB, aunt);
		editor.setFatherChildRel(grandfatherB, aunt);
		editor.setMotherChildRel(aunt, cousin);
	}
	
	@Test
	public void testGetLeaves() {
		List<Person> expected = Arrays.asList(child1, child2, child3, cousin);
		List<Person> actual = explorer.getLeaves();
		
		assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
	}

	@Test
	public void testGetRoots() {
		List<Person> expected = Arrays.asList(
				grand3mother,
				grandgrandmother,
				grandmotherA,
				grandfatherB, grandmotherB);
		List<Person> actual = explorer.getRoots();
		
		assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
	}

	@Test
	public void testGetRootsBoolean() {
		List<Person> expected = Arrays.asList(
				grand3mother,
				grandfatherB, grandmotherB);
		List<Person> actual = explorer.getRoots(true);
		
		assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
	}

}
