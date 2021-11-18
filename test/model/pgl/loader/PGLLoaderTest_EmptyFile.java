package model.pgl.loader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.pgl.PGL;

public class PGLLoaderTest_EmptyFile {
	
	static String content = "";

	private static PGL pgl;
	
	
	@BeforeClass
	public static void prepare() {
		pgl = new TempFile(content).loadPGL();
	}

	
	@Test
	public void shouldReturnSize0WhenFileIsEmpty() {
		assertEquals(0, pgl.size());
	}
}
