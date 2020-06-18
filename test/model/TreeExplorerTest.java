package model;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.familyRelations.RelationEditor;
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
		RelationEditor relEditor = new RelationEditor(tree);
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
		relEditor.createMarriageRel(grandgrandfather, grandgrandmother);
		relEditor.createMarriageRel(grandfatherA, grandmotherA);
		relEditor.createMarriageRel(grandfatherB, grandmotherB);
		relEditor.createMarriageRel(father, mother);

		relEditor.setMotherChildRel(grand3mother, grandgrandfather);
		relEditor.setParentsChildRel(grandgrandfather, grandgrandmother, grandfatherA);
		relEditor.setParentsChildRel(grandfatherA, grandmotherA, father);
		relEditor.setParentsChildRel(grandfatherB, grandmotherB, mother);

		relEditor.setParentsChildRel(father, mother, child1);
		relEditor.setParentsChildRel(father, mother, child2);
		relEditor.setParentsChildRel(father, mother, child3);

		relEditor.setParentsChildRel(grandfatherB, grandmotherB, aunt);
		relEditor.setMotherChildRel(aunt, cousin);
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
