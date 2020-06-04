package treeGraphs.painter.nameDisplayers.markers;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
		return execute(person,
				marker -> marker.print(person, x, y),
				() -> displayer.display(person, x, y));
	}

	public <T> T get(Person person, Supplier<T> supplier) {
		return execute(person,
				marker -> marker.get(person, supplier),
				supplier);
	}
	
	private <T> T execute(Person person, Function<NameMarker,T> withMarker, Supplier<T> withoutMarker) {
		if  (person == null) return null;
		
		T outcome = null;
		
		for (NameMarker marker : markers)
			if (marker.check(person)) {
				outcome = withMarker.apply(marker);
				break;
			}
		
		if (outcome == null)
			outcome = withoutMarker.get();
		
		return outcome;
	}
}
