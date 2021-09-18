package model.pgl.saver;

import java.io.File;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Tree;

@RequiredArgsConstructor
public class TreeSaver {
	
	@NonNull
	private File file;
	
	public boolean save(Tree tree) {
		try {
			if (tree == null) return false;
			
			new PGLSaver(file).save(new TreeToPGLMapper().map(tree));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
