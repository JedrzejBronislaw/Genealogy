package treeGraphs.painter.nameDisplayers.markers;

import java.util.LinkedList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;

@RequiredArgsConstructor
public class MarkerList {

	private Painter painter;
	private final Displayer displayer;
	protected List<NameMarker> markers = new LinkedList<>();
	
	public void setPainter(Painter painter) {
		this.painter = painter;
		markers.forEach(marker -> marker.setPainter(painter));
	}
	
	public void add(NameMarker marker) {
		marker.setPainter(painter);
		marker.setDisplayer(displayer);
		markers.add(marker);
	}
	
	public Handle print(Person person, int x, int y) {
		if  (person == null) return null;
		
		Handle handle = null;
		
		for (NameMarker marker : markers)
			if (marker.check(person)) {
				handle = marker.print(person, x, y);
				break;
			}
		
		if (handle == null)
			handle = displayer.display(person, x, y);
		
		return handle;
	}
}
