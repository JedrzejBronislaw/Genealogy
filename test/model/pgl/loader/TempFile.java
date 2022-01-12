package model.pgl.loader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.Tree;
import model.pgl.PGL;

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
	
	public PGL loadPGL() {
		PGLLoader pglLoader = new PGLLoader(file.getAbsolutePath());
		
		try {
			return pglLoader.load();
		} catch (IOException e) {
			return null;
		}
	}
	
	public Tree loadTree() {
		Tree tree = new Tree();
		PGLLoader pglLoader = new PGLLoader(file.getAbsolutePath());
		TreeLoader treeLoader = new TreeLoader(pglLoader);

		treeLoader.load(tree);
		
		return tree;
	}
	
}
