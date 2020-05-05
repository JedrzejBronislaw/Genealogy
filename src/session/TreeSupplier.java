package session;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import model.Tree;
import tools.Injection;

public class TreeSupplier {
	private Tree tree = null;
	private List<Consumer<Tree>> listeners = new ArrayList<>();

	public Tree get() {
		return tree;
	}
	
	public void setTree(Tree tree) {
		this.tree = tree;
		for(Consumer<Tree> listener : listeners)
			Injection.run(listener, tree);
	}
	
	public void addListener(Consumer<Tree> listener) {
		listeners.add(listener);
	}
}
