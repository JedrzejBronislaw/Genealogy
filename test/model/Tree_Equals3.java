package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Person.Sex;
import model.familyRelations.RelationEditor;

public class Tree_Equals3 {

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

		relationEditor.setMotherChildRel(mother1, child1);
		relationEditor.setMotherChildRel(mother2, child2);


		tree.addPerson("21", mother1);
		tree.addPerson("22", child1);
		tree.addPerson("23", mother2);
		tree.addPerson("24", child2);
		
		return tree;
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
	
	@Test
	public void notEquals_changeOnlyMotherRelation() {
		Person mother1 = actualTree.getPerson("21");
		Person child1  = actualTree.getPerson("22");
		Person mother2 = actualTree.getPerson("23");
		Person child2  = actualTree.getPerson("24");
		
		child1.setMother(mother2);
		child2.setMother(mother1);
		
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_changeOnlyChildRelation() {
		Person mother1 = actualTree.getPerson("21");
		Person child1  = actualTree.getPerson("22");
		Person mother2 = actualTree.getPerson("23");
		Person child2  = actualTree.getPerson("24");

		mother1.delAllChildRelation();
		mother2.delAllChildRelation();
		
		mother1.addChild(child2);
		mother2.addChild(child1);
		
		assertNotEquals(expectedTree, actualTree);
	}
}
