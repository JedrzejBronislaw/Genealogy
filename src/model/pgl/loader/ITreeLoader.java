package model.pgl.loader;

import model.Tree;
import model.pgl.comparator.PGLDiffReport;

public interface ITreeLoader {
	
	boolean load(Tree tree);
	PGLDiffReport loadAndAnalize(Tree tree);
}
