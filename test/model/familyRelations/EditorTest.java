package model.familyRelations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeEditor;
import model.random.RandomPerson;

public class EditorTest {

	private Editor editor;
	private RandomPerson rPerson;
	
	@Before
	public void prepare() {
		editor = new Editor();
		rPerson = new RandomPerson();
	}
	
	//-----MOTHER-----
	
	@Test
	public void testSetMotherChildRel_maleMother() {
		Person mother = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
		
		assertFalse(editor.setMotherChildRel(mother, child));
		
		assertEquals(0, mother.numberOfChildren());
		assertNull(child.getMother());
		assertNull(child.getFather());
	}
	
	@Test
	public void testSetMotherChildRel_add() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertTrue(editor.setMotherChildRel(mother, child));
		
		assertEquals(1, mother.numberOfChildren());
		assertTrue(child.getMother() == mother);
		assertTrue(mother.getChild(0) == child);
	}

	@Test
	public void testSetMotherChildRel_changeMother() {
		Person mother1 = rPerson.generate(Sex.WOMAN);
		Person mother2 = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
				
		editor.setMotherChildRel(mother1, child);
		editor.setMotherChildRel(mother2, child);
		
		assertEquals(0, mother1.numberOfChildren());
		assertEquals(1, mother2.numberOfChildren());
		assertTrue(child.getMother() == mother2);
		assertTrue(mother2.getChild(0) == child);
	}

	@Test
	public void testSetMotherChildRel_add2Children() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child1  = rPerson.generate();
		Person child2  = rPerson.generate();
		
		editor.setMotherChildRel(mother, child1);
		editor.setMotherChildRel(mother, child2);
		
		assertEquals(2, mother.numberOfChildren());
		assertTrue(child1.getMother() == mother);
		assertTrue(child2.getMother() == mother);
		assertTrue(mother.getChild(0) == child1);
		assertTrue(mother.getChild(1) == child2);
	}
	
	@Test
	public void testSetMotherChildRel_addToTree() {
		Tree tree = new Tree();
		Editor editor = new Editor(tree);
		TreeEditor treeEditor = new TreeEditor(tree);
		
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		tree.addPerson("1", mother);
		editor.setMotherChildRel(mother, child);
		
		assertEquals(2, tree.numberOfPersons());
		assertTrue(treeEditor.isInTree(mother));
		assertTrue(treeEditor.isInTree(child));
	}
	
	//-----FATHER-----

	@Test
	public void testSetFatherChildRel_femaleFather() {
		Person father = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertFalse(editor.setFatherChildRel(father, child));
		
		assertEquals(0, father.numberOfChildren());
		assertNull(child.getFather());
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetFatherChildRel_add() {
		Person father = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
		
		assertTrue(editor.setFatherChildRel(father, child));
		
		assertEquals(1, father.numberOfChildren());
		assertTrue(child.getFather() == father);
		assertTrue(father.getChild(0) == child);
	}

	@Test
	public void testSetFatherChildRel_changeFather() {
		Person father1 = rPerson.generate(Sex.MAN);
		Person father2 = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
				
		editor.setFatherChildRel(father1, child);
		editor.setFatherChildRel(father2, child);
		
		assertEquals(0, father1.numberOfChildren());
		assertEquals(1, father2.numberOfChildren());
		assertTrue(child.getFather() == father2);
		assertTrue(father2.getChild(0) == child);
	}

	@Test
	public void testSetFatherChildRel_add2Children() {
		Person father = rPerson.generate(Sex.MAN);
		Person child1  = rPerson.generate();
		Person child2  = rPerson.generate();
		
		editor.setFatherChildRel(father, child1);
		editor.setFatherChildRel(father, child2);
		
		assertEquals(2, father.numberOfChildren());
		assertTrue(child1.getFather() == father);
		assertTrue(child2.getFather() == father);
		assertTrue(father.getChild(0) == child1);
		assertTrue(father.getChild(1) == child2);
	}
	
	@Test
	public void testSetFatherChildRel_addToTree() {
		Tree tree = new Tree();
		Editor editor = new Editor(tree);
		TreeEditor treeEditor = new TreeEditor(tree);
		
		Person father = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
		
		tree.addPerson("1", father);
		editor.setFatherChildRel(father, child);
		
		assertEquals(2, tree.numberOfPersons());
		assertTrue(treeEditor.isInTree(father));
		assertTrue(treeEditor.isInTree(child));
	}
	
	//-----MOTHER-----

	@Test
	public void testDelMotherRelation() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		child.setMother(mother);
		mother.addChild(child);
		
		editor.delMotherRelation(child);
		
		assertNull(child.getMother());
		assertEquals(0, mother.numberOfChildren());
	}

	@Test
	public void testDelMotherRelation_paramMother() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		child.setMother(mother);
		mother.addChild(child);
		
		editor.delMotherRelation(mother);
		
		assertTrue(child.getMother() == mother);
		assertEquals(1, mother.numberOfChildren());
	}

	@Test
	public void testDelMotherRelation_noMother() {
		Person child  = rPerson.generate();
		
		editor.delMotherRelation(child);
		
		assertNull(child.getMother());
	}

	//-----FATHER-----

	@Test
	public void testDelFatherRelation() {
		Person father = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
		
		child.setFather(father);
		father.addChild(child);
		
		editor.delFatherRelation(child);
		
		assertNull(child.getFather());
		assertEquals(0, father.numberOfChildren());
	}

	@Test
	public void testDelFatherRelation_paramFather() {
		Person father = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
		
		child.setFather(father);
		father.addChild(child);
		
		editor.delFatherRelation(father);
		
		assertTrue(child.getFather() == father);
		assertEquals(1, father.numberOfChildren());
	}

	@Test
	public void testDelFatherRelation_noFather() {
		Person child  = rPerson.generate();
		
		editor.delFatherRelation(child);
		
		assertNull(child.getFather());
	}

	//-----MARRIAGE-----

	@Test
	public void testCreateMarriageRel_add() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.createMarriageRel(husband, wife, "01.01.2000", "Warsaw"));
		
		assertEquals(1, husband.numberOfMarriages());
		assertEquals(1,    wife.numberOfMarriages());
		
		assertEquals(wife, husband.getSpouse(0));
		assertEquals(husband, wife.getSpouse(0));
		
		assertEquals("01.01.2000", husband.getMarriage(0).getDate());
		assertEquals("Warsaw", husband.getMarriage(0).getPlace());
		assertEquals("01.01.2000", wife.getMarriage(0).getDate());
		assertEquals("Warsaw", wife.getMarriage(0).getPlace());
	}
	
	@Test
	public void testCreateMarriageRel_reverseOrder() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.createMarriageRel(wife, husband));
		
		assertEquals(1, husband.numberOfMarriages());
		assertEquals(1,    wife.numberOfMarriages());
		
		assertEquals(wife, husband.getSpouse(0));
		assertEquals(husband, wife.getSpouse(0));
	}

	@Test
	public void testCreateMarriageRel_addToTree() {
		Tree tree = new Tree();
		Editor editor = new Editor(tree);
		TreeEditor treeEditor = new TreeEditor(tree);

		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.createMarriageRel(husband, wife));
		
		assertEquals(2, tree.numberOfPersons());
		assertTrue(treeEditor.isInTree(husband));
		assertTrue(treeEditor.isInTree(wife));
	}
	
	@Test
	public void testCreateMarriageRel_twoMen() {
		Person person1 = rPerson.generate(Sex.MAN);
		Person person2 = rPerson.generate(Sex.MAN);
		
		assertFalse(editor.createMarriageRel(person1, person2));
		
		assertEquals(0, person1.numberOfMarriages());
		assertEquals(0, person2.numberOfMarriages());
	}
	
	@Test
	public void testCreateMarriageRel_twoWomen() {
		Person person1 = rPerson.generate(Sex.WOMAN);
		Person person2 = rPerson.generate(Sex.WOMAN);
		
		assertFalse(editor.createMarriageRel(person1, person2));
		
		assertEquals(0, person1.numberOfMarriages());
		assertEquals(0, person2.numberOfMarriages());
	}
	
	@Test
	public void testCreateMarriageRel_onlyMen() {
		Person person1 = rPerson.generate(Sex.MAN);
		Person person2 = rPerson.generate(Sex.UNKNOWN);
		
		assertFalse(editor.createMarriageRel(person1, person2));
		
		assertEquals(0, person1.numberOfMarriages());
		assertEquals(0, person2.numberOfMarriages());
	}
	
	@Test
	public void testCreateMarriageRel_onlyWomen() {
		Person person1 = rPerson.generate(Sex.WOMAN);
		Person person2 = rPerson.generate(Sex.UNKNOWN);
		
		assertFalse(editor.createMarriageRel(person1, person2));
		
		assertEquals(0, person1.numberOfMarriages());
		assertEquals(0, person2.numberOfMarriages());
	}

	//-----DEL MARRIAGE-----
	
	@Test
	public void testDelMarriageRel() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.createMarriageRel(husband, wife));
		assertTrue(editor.delMarriageRel(husband, wife));
		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0,    wife.numberOfMarriages());
	}
	
	@Test
	public void testDelMarriageRel_reverseOrder() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.createMarriageRel(husband, wife));
		assertTrue(editor.delMarriageRel(wife, husband));
		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0,    wife.numberOfMarriages());
	}
	
	@Test
	public void testDelMarriageRel_noMarriage() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.delMarriageRel(wife, husband));
		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0,    wife.numberOfMarriages());
	}
	
	@Test
	public void testDelMarriageRel_twoMarriages() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);

		assertTrue(editor.createMarriageRel(husband1, wife1));
		assertTrue(editor.createMarriageRel(husband2, wife2));
		
		assertTrue(editor.delMarriageRel(wife1, husband1));

		assertEquals(0, husband1.numberOfMarriages());
		assertEquals(0,    wife1.numberOfMarriages());
		assertEquals(1, husband2.numberOfMarriages());
		assertEquals(1,    wife2.numberOfMarriages());
	}
	
	@Test
	public void testDelMarriageRel_otherMarriage() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);

		assertTrue(editor.createMarriageRel(husband1, wife1));
		assertTrue(editor.createMarriageRel(husband2, wife2));
		
		assertTrue(editor.delMarriageRel(wife1, husband2));

		assertEquals(1, husband1.numberOfMarriages());
		assertEquals(1,    wife1.numberOfMarriages());
		assertEquals(1, husband2.numberOfMarriages());
		assertEquals(1,    wife2.numberOfMarriages());
	}

	//-----SET PARENTS-----

	@Test
	public void testSetParentsChildRel() {
		Person father = rPerson.generate(Sex.MAN);
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertTrue(editor.setParentsChildRel(father, mother, child));
		
		assertEquals(1, father.numberOfChildren());
		assertEquals(1, mother.numberOfChildren());
		assertEquals(father, child.getFather());
		assertEquals(mother, child.getMother());
	}

	@Test
	public void testSetParentsChildRel_addToTree() {
		Tree tree = new Tree();
		Editor editor = new Editor(tree);
		TreeEditor treeEditor = new TreeEditor(tree);
		Person father = rPerson.generate(Sex.MAN);
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertTrue(editor.setParentsChildRel(father, mother, child));
		
		assertEquals(3, tree.numberOfPersons());
		assertTrue(treeEditor.isInTree(father));
		assertTrue(treeEditor.isInTree(mother));
		assertTrue(treeEditor.isInTree(child));
		
	}
	
	@Test
	public void testSetParentsChildRel_reverseOrder() {
		Person father = rPerson.generate(Sex.MAN);
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertTrue(editor.setParentsChildRel(mother, father, child));
		
		assertEquals(1, father.numberOfChildren());
		assertEquals(1, mother.numberOfChildren());
		assertEquals(father, child.getFather());
		assertEquals(mother, child.getMother());
	}
	
	@Test
	public void testSetParentsChildRel_twoMen() {
		Person person1 = rPerson.generate(Sex.MAN);
		Person person2 = rPerson.generate(Sex.MAN);
		Person child  = rPerson.generate();
		
		assertFalse(editor.setParentsChildRel(person2, person1, child));
		
		assertEquals(0, person1.numberOfChildren());
		assertEquals(0, person2.numberOfChildren());
		assertNull(child.getFather());
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetParentsChildRel_twoWomen() {
		Person person1 = rPerson.generate(Sex.WOMAN);
		Person person2 = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertFalse(editor.setParentsChildRel(person2, person1, child));
		
		assertEquals(0, person1.numberOfChildren());
		assertEquals(0, person2.numberOfChildren());
		assertNull(child.getFather());
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetParentsChildRel_onlyMen() {
		Person person1 = rPerson.generate(Sex.MAN);
		Person person2 = rPerson.generate(Sex.UNKNOWN);
		Person child  = rPerson.generate();
		
		assertFalse(editor.setParentsChildRel(person2, person1, child));
		
		assertEquals(0, person1.numberOfChildren());
		assertEquals(0, person2.numberOfChildren());
		assertNull(child.getFather());
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetParentsChildRel_onlyWomen() {
		Person person1 = rPerson.generate(Sex.WOMAN);
		Person person2 = rPerson.generate(Sex.UNKNOWN);
		Person child  = rPerson.generate();
		
		assertFalse(editor.setParentsChildRel(person2, person1, child));
		
		assertEquals(0, person1.numberOfChildren());
		assertEquals(0, person2.numberOfChildren());
		assertNull(child.getFather());
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetParentsChildRel_otherParents() {
		Person father = rPerson.generate(Sex.MAN);
		Person mother = rPerson.generate(Sex.WOMAN);
		Person newFather = rPerson.generate(Sex.MAN);
		Person newMother = rPerson.generate(Sex.WOMAN);
		Person child  = rPerson.generate();
		
		assertTrue(editor.setParentsChildRel(mother, father, child));
		assertTrue(editor.setParentsChildRel(newMother, newFather, child));

		assertEquals(0, father.numberOfChildren());
		assertEquals(0, mother.numberOfChildren());
		assertEquals(1, newFather.numberOfChildren());
		assertEquals(1, newMother.numberOfChildren());
		assertEquals(newFather, child.getFather());
		assertEquals(newMother, child.getMother());
	}
}
