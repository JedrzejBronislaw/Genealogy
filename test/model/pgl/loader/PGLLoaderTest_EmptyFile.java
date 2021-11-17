package model.pgl.loader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Tree;

public class PGLLoaderTest_EmptyFile {
	
	static TempFile tempFile = new TempFile("emptyFile");
	static String content = "";

	private static Tree tree;
	
	@BeforeClass
	public static void x() {
		tempFile.createPGLFile(content);
		tree = tempFile.loadTreeFromFile();
	}

	
	@Test
	public void test() {
		assertEquals(0, tree.getAll().length);
	}

}
