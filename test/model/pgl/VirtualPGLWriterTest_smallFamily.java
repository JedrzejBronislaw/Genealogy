package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.MyDate;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.familyRelations.RelationEditor;
import model.pgl.virtual.VirtualPGL;
import model.random.RandomPerson;

public class VirtualPGLWriterTest_smallFamily {

	private static final String GM_ID = "1";
	private static final String GF_ID = "2";
	private static final String M_ID  = "3";
	private static final String F_ID  = "4";
	private static final String H2_ID = "5";
	private static final String C1_ID = "6";
	private static final String C2_ID = "7";

	private static VirtualPGL virtualPGL;
	
	private static Tree tree = new Tree();
	
	private static String marriageDate = new MyDate(2, 3, 1904).toString();
	
	@BeforeClass
	public static void prepare() {
		VirtualPGLWriter writer = new VirtualPGLWriter();
		virtualPGL = writer.write(prepareTree());
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
		assertTrue(virtualPGL.get(PGLFields.mainSectionName).isPresent());
		assertEquals("7", virtualPGL.getValue(PGLFields.mainSectionName, "ile"));
	}
	
	@Test
	public void testMothers() {
		assertNull(virtualPGL.getValue(GM_ID, "matka"));
		assertNull(virtualPGL.getValue(GF_ID, "matka"));
		assertNull(virtualPGL.getValue(M_ID,  "matka"));
		assertNull(virtualPGL.getValue(H2_ID, "matka"));
		
		assertEquals(GM_ID, virtualPGL.getValue(F_ID,  "matka"));
		assertEquals(M_ID,  virtualPGL.getValue(C1_ID, "matka"));
		assertEquals(M_ID,  virtualPGL.getValue(C2_ID, "matka"));
	}
	
	@Test
	public void testFathers() {
		assertNull(virtualPGL.getValue(GM_ID, "ojciec"));
		assertNull(virtualPGL.getValue(GF_ID, "ojciec"));
		assertNull(virtualPGL.getValue(F_ID,  "ojciec"));
		assertNull(virtualPGL.getValue(H2_ID, "ojciec"));
		
		assertEquals(GF_ID, virtualPGL.getValue(M_ID,  "ojciec"));
		assertEquals(F_ID,  virtualPGL.getValue(C1_ID, "ojciec"));
		assertEquals(F_ID,  virtualPGL.getValue(C2_ID, "ojciec"));
	}
	
	@Test
	public void testChildren() {
		assertEquals("1", virtualPGL.getValue(GM_ID, "dzieci"));
		assertEquals("1", virtualPGL.getValue(GF_ID, "dzieci"));
		assertEquals("2", virtualPGL.getValue(F_ID,  "dzieci"));
		assertEquals("2", virtualPGL.getValue(M_ID,  "dzieci"));
		assertEquals("0", virtualPGL.getValue(H2_ID, "dzieci"));
		assertEquals("0", virtualPGL.getValue(C1_ID,  "dzieci"));
		assertEquals("0", virtualPGL.getValue(C2_ID,  "dzieci"));
		
		assertEquals(F_ID,  virtualPGL.getValue(GM_ID, "dziecko1"));
		assertEquals(M_ID,  virtualPGL.getValue(GF_ID, "dziecko1"));
		assertEquals(C1_ID, virtualPGL.getValue(M_ID,  "dziecko1"));
		assertEquals(C2_ID, virtualPGL.getValue(M_ID,  "dziecko2"));
		assertEquals(C1_ID, virtualPGL.getValue(F_ID,  "dziecko1"));
		assertEquals(C2_ID, virtualPGL.getValue(F_ID,  "dziecko2"));
	}

	@Test
	public void testMarriages() {
		assertEquals("0", virtualPGL.getValue(GM_ID, "malzenstwa"));
		assertEquals("0", virtualPGL.getValue(GF_ID, "malzenstwa"));
		assertEquals("2", virtualPGL.getValue(M_ID,  "malzenstwa"));
		assertEquals("1", virtualPGL.getValue(F_ID,  "malzenstwa"));
		assertEquals("1", virtualPGL.getValue(H2_ID, "malzenstwa"));
		assertEquals("0", virtualPGL.getValue(C1_ID, "malzenstwa"));
		assertEquals("0", virtualPGL.getValue(C2_ID, "malzenstwa"));

		assertEquals(F_ID,  virtualPGL.getValue(M_ID,  "malzonek1"));
		assertEquals(M_ID,  virtualPGL.getValue(F_ID,  "malzonek1"));
		assertEquals(H2_ID, virtualPGL.getValue(M_ID,  "malzonek2"));
		assertEquals(M_ID,  virtualPGL.getValue(H2_ID, "malzonek1"));

		assertEquals(marriageDate,  virtualPGL.getValue(M_ID, "malzdata1"));
		assertEquals(marriageDate,  virtualPGL.getValue(F_ID, "malzdata1"));
		assertNull(virtualPGL.getValue(M_ID,  "malzdata2"));
		assertNull(virtualPGL.getValue(H2_ID, "malzdata1"));

		assertEquals("place",  virtualPGL.getValue(M_ID,  "malzmjsc1"));
		assertEquals("place",  virtualPGL.getValue(F_ID,  "malzmjsc1"));
		assertEquals("place2", virtualPGL.getValue(M_ID,  "malzmjsc2"));
		assertEquals("place2", virtualPGL.getValue(H2_ID, "malzmjsc1"));
	}
}
