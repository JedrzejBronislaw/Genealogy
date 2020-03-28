package fxmlBuilders.session;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import model.Tree;

public class Session {

	public interface NewTreeListener {
		void run(Tree newTree);
	}
	
	@Getter
	private Tree tree;
	
	private List<NewTreeListener> newTreeListeners = new ArrayList<>();
	
	public void setTree(Tree tree) {
		this.tree = tree;
		newTreeListeners.forEach(l -> l.run(tree));
	}
	
	public void addNewTreeListener(NewTreeListener listener) {
		newTreeListeners.add(listener);
	}
}
