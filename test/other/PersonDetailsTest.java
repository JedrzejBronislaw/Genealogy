package other;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Person;

public class PersonDetailsTest {

	private void createParentChildRelation(Person parent, Person child, boolean father) {
		parent.addChild(child);
		if (father)
			child.setFather(parent); else
			child.setMother(parent);
	}
	
	@Test
	public void descendantsBranchesWidth_onePerson() {
		Person person = new Person();
		assertEquals(1, PersonDetails.descendantsBranchesWidth(person));
	}
	
	@Test
	public void descendantsBranchesWidth_oneChild() {
		Person parent = new Person();
		Person child = new Person();
		
		createParentChildRelation(parent, child, true);
		
		assertEquals(1, PersonDetails.descendantsBranchesWidth(parent));
	}
	
	@Test
	public void descendantsBranchesWidth_twoChildern() {
		Person father = new Person();
		Person child1 = new Person();
		Person child2 = new Person();

		createParentChildRelation(father, child1, true);
		createParentChildRelation(father, child2, true);
		
		assertEquals(2, PersonDetails.descendantsBranchesWidth(father));
	}
	
	@Test
	public void descendantsBranchesWidth_treeChildren() {
		Person mother = new Person();
		Person child1 = new Person();
		Person child2 = new Person();
		Person child3 = new Person();
		
		createParentChildRelation(mother, child1, false);
		createParentChildRelation(mother, child2, false);
		createParentChildRelation(mother, child3, false);
		
		assertEquals(3, PersonDetails.descendantsBranchesWidth(mother));
	}
	
	@Test
	public void descendantsBranchesWidth_twentyChildren() {
		Person father = new Person();
		Person[] children = new Person[20];
		
		for(int i=0; i<20; i++) {
			children[i] = new Person();
			createParentChildRelation(father, children[i], true);
		}
		
		assertEquals(20, PersonDetails.descendantsBranchesWidth(father));
	}
	
	@Test
	public void descendantsBranchesWidth_fiveChildren_oneGrandChild() {
		Person father = new Person();
		Person[] children = new Person[5];
		Person grandchild = new Person();
		
		for(int i=0; i<5; i++) {
			children[i] = new Person();
			createParentChildRelation(father, children[i], true);
		}
		
		children[1].addChild(grandchild);
		
		assertEquals(5, PersonDetails.descendantsBranchesWidth(father));
	}
	
	@Test
	public void descendantsBranchesWidth_middleSizeFamily() {
		Person root = new Person();
		Person[] children = new Person[3];
		Person[] grandchildren = new Person[9];
		Person[] ggchildren = new Person[2];

		
		for(int i=0; i<children.length; i++)
			children[i] = new Person();
			
		for(int i=0; i<grandchildren.length; i++)
			grandchildren[i] = new Person();
				
		for(int i=0; i<ggchildren.length; i++)
			ggchildren[i] = new Person();
		

		createParentChildRelation(root, children[0], true);
		createParentChildRelation(root, children[1], true);
		createParentChildRelation(root, children[2], true);

		createParentChildRelation(children[0], grandchildren[0], true);
		createParentChildRelation(children[0], grandchildren[1], true);
		createParentChildRelation(children[0], grandchildren[2], true);
		createParentChildRelation(children[1], grandchildren[3], true);
		createParentChildRelation(children[1], grandchildren[4], true);
		createParentChildRelation(children[1], grandchildren[5], true);
		createParentChildRelation(children[2], grandchildren[6], true);
		createParentChildRelation(children[2], grandchildren[7], true);
		createParentChildRelation(children[2], grandchildren[8], true);

		createParentChildRelation(grandchildren[2], ggchildren[0], true);
		createParentChildRelation(grandchildren[2], ggchildren[1], true);
		
		
		assertEquals(10, PersonDetails.descendantsBranchesWidth(root));
	}

}
