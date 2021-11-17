package model.pgl.loader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Tree;

public class TreeLoaderTest_EmptyFile {
	
	static String content = "";

	private static Tree tree;
	
	
	@BeforeClass
	public static void prepare() {
		tree = new TempFile(content).loadTree();
	}

	
	@Test
	public void test() {
		assertEquals(0, tree.getAll().length);
	}
}
