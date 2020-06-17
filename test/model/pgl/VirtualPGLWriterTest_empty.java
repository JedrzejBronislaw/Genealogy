package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Tree;
import model.pgl.virtual.VirtualPGL;

public class VirtualPGLWriterTest_empty {

	private static VirtualPGL virtualPGL;
	
	@BeforeClass
	public static void prepare() {
		VirtualPGLWriter writer = new VirtualPGLWriter();
		virtualPGL = writer.write(new Tree());
	}
	
	@Test
	public void testMainSection() {
		assertTrue(virtualPGL.get("Main").isPresent());
	}
	
	@Test
	public void testNumOfSections() {
		assertEquals(1, virtualPGL.size());
	}
}
