package treeGraphs.painter;

import java.util.LinkedList;
import java.util.List;

public class MultiHandle extends Handle {

	private List<Handle> handles = new LinkedList<>();

	public MultiHandle(Handle... handles) {
		for (Handle handle : handles)
			this.handles.add(handle);
	}
	
	public void addHandle(Handle handle) {
		handles.add(handle);
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
