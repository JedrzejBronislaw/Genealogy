package model;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Person.Sex;

public class Tree_Equals2 {

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
		
		husband1.addMarriage(wife1);
		wife1.addMarriage(husband1);
		husband1.addWeddingVenue(wife1, "Poznan");
		
		husband2.setFirstName("Pawel");
		husband2.setLastName("Iksinski");
		husband2.setSex(Sex.MAN);
		
		wife2.setFirstName("Julia");
		wife2.setLastName("Pawlak");
		wife2.setSex(Sex.WOMAN);
		
		husband2.addMarriage(wife2);
		wife2.addMarriage(husband2);
		husband2.addWeddingDate(wife2, "1999.01.01");


		tree.addPerson("11", husband1);
		tree.addPerson("12", wife1);
		tree.addPerson("13", husband2);
		tree.addPerson("14", wife2);
		
		return tree;
	}
	
	
	@Test
	public void notEquals_delWeddingVenue() {
		Person husband = actualTree.getPerson("11");
		Person wife = actualTree.getPerson("12");
		
		husband.delAllSpouseRelation();
		husband.addMarriage(wife);
		assertNotEquals(expectedTree, actualTree);
	}
	
	@Test
	public void notEquals_delWeddingDate() {
		Person husband = actualTree.getPerson("13");
		Person wife = actualTree.getPerson("14");
		
		husband.delAllSpouseRelation();
		husband.addMarriage(wife);
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
		
		husband1.addMarriage(wife2);
		wife2.addMarriage(husband1);
		husband2.addMarriage(wife1);
		wife1.addMarriage(husband2);
		
		assertNotEquals(expectedTree, actualTree);
	}
}
