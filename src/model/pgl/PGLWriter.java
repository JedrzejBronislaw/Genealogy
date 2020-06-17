package model.pgl;

import java.io.File;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Tree;

@RequiredArgsConstructor
public class PGLWriter {
	
	@NonNull
	private File file;
	
	public boolean save(Tree tree) {
		try {
			if (tree == null) return false;
			
			new VirtualPGLtoFileWriter(file).save(new VirtualPGLWriter().write(tree));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
