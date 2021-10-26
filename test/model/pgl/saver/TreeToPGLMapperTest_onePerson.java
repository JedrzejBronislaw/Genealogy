package model.pgl.saver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.LifeStatus;
import model.MyDate;
import model.Person;
import model.Sex;
import model.Tree;
import model.pgl.PGLFields;
import model.pgl.saver.TreeToPGLMapper;
import model.pgl.PGL;

public class TreeToPGLMapperTest_onePerson {

	private static final String ID = "1";

	private static PGL pgl;
	
	private static Tree tree = new Tree();
	
	private static MyDate birthDate = new MyDate(2, 3, 1904);
	private static MyDate deathDate = new MyDate(5, 6, 2007);
	
	@BeforeClass
	public static void prepare() {
		TreeToPGLMapper writer = new TreeToPGLMapper();
		pgl = writer.map(prepareTree());
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
		assertTrue(pgl.getSection(PGLFields.mainSectionName).isPresent());
		assertEquals("1", pgl.getValue(PGLFields.mainSectionName, PGLFields.numberOfPersons));
	}
	
	@Test
	public void testFirstName() {
		assertEquals("John", pgl.getValue(ID, PGLFields.firstName));
	}
	
	@Test
	public void testLastName() {
		assertEquals("Smith", pgl.getValue(ID, PGLFields.lastName));
	}
	
	@Test
	public void testAlias() {
		assertEquals("JS", pgl.getValue(ID, PGLFields.alias));
	}
	
	@Test
	public void testBirthDate() {
		assertEquals(birthDate, new MyDate(pgl.getValue(ID, PGLFields.birthDate)));
	}
	
	@Test
	public void testBirthPlace() {
		assertEquals("birthPlace", pgl.getValue(ID, PGLFields.birthPlace));
	}
	
	@Test
	public void testBaptismParish() {
		assertEquals("baptismParish", pgl.getValue(ID, PGLFields.baptismParish));
	}
	
	@Test
	public void testDeathDate() {
		assertEquals(deathDate, new MyDate(pgl.getValue(ID, PGLFields.deathDate)));
	}
	
	@Test
	public void testDeathPlace() {
		assertEquals("deathPlace", pgl.getValue(ID, PGLFields.deathPlace));
	}
	
	@Test
	public void testBurialPlace() {
		assertEquals("burialPlace", pgl.getValue(ID, PGLFields.burialPlace));
	}
	
	@Test
	public void testComments() {
		assertEquals("comments" + PGLFields.lineSeparator + "line2", pgl.getValue(ID, PGLFields.comments));
	}
	
	@Test
	public void testContact() {
		assertEquals("contact", pgl.getValue(ID, PGLFields.contact));
	}
	
	@Test
	public void testLifeStatus() {
		assertEquals(LifeStatus.NO.toString(), pgl.getValue(ID, PGLFields.lifeStatus));
	}
	
	@Test
	public void testSex() {
		assertEquals(Sex.MAN.toString(), pgl.getValue(ID, PGLFields.sex));
	}

	
}
