package model.pgl.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Tree;
import model.pgl.loader.PGLLoader;

@RequiredArgsConstructor
public class PGLFilePreparation {
	
	@NonNull private String fileName;
	protected File file;

	public void createPGLFile(String content) {
		try {
			file = File.createTempFile(fileName, ".pgl");
			file.deleteOnExit();
			
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Tree loadTreeFromFile() {
		PGLLoader pglReader = null;
		Tree tree = new Tree();

		try {
			pglReader = new PGLLoader(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		pglReader.load(tree);
		
		return tree;
	}
	
}
