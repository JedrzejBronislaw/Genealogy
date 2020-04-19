package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PGLFileTest_EmptyFile extends PGLFileTest {
	
	@Override
	protected String content() {
		return "";
	}
	
	@Test
	public void test() {
		createPGLFile();
		Tree tree = loadTreeFromFile();
		
		assertEquals(0, tree.getAll().length);
	}

}
