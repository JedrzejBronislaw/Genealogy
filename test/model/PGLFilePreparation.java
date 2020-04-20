package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PGLFilePreparation {
	
	@NonNull private String fileName;
	protected File file;

	protected void createPGLFile(String content) {
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
	
}
