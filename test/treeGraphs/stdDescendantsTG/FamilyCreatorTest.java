package treeGraphs.stdDescendantsTG;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Person;
import treeGraphs.painter.Point;

public class FamilyCreatorTest {
	
	private TestFamily testFamily;
	
	@Before
	public void prepare() {
		testFamily = new TestFamily();
		testFamily.prepare();
	}
	
	private void checkLocations(Point[] locations, PersonLocation[] persons) {
		for(int i=0; i<persons.length; i++)
			assertEquals("Person nr: " + i, locations[i], persons[i].getLocation());
	}
	

	
	@Test
	public void allPersonsLocations() {
		checkLocations(testFamily.expectedLocations, testFamily.persons);
	}
	
	@Test
	public void spouseIndentation() {
		singleSpouseIndentation(testFamily.grandfather, testFamily.grandmother);
		singleSpouseIndentation(testFamily.daughter, testFamily.daughtersHusband);
		singleSpouseIndentation(testFamily.son, testFamily.sonsWife);
		singleSpouseIndentation(testFamily.granddaughter1, testFamily.granddaughter1Husband);
		singleSpouseIndentation(testFamily.granddaughter3, testFamily.granddaughter3Husband);
	}
	
	private void singleSpouseIndentation(Person person, Person spouse) {
		int indentation = 20;
		
		Point personLocation = testFamily.person(person).getLocation();
		Point spouseLocation = testFamily.person(spouse).getLocation();
		assertEquals(personLocation.getX() + indentation, spouseLocation.getX());
	}
	
	@Test
	public void moveFamilyTest() {
		for (int i=0; i<testFamily.expectedLocations.length; i++)
			testFamily.expectedLocations[i] = testFamily.expectedLocations[i].addVector(100, 50);
		
		testFamily.family.move(100, 50);
		
		checkLocations(testFamily.expectedLocations, testFamily.persons);
	}

}
