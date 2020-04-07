package session;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import model.Tree;
import settings.Settings;

public class Session {

	public interface NewTreeListener {
		void run(Tree newTree);
	}
	
	@Getter
	private Settings settings;
	
	@Getter
	private Tree tree;
	
	public Session() {
		settings = new Settings();
		settings.load();
	}

	private List<NewTreeListener> newTreeListeners = new ArrayList<>();
	private List<Runnable> closeTreeListeners = new ArrayList<>();
	
	public void setTree(Tree tree) {
		this.tree = tree;
		if (tree != null)
			newTreeListeners.forEach(l -> l.run(tree));
		else
			closeTreeListeners.forEach(l -> l.run());
	}
	
	public void addNewTreeListener(NewTreeListener listener) {
		newTreeListeners.add(listener);
	}
	
	public void addCloseTreeListener(Runnable listener) {
		closeTreeListeners.add(listener);
	}
}
