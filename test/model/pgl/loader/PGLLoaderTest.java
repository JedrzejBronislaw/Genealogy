package model.pgl.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;

import model.pgl.PGL;

public class PGLLoaderTest {
	
	private File createFile(String content) {
		File file = null;
		
		try {
			file = File.createTempFile("file", ".txt");
			file.deleteOnExit();
			
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	private PGLLoader createPGLLoader(File file) {
		return new PGLLoader(file.getAbsolutePath());
	}

	
	@Test
	public void shouldLoadFileWhenFileIsEmpty() throws IOException {
		// given
		File file = createFile("");
		PGLLoader pglLoader = createPGLLoader(file);

		// when
		PGL pgl = pglLoader.load();
		
		// then
		assertNotNull(pgl);
	}
	
	@Test
	public void shouldLoadFile() throws IOException {
		// given
		File file = createFile("[1]");
		PGLLoader pglLoader = createPGLLoader(file);

		// when
		PGL pgl = pglLoader.load();
		
		// then
		assertEquals(1, pgl.size());
	}
	
	@Test
	public void shouldLoadFileTwice() throws IOException {
		// given
		File file = createFile("[1]");
		PGLLoader pglLoader = createPGLLoader(file);
		pglLoader.load();

		// when
		PGL pgl = pglLoader.load();
		
		// then
		assertNotNull(pgl);
	}
	
	@Test
	public void shouldSecondLoadedFileBeEqualFirst() throws IOException {
		// given
		File file = createFile("[1]\na=1\nb=2\n[2]\nc=3");
		PGLLoader pglLoader = createPGLLoader(file);

		// when
		PGL pgl1 = pglLoader.load();
		PGL pgl2 = pglLoader.load();
		
		// then
		assertEquals(2, pgl1.size());
		assertEquals(2, pgl1.getSection("1").get().size());
		assertEquals(1, pgl1.getSection("2").get().size());
		assertEquals("2", pgl1.getValue("1", "b"));
		assertEquals(2, pgl2.size());
		assertEquals(2, pgl2.getSection("1").get().size());
		assertEquals(1, pgl2.getSection("2").get().size());
		assertEquals("2", pgl2.getValue("1", "b"));
	}
	
	@Test
	public void shouldSecondLoadedFileBeOtherObjectThanFirst() throws IOException {
		// given
		File file = createFile("[1]");
		PGLLoader pglLoader = createPGLLoader(file);

		// when
		PGL pgl1 = pglLoader.load();
		PGL pgl2 = pglLoader.load();
		
		// then
		assertNotSame(pgl1, pgl2);
	}
	
	@Test(expected = FileNotFoundException.class)
	public void shouldThrowExceptionWhenPathIsWrong() throws IOException {
		// given
		PGLLoader pglLoader = new PGLLoader("wrongPath");

		// when
		// then
		pglLoader.load();
	}
}
