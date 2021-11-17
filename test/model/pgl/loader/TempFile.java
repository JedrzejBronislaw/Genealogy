package model.pgl.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import model.Tree;

public class TempFile {
	
	protected File file;
	

	public TempFile(String content) {
		createFile(content);
	}
	
	
	private void createFile(String content) {
		try {
			file = File.createTempFile("file", ".txt");
			file.deleteOnExit();
			
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Tree loadTree() {
		PGLLoader pglLoader = null;
		TreeLoader treeLoader;
		Tree tree = new Tree();

		try {
			pglLoader = new PGLLoader(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		treeLoader = new TreeLoader(pglLoader);
		treeLoader.load(tree);
		
		return tree;
	}
	
}
