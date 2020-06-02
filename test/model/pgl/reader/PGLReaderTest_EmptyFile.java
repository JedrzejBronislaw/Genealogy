package model.pgl.reader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Tree;

public class PGLReaderTest_EmptyFile {
	
	static PGLFilePreparation preparation = new PGLFilePreparation("emptyFile");
	static String content = "";

	private static Tree tree;
	
	@BeforeClass
	public static void x() {
		preparation.createPGLFile(content);
		tree = preparation.loadTreeFromFile();
	}

	
	@Test
	public void test() {
		assertEquals(0, tree.getAll().length);
	}

}
