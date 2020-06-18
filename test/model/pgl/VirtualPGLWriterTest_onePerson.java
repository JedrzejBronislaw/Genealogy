package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.MyDate;
import model.Person;
import model.Person.LifeStatus;
import model.Person.Sex;
import model.Tree;
import model.pgl.virtual.VirtualPGL;

public class VirtualPGLWriterTest_onePerson {

	private static final String ID = "1";

	private static VirtualPGL virtualPGL;
	
	private static Tree tree = new Tree();
	
	private static MyDate birthDate = new MyDate(2, 3, 1904);
	private static MyDate deathDate = new MyDate(5, 6, 2007);
	
	@BeforeClass
	public static void prepare() {
		VirtualPGLWriter writer = new VirtualPGLWriter();
		virtualPGL = writer.write(prepareTree());
	}
	
	private static Tree prepareTree() {
		
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Smith");
		person.setAlias("JS");
		person.setBirthDate(birthDate);
		person.setBirthPlace("birthPlace");
		person.setBaptismParish("baptismParish");
		person.setDeathDate(deathDate);
		person.setDeathPlace("deathPlace");
		person.setBurialPlace("burialPlace");
		
		person.setComments("comments" + System.lineSeparator() + "line2");
		person.setContact("contact");
		
		person.setLifeStatus(LifeStatus.NO);
		person.setSex(Sex.MAN);
		
		tree.addPerson(ID, person);
		
		return tree;
	}
	
	@Test
	public void testMainSection() {
		assertTrue(virtualPGL.get(PGLFields.mainSectionName).isPresent());
		assertEquals("1", virtualPGL.getValue(PGLFields.mainSectionName, PGLFields.numberOfPersons));
	}
	
	@Test
	public void testFirstName() {
		assertEquals("John", virtualPGL.getValue(ID, PGLFields.firstName));
	}
	
	@Test
	public void testLastName() {
		assertEquals("Smith", virtualPGL.getValue(ID, PGLFields.lastName));
	}
	
	@Test
	public void testAlias() {
		assertEquals("JS", virtualPGL.getValue(ID, PGLFields.alias));
	}
	
	@Test
	public void testBirthDate() {
		assertEquals(birthDate, new MyDate(virtualPGL.getValue(ID, PGLFields.birthDate)));
	}
	
	@Test
	public void testBirthPlace() {
		assertEquals("birthPlace", virtualPGL.getValue(ID, PGLFields.birthPlace));
	}
	
	@Test
	public void testBaptismParish() {
		assertEquals("baptismParish", virtualPGL.getValue(ID, PGLFields.baptismParish));
	}
	
	@Test
	public void testDeathDate() {
		assertEquals(deathDate, new MyDate(virtualPGL.getValue(ID, PGLFields.deathDate)));
	}
	
	@Test
	public void testDeathPlace() {
		assertEquals("deathPlace", virtualPGL.getValue(ID, PGLFields.deathPlace));
	}
	
	@Test
	public void testBurialPlace() {
		assertEquals("burialPlace", virtualPGL.getValue(ID, PGLFields.burialPlace));
	}
	
	@Test
	public void testComments() {
		assertEquals("comments" + PGLFields.lineSeparator + "line2", virtualPGL.getValue(ID, PGLFields.comments));
	}
	
	@Test
	public void testContact() {
		assertEquals("contact", virtualPGL.getValue(ID, PGLFields.contact));
	}
	
	@Test
	public void testLifeStatus() {
		assertEquals("0", virtualPGL.getValue(ID, PGLFields.lifeStatus));
	}
	
	@Test
	public void testSex() {
		assertEquals("1", virtualPGL.getValue(ID, PGLFields.sex));
	}

	
}
