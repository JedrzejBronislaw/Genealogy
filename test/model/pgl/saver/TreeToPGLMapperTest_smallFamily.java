package model.pgl.saver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.MyDate;
import model.Person;
import model.Sex;
import model.Tree;
import model.familyRelations.RelationEditor;
import model.pgl.PGLFields;
import model.pgl.saver.TreeToPGLMapper;
import model.pgl.PGL;
import model.random.RandomPerson;

public class TreeToPGLMapperTest_smallFamily {

	private static final String GM_ID = "1";
	private static final String GF_ID = "2";
	private static final String M_ID  = "3";
	private static final String F_ID  = "4";
	private static final String H2_ID = "5";
	private static final String C1_ID = "6";
	private static final String C2_ID = "7";

	private static PGL pgl;
	
	private static Tree tree = new Tree();
	
	private static String marriageDate = new MyDate(2, 3, 1904).toString();
	
	@BeforeClass
	public static void prepare() {
		TreeToPGLMapper writer = new TreeToPGLMapper();
		pgl = writer.map(prepareTree());
	}
	
	private static Tree prepareTree() {
		RandomPerson rPerson = new RandomPerson();
		RelationEditor relationEditor = new RelationEditor();
		
		Person grandMother = rPerson.generate(Sex.WOMAN);
		Person grandFather = rPerson.generate(Sex.MAN);
		
		Person mother = rPerson.generate(Sex.WOMAN);
		Person father = rPerson.generate(Sex.MAN);
		Person secHusband = rPerson.generate(Sex.MAN);

		Person child1 = rPerson.generate();
		Person child2 = rPerson.generate();
		
		relationEditor.setMotherChildRel(grandMother, father);
		relationEditor.setFatherChildRel(grandFather, mother);
		relationEditor.setParentsChildRel(mother, father, child1);
		relationEditor.setParentsChildRel(mother, father, child2);
		
		relationEditor.createMarriageRel(mother, father, marriageDate, "place");
		relationEditor.createMarriageRel(mother, secHusband, "", "place2");
		
		tree.addPerson(GM_ID, grandMother);
		tree.addPerson(GF_ID, grandFather);
		tree.addPerson(M_ID,  mother);
		tree.addPerson(F_ID,  father);
		tree.addPerson(H2_ID, secHusband);
		tree.addPerson(C1_ID, child1);
		tree.addPerson(C2_ID, child2);
		
		return tree;
	}
	
	@Test
	public void testMainSection() {
		assertTrue(pgl.get(PGLFields.mainSectionName).isPresent());
		assertEquals("7", pgl.getValue(PGLFields.mainSectionName, PGLFields.numberOfPersons));
	}
	
	@Test
	public void testMothers() {
		assertNull(pgl.getValue(GM_ID, PGLFields.mother));
		assertNull(pgl.getValue(GF_ID, PGLFields.mother));
		assertNull(pgl.getValue(M_ID,  PGLFields.mother));
		assertNull(pgl.getValue(H2_ID, PGLFields.mother));
		
		assertEquals(GM_ID, pgl.getValue(F_ID,  PGLFields.mother));
		assertEquals(M_ID,  pgl.getValue(C1_ID, PGLFields.mother));
		assertEquals(M_ID,  pgl.getValue(C2_ID, PGLFields.mother));
	}
	
	@Test
	public void testFathers() {
		assertNull(pgl.getValue(GM_ID, PGLFields.father));
		assertNull(pgl.getValue(GF_ID, PGLFields.father));
		assertNull(pgl.getValue(F_ID,  PGLFields.father));
		assertNull(pgl.getValue(H2_ID, PGLFields.father));
		
		assertEquals(GF_ID, pgl.getValue(M_ID,  PGLFields.father));
		assertEquals(F_ID,  pgl.getValue(C1_ID, PGLFields.father));
		assertEquals(F_ID,  pgl.getValue(C2_ID, PGLFields.father));
	}
	
	@Test
	public void testChildren() {
		assertEquals("1", pgl.getValue(GM_ID, PGLFields.children));
		assertEquals("1", pgl.getValue(GF_ID, PGLFields.children));
		assertEquals("2", pgl.getValue(F_ID,  PGLFields.children));
		assertEquals("2", pgl.getValue(M_ID,  PGLFields.children));
		assertNull(pgl.getValue(H2_ID, PGLFields.children));
		assertNull(pgl.getValue(C1_ID, PGLFields.children));
		assertNull(pgl.getValue(C2_ID, PGLFields.children));
		
		assertEquals(F_ID,  pgl.getValue(GM_ID, PGLFields.child(1)));
		assertEquals(M_ID,  pgl.getValue(GF_ID, PGLFields.child(1)));
		assertEquals(C1_ID, pgl.getValue(M_ID,  PGLFields.child(1)));
		assertEquals(C2_ID, pgl.getValue(M_ID,  PGLFields.child(2)));
		assertEquals(C1_ID, pgl.getValue(F_ID,  PGLFields.child(1)));
		assertEquals(C2_ID, pgl.getValue(F_ID,  PGLFields.child(2)));
	}

	@Test
	public void testMarriages() {
		assertEquals("2", pgl.getValue(M_ID,  PGLFields.marriages));
		assertEquals("1", pgl.getValue(F_ID,  PGLFields.marriages));
		assertEquals("1", pgl.getValue(H2_ID, PGLFields.marriages));
		assertNull(pgl.getValue(GM_ID, PGLFields.marriages));
		assertNull(pgl.getValue(GF_ID, PGLFields.marriages));
		assertNull(pgl.getValue(C1_ID, PGLFields.marriages));
		assertNull(pgl.getValue(C2_ID, PGLFields.marriages));

		assertEquals(F_ID,  pgl.getValue(M_ID,  PGLFields.spouse(1)));
		assertEquals(M_ID,  pgl.getValue(F_ID,  PGLFields.spouse(1)));
		assertEquals(H2_ID, pgl.getValue(M_ID,  PGLFields.spouse(2)));
		assertEquals(M_ID,  pgl.getValue(H2_ID, PGLFields.spouse(1)));

		assertEquals(marriageDate,  pgl.getValue(M_ID, PGLFields.weddingDate(1)));
		assertEquals(marriageDate,  pgl.getValue(F_ID, PGLFields.weddingDate(1)));
		assertNull(pgl.getValue(M_ID,  PGLFields.weddingDate(2)));
		assertNull(pgl.getValue(H2_ID, PGLFields.weddingDate(1)));

		assertEquals("place",  pgl.getValue(M_ID,  PGLFields.weddingPlace(1)));
		assertEquals("place",  pgl.getValue(F_ID,  PGLFields.weddingPlace(1)));
		assertEquals("place2", pgl.getValue(M_ID,  PGLFields.weddingPlace(2)));
		assertEquals("place2", pgl.getValue(H2_ID, PGLFields.weddingPlace(1)));
	}
}
