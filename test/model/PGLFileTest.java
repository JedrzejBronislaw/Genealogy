package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public abstract class PGLFileTest {
	private static final String fileName = "tree.pgl";

	@Rule
	public TemporaryFolder tempfolder = new TemporaryFolder();
	protected File file;

	protected void createPGLFile() {
		try {
			file = tempfolder.newFile(fileName);
			FileWriter writer = new FileWriter(file);
			writer.write(content());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Tree loadTreeFromFile() {
		PGLFile pglFile = null;
		Tree tree = new Tree();

		try {
			pglFile = new PGLFile(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		pglFile.load(tree);
		
		return tree;
	}
	
	protected abstract String content();
	
}
