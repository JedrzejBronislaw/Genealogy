package model.pgl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import model.MyDate;
import model.Person;
import model.Tree;
import model.Person.LifeStatus;
import model.Person.Sex;
import model.familyRelations.RelationEditor;
import model.pgl.PGLWriter;
import model.pgl.reader.PGLReader;

public class PGLWriterTest {

	public Tree prepareTree() {
		Tree tree = new Tree();
		RelationEditor relationEditor = new RelationEditor(tree);
		
		Person father = prepareFather();
		Person mother = prepareMother();
		Person child  = prepareChild();
		
		relationEditor.createMarriageRel(father, mother);
		relationEditor.setParentsChildRel(father, mother, child);
		
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
		person.setContact("contact" + System.lineSeparator() + "line 2");
		person.setComments("comments" + System.lineSeparator() + "line 2");
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
		PGLReader pglFile = null;
		
		try {
			file = File.createTempFile("pglWriterTest", ".pgl");
			file.deleteOnExit();
		} catch (IOException e) {
			fail("Creation file error");
		}
		
		
		writer = new PGLWriter(file);
		if (!writer.save(tree)) fail("Saving file error");
		
		
		try {
			pglFile = new PGLReader(file.getAbsolutePath());
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

	@Test
	public void relations() {
		Tree treeFromFile = saveAndLoadTree(prepareTree());

		Person father = treeFromFile.getPerson("0");
		Person mother = treeFromFile.getPerson("1");
		Person child  = treeFromFile.getPerson("2");
		
		assertEquals(1, father.numberOfMarriages());
		assertEquals(1, mother.numberOfMarriages());
		assertEquals(0, child.numberOfMarriages());
		
		assertEquals(mother, father.getSpouse(0));
		assertEquals(father, mother.getSpouse(0));

		assertEquals(1, father.numberOfChildren());
		assertEquals(1, mother.numberOfChildren());
		assertEquals(0, child.numberOfChildren());
		
		assertEquals(father, child.getFather());
		assertEquals(mother, child.getMother());
		assertNull(father.getFather());
		assertNull(father.getMother());
		assertNull(mother.getFather());
		assertNull(mother.getMother());
	}
}
