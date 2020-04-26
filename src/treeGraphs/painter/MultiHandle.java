package treeGraphs.painter;

public class MultiHandle extends Handle {

	private Handle[] handles;

	public MultiHandle(Handle... handles) {
		this.handles = handles;
	}
	
	@Override
	public void setOnMouseClick(Runnable action) {
		for (Handle handle : handles) handle.setOnMouseClick(action);
	}
}
