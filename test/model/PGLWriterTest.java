package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import model.Person.LifeStatus;
import model.Person.Sex;

public class PGLWriterTest {

	public Tree prepareTree() {
		Tree tree = new Tree();
		Person father = prepareFather();
		Person mother = prepareMother();
		Person child = prepareChild();
		
		father.addMarriage(mother);
		father.addChild(child);
		mother.addMarriage(father);
		mother.addChild(child);
		child.setFather(father);
		child.setMother(mother);
		
		tree.addPerson("1", father);
		tree.addPerson("2", mother);
		tree.addPerson("3", child);
		
		tree.addCommonSurname("Kowalski");
		tree.addCommonSurname("Nowak");
		tree.addCommonSurname("Kowal");
		
		tree.setLastOpen(tryLoadDate("11:32:16 01.04.2020", new SimpleDateFormat("hh:mm:ss dd.MM.yyyy")));
		tree.setLastModification(tryLoadDate("08:12:55 06.05.2019", new SimpleDateFormat("hh:mm:ss dd.MM.yyyy")));
		
		return tree;
	}

	private Person prepareFather() {
		Person person = new Person();
		
		person.setFirstName("Adam");
		person.setLastName("Kowalski");
		person.setAlias("Daniel");
		person.setLifeStatus(LifeStatus.NO);
		person.setSex(Sex.MAN);
		person.setBirthDate(new MyDate("1.2.1908"));
		person.setDeathDate(new MyDate("3.4.2010"));
		person.setBirthPlace("Poznan");
		person.setDeathPlace("Krakow");
		person.setContact("contact");
		person.setComments("comments");
		person.setBaptismParish("parish");
		person.setBurialPlace("cemetery");
		
		return person;
	}

	private Person prepareMother() {
		Person person = new Person();
		
		person.setFirstName("Ewa");
		person.setLastName("Nowak");
		person.setSex(Sex.WOMAN);
		
		return person;
	}

	private Person prepareChild() {
		Person person = new Person();
		
		person.setFirstName("Robert");
		person.setLastName("Kowalski");
		person.setSex(Sex.MAN);
		
		return person;
	}
	
	private Date tryLoadDate(String textDate, SimpleDateFormat format) {
		Date date;
		try {
			date = format.parse(textDate);
		} catch (ParseException e) {
			date = null;
		}
		
		return date;
	}
	
	private Tree saveAndLoadTree(Tree tree) {
		File file = null;
		Tree treeFromFile = new Tree();
		PGLWriter writer;
		PGLFile pglFile = null;
		
		try {
			file = File.createTempFile("pglWriterTest", ".pgl");
			file.deleteOnExit();
		} catch (IOException e) {
			fail("Creation file error");
		}
		
		
		writer = new PGLWriter(file);
		if (!writer.save(tree)) fail("Saving file error");
		
		
		try {
			pglFile = new PGLFile(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			fail("Opening file error");
		}
		
		if (!pglFile.load(treeFromFile)) fail("Loading file error");
		
		return treeFromFile;
	}

	@Test
	public void equalsTree() {
		Tree tree = prepareTree();
		Tree treeFromFile = saveAndLoadTree(tree);

		assertEquals(tree, treeFromFile);
	}

	@Test
	public void numberOfPersons() {
		Tree tree = prepareTree();
		Tree treeFromFile = saveAndLoadTree(tree);
		
		assertEquals(tree.numberOfPersons(), treeFromFile.numberOfPersons());
	}

	@Test
	public void commonSurnames() {
		Tree tree = prepareTree();
		Tree treeFromFile = saveAndLoadTree(tree);
		
		assertArrayEquals(tree.getCommonSurnames(), treeFromFile.getCommonSurnames());
	}

	@Test
	public void lastOpenDate() {
		Tree tree = prepareTree();
		Tree treeFromFile = saveAndLoadTree(tree);
		
		assertEquals(tree.getLastOpen(), treeFromFile.getLastOpen());
	}

	@Test
	public void lastModificationDate() {
		Tree tree = prepareTree();
		Tree treeFromFile = saveAndLoadTree(tree);
		
		assertEquals(tree.getLastModification(), treeFromFile.getLastModification());
	}

	@Test
	public void equalsEachPerson() {
		Tree tree = prepareTree();
		Tree treeFromFile = saveAndLoadTree(tree);

		String[] ids = tree.getIDs();
		
		List.of(ids).forEach(id ->
			assertEquals("id="+id, tree.getPerson(id), treeFromFile.getPerson(id))
		);
	}
}
