package treeGraphs.painter;

public class MultiHandle extends Handle {

	private Handle[] handles;

	public MultiHandle(Handle... handles) {
		this.handles = handles;
	}

	@Override
	public void setOnMouseSingleClick(Runnable action) {
		for (Handle handle : handles) handle.setOnMouseSingleClick(action);
	}
	
	@Override
	public void setOnMouseDoubleClick(Runnable action) {
		for (Handle handle : handles) handle.setOnMouseDoubleClick(action);
	}
}
