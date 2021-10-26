package model.pgl.saver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Tree;
import model.pgl.PGLFields;
import model.pgl.saver.TreeToPGLMapper;
import model.pgl.PGL;

public class TreeToPGLMapperTest_empty {

	private static PGL pgl;
	
	@BeforeClass
	public static void prepare() {
		TreeToPGLMapper writer = new TreeToPGLMapper();
		pgl = writer.map(new Tree());
	}
	
	@Test
	public void testMainSection() {
		assertTrue(pgl.getSection(PGLFields.mainSectionName).isPresent());
	}
	
	@Test
	public void testNumOfSections() {
		assertEquals(1, pgl.size());
	}
}
