package treeGraphs.painter.nameDisplayers.markers;

import lombok.Setter;
import model.Person;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;

abstract public class NameMarker {
	
	@Setter
	protected Displayer displayer;
	@Setter
	protected Painter painter;
	
	public Handle print(Person person, int x, int y) {
		if (displayer == null ||
			painter   == null ||
			!check(person))
			return null;
			
		prepare(person);
		Handle handle = displayer.display(person, x, y);
		clean();
		
		return handle;
	}
	
	abstract public boolean check(Person person);
	abstract protected void prepare(Person person);
	abstract protected void clean();
}
