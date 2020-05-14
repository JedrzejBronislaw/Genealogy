package model.familyRelations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Marriage;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeEditor;
import model.random.RandomPerson;

public class RelationEditorTest {

	private RelationEditor editor;
	private RandomPerson rPerson;
	
	@Before
	public void prepare() {
		editor = new RelationEditor();
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
	public void testSetMotherChildRel_nullMother() {
		Person child  = rPerson.generate();
		
		assertFalse(editor.setMotherChildRel(null, child));
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetMotherChildRel_nullChild() {
		Person mother = rPerson.generate(Sex.WOMAN);
		
		assertFalse(editor.setMotherChildRel(mother, null));
		assertEquals(0, mother.numberOfChildren());
	}
	
	@Test
	public void testSetMotherChildRel_null() {
		assertFalse(editor.setMotherChildRel(null, null));
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
		RelationEditor editor = new RelationEditor(tree);
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
	public void testSetFatherChildRel_nullFather() {
		Person child  = rPerson.generate();
		
		assertFalse(editor.setFatherChildRel(null, child));
		assertNull(child.getMother());
	}
	
	@Test
	public void testSetFatherChildRel_nullChild() {
		Person father = rPerson.generate(Sex.MAN);
		
		assertFalse(editor.setFatherChildRel(father, null));
		assertEquals(0, father.numberOfChildren());
	}
	
	@Test
	public void testSetFatherChildRel_null() {
		assertFalse(editor.setFatherChildRel(null, null));
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
		RelationEditor editor = new RelationEditor(tree);
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
	public void testDelMotherRelation_null() {
		assertFalse(editor.delMotherRelation(null));
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
	public void testDelFatherRelation_null() {
		assertFalse(editor.delFatherRelation(null));
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
		RelationEditor editor = new RelationEditor(tree);
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
		RelationEditor editor = new RelationEditor(tree);
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

	//-----SET CHILDREN-----

	@Test
	public void testSetParentChildrenRel_father() {
		Person father = rPerson.generate(Sex.MAN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(father, children));
		
		assertTrue(father == child1.getFather());
		assertTrue(father == child2.getFather());
		assertTrue(father == child3.getFather());

		assertEquals(3, father.numberOfChildren());
		assertTrue(father.getChild(0) == child1);
		assertTrue(father.getChild(1) == child2);
		assertTrue(father.getChild(2) == child3);
	}

	@Test
	public void testSetParentChildrenRel_mother() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(mother, children));
		
		assertTrue(mother == child1.getMother());
		assertTrue(mother == child2.getMother());
		assertTrue(mother == child3.getMother());

		assertEquals(3, mother.numberOfChildren());
		assertTrue(mother.getChild(0) == child1);
		assertTrue(mother.getChild(1) == child2);
		assertTrue(mother.getChild(2) == child3);
	}

	@Test
	public void testSetParentChildrenRel_bothParents() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person father = rPerson.generate(Sex.MAN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children123 = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(father, children123));
		assertTrue(editor.setParentChildrenRel(mother, children123));
		
		assertTrue(mother == child1.getMother());
		assertTrue(mother == child2.getMother());
		assertTrue(mother == child3.getMother());
		assertTrue(father == child1.getFather());
		assertTrue(father == child2.getFather());
		assertTrue(father == child3.getFather());

		assertEquals(3, mother.numberOfChildren());
		assertEquals(3, father.numberOfChildren());

		assertTrue(mother.getChild(0) == child1);
		assertTrue(mother.getChild(1) == child2);
		assertTrue(mother.getChild(2) == child3);
		assertTrue(father.getChild(0) == child1);
		assertTrue(father.getChild(1) == child2);
		assertTrue(father.getChild(2) == child3);
	}

	@Test
	public void testSetParentChildrenRel_nullParent() {
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children = Arrays.asList(child1, child2, child3);
		
		assertFalse(editor.setParentChildrenRel(null, children));
		
		assertNull(child1.getMother());
		assertNull(child2.getMother());
		assertNull(child3.getMother());
	}

	@Test
	public void testSetParentChildrenRel_mother_nullChildren() {
		Person mother = rPerson.generate(Sex.WOMAN);
		
		assertFalse(editor.setParentChildrenRel(mother, null));
		assertEquals(0, mother.numberOfChildren());
	}

	@Test
	public void testSetParentChildrenRel_father_nullChildren() {
		Person father = rPerson.generate(Sex.MAN);
		
		assertFalse(editor.setParentChildrenRel(father, null));
		assertEquals(0, father.numberOfChildren());
	}

	@Test
	public void testSetParentChildrenRel_emptyList() {
		Person mother = rPerson.generate(Sex.WOMAN);
		List<Person> children = new ArrayList<>();
		
		assertTrue(editor.setParentChildrenRel(mother, children));
		assertEquals(0, mother.numberOfChildren());
	}

	@Test
	public void testSetParentChildrenRel_unknowSexParent() {
		Person parent = rPerson.generate(Sex.WOMAN);
		parent.setSex(Sex.UNKNOWN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children = Arrays.asList(child1, child2, child3);
		
		assertFalse(editor.setParentChildrenRel(parent, children));
		
		assertNull(child1.getMother());
		assertNull(child2.getMother());
		assertNull(child3.getMother());

		assertEquals(0, parent.numberOfChildren());
	}

	@Test
	public void testSetParentChildrenRel_prevChildren() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person childA = rPerson.generate();
		Person childB = rPerson.generate();
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> childrenAB = Arrays.asList(childA, childB);
		List<Person> children123 = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(mother, childrenAB));
		assertTrue(editor.setParentChildrenRel(mother, children123));
		
		assertNull(childA.getMother());
		assertNull(childB.getMother());
		
		assertTrue(mother == child1.getMother());
		assertTrue(mother == child2.getMother());
		assertTrue(mother == child3.getMother());

		assertEquals(3, mother.numberOfChildren());
		assertTrue(mother.getChild(0) == child1);
		assertTrue(mother.getChild(1) == child2);
		assertTrue(mother.getChild(2) == child3);
	}

	@Test
	public void testSetParentChildrenRel_prevParents() {
		Person mother1 = rPerson.generate(Sex.WOMAN);
		Person mother2 = rPerson.generate(Sex.WOMAN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		Person child4 = rPerson.generate();
		List<Person> children124 = Arrays.asList(child1, child2, child4);
		List<Person> children123 = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(mother1, children124));
		assertTrue(editor.setParentChildrenRel(mother2, children123));
		
		assertTrue(mother2 == child1.getMother());
		assertTrue(mother2 == child2.getMother());
		assertTrue(mother2 == child3.getMother());
		assertTrue(mother1 == child4.getMother());

		assertEquals(1, mother1.numberOfChildren());
		assertEquals(3, mother2.numberOfChildren());

		assertTrue(mother1.getChild(0) == child4);
		assertTrue(mother2.getChild(0) == child1);
		assertTrue(mother2.getChild(1) == child2);
		assertTrue(mother2.getChild(2) == child3);
	}

	//-----DEL CHILDREN REL-----

	@Test
	public void delChildrenRelations_father() {
		Person father = rPerson.generate(Sex.MAN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(father, children));
		assertTrue(editor.delChildrenRelations(father));
		
		assertNull(child1.getFather());
		assertNull(child2.getFather());
		assertNull(child3.getFather());

		assertEquals(0, father.numberOfChildren());
	}

	@Test
	public void delChildrenRelations_mother() {
		Person mother = rPerson.generate(Sex.WOMAN);
		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		Person child3 = rPerson.generate();
		List<Person> children = Arrays.asList(child1, child2, child3);
		
		assertTrue(editor.setParentChildrenRel(mother, children));
		assertTrue(editor.delChildrenRelations(mother));
		
		assertNull(child1.getMother());
		assertNull(child2.getMother());
		assertNull(child3.getMother());

		assertEquals(0, mother.numberOfChildren());
	}

	@Test
	public void delChildrenRelations_parentWithoutChildren() {
		Person mother = rPerson.generate(Sex.WOMAN);
		
		assertTrue(editor.delChildrenRelations(mother));
		assertEquals(0, mother.numberOfChildren());
	}

	@Test
	public void delChildrenRelations_nullParent() {
		assertFalse(editor.delChildrenRelations(null));
	}

	@Test
	public void delChildrenRelations_unknowSexParent() {
		Person parent = rPerson.generate();
		parent.setSex(Sex.UNKNOWN);
		
		assertTrue(editor.delChildrenRelations(parent));
	}

	//-----SET SPOUSES REL-----

	@Test
	public void setSpousesRel() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Person> wifes = Arrays.asList(wife1, wife2, wife3);
		
		assertTrue(editor.setSpousesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband, wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife2.getMarriage(0).getHusband());
		assertEquals(husband, wife3.getMarriage(0).getHusband());
	}

	@Test
	public void setSpousesRel_femaleHusband() {
		Person husband = rPerson.generate(Sex.WOMAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Person> wifes = Arrays.asList(wife1, wife2, wife3);
		
		assertFalse(editor.setSpousesRel(husband, wifes));

		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0, wife1.numberOfMarriages());
		assertEquals(0, wife2.numberOfMarriages());
		assertEquals(0, wife3.numberOfMarriages());
	}
	
	//-----SET MERRIAGES REL-----

	@Test
	public void setMarriagesRel_man() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertTrue(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband, wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife2.getMarriage(0).getHusband());
		assertEquals(husband, wife3.getMarriage(0).getHusband());
	}

	@Test
	public void setMarriagesRel_woman() {
		Person wife = rPerson.generate(Sex.WOMAN);
		Person husband1 = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person husband3 = rPerson.generate(Sex.MAN);
		List<Marriage> husbands = Arrays.asList(
				new Marriage(wife, husband1),
				new Marriage(wife, husband2),
				new Marriage(wife, husband3));
		
		assertTrue(editor.setMarriagesRel(wife, husbands));

		
		assertEquals(3, wife.numberOfMarriages());
		assertEquals(1, husband1.numberOfMarriages());
		assertEquals(1, husband2.numberOfMarriages());
		assertEquals(1, husband3.numberOfMarriages());
		
		assertEquals(husband1, wife.getMarriage(0).getHusband());
		assertEquals(husband2, wife.getMarriage(1).getHusband());
		assertEquals(husband3, wife.getMarriage(2).getHusband());
		
		assertEquals(wife, husband1.getMarriage(0).getWife());
		assertEquals(wife, husband2.getMarriage(0).getWife());
		assertEquals(wife, husband3.getMarriage(0).getWife());
	}

	@Test
	public void setMarriagesRel_spousesWithSpouses() {
		Person husband = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertTrue(editor.createMarriageRel(husband2, wife1));
		assertTrue(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(2, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband2,  wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife1.getMarriage(1).getHusband());
		assertEquals(husband,  wife2.getMarriage(0).getHusband());
		assertEquals(husband,  wife3.getMarriage(0).getHusband());
	}

	@Test
	public void setMarriagesRel_spouseWithMarriageWithThisPerson() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));

		wife1.addMarriage(husband);
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(husband, wife1.getSpouse(0));
		
		assertTrue(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband, wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife2.getMarriage(0).getHusband());
		assertEquals(husband, wife3.getMarriage(0).getHusband());
	}
	
	@Test
	public void setMarriagesRel_existingSpouse() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertTrue(editor.createMarriageRel(husband, wife1));
		assertTrue(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband, wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife2.getMarriage(0).getHusband());
		assertEquals(husband, wife3.getMarriage(0).getHusband());
	}
	
	@Test
	public void setMarriagesRel_twiceInTheList() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertTrue(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband, wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife2.getMarriage(0).getHusband());
		assertEquals(husband, wife3.getMarriage(0).getHusband());
	}
	
	@Test
	public void setMarriagesRel_nullPerson() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertFalse(editor.setMarriagesRel(null, wifes));

		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0, wife1.numberOfMarriages());
		assertEquals(0, wife2.numberOfMarriages());
		assertEquals(0, wife3.numberOfMarriages());
	}
	
	@Test
	public void setMarriagesRel_nullList() {
		Person husband = rPerson.generate(Sex.MAN);
		
		assertFalse(editor.setMarriagesRel(husband, null));
		assertEquals(0, husband.numberOfMarriages());
	}

	@Test
	public void setMarriagesRel_nullinTheList() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				null,
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertTrue(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(3, husband.numberOfMarriages());
		assertEquals(1, wife1.numberOfMarriages());
		assertEquals(1, wife2.numberOfMarriages());
		assertEquals(1, wife3.numberOfMarriages());
		
		assertEquals(wife1, husband.getMarriage(0).getWife());
		assertEquals(wife2, husband.getMarriage(1).getWife());
		assertEquals(wife3, husband.getMarriage(2).getWife());
		
		assertEquals(husband, wife1.getMarriage(0).getHusband());
		assertEquals(husband, wife2.getMarriage(0).getHusband());
		assertEquals(husband, wife3.getMarriage(0).getHusband());
	}
	
	@Test
	public void setMarriagesRel_empty() {
		Person husband = rPerson.generate(Sex.MAN);
		List<Marriage> wifes = new ArrayList<>();
		
		assertTrue(editor.setMarriagesRel(husband, wifes));
		
		assertEquals(0, husband.numberOfMarriages());
	}
	
	@Test
	public void setMarriagesRel_emptyWithSpouses() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = new ArrayList<>();
		
		assertTrue(editor.createMarriageRel(husband, wife));

		assertEquals(1, husband.numberOfMarriages());
		assertEquals(1, wife.numberOfMarriages());
		
		
		assertTrue(editor.setMarriagesRel(husband, wifes));
		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0, wife.numberOfMarriages());
	}
	
	@Test
	public void setMarriagesRel_wrongHusband() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wrongHusband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(wrongHusband, wife2),
				new Marriage(husband, wife3));
		
		assertFalse(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0, wife1.numberOfMarriages());
		assertEquals(0, wife2.numberOfMarriages());
		assertEquals(0, wife3.numberOfMarriages());
	}
	
	@Test
	public void setMarriagesRel_femaleHusband() {
		Person husband = rPerson.generate(Sex.WOMAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertFalse(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0, wife1.numberOfMarriages());
		assertEquals(0, wife2.numberOfMarriages());
		assertEquals(0, wife3.numberOfMarriages());
	}
	
	@Test
	public void setMarriagesRel_wrongHusband_existingSpouse() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wrongHusband = rPerson.generate(Sex.MAN);
		Person wife  = rPerson.generate(Sex.WOMAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(wrongHusband, wife2),
				new Marriage(husband, wife3));
		
		
		assertTrue(editor.createMarriageRel(husband, wife));

		assertEquals(1, husband.numberOfMarriages());
		assertEquals(1, wife.numberOfMarriages());
		
		assertFalse(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(1, husband.numberOfMarriages());
		assertEquals(1, wife.numberOfMarriages());
		assertEquals(0, wife1.numberOfMarriages());
		assertEquals(0, wife2.numberOfMarriages());
		assertEquals(0, wife3.numberOfMarriages());
	}
	
	@Test
	public void setMarriagesRel_maleWife() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.MAN);
		List<Marriage> wifes = Arrays.asList(
				new Marriage(husband, wife1),
				new Marriage(husband, wife2),
				new Marriage(husband, wife3));
		
		assertFalse(editor.setMarriagesRel(husband, wifes));

		
		assertEquals(0, husband.numberOfMarriages());
		assertEquals(0, wife1.numberOfMarriages());
		assertEquals(0, wife2.numberOfMarriages());
		assertEquals(0, wife3.numberOfMarriages());
	}
	
}
