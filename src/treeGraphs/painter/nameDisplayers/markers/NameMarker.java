package treeGraphs.painter.nameDisplayers.markers;

import java.util.function.Supplier;

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
		return execute(person, () -> displayer.display(person, x, y));
	}
	
	public <T> T get(Person person, Supplier<T> supplier) {
		return execute(person, supplier);
	}
	
	private <T> T execute(Person person, Supplier<T> supplier) {
		if (displayer == null ||
			painter   == null ||
			supplier  == null ||
			!check(person))
			return null;
			
		prepare(person);
		T outcom = supplier.get();
		clean();
		
		return outcom;
	}
	
	abstract public boolean check(Person person);
	abstract protected void prepare(Person person);
	abstract protected void clean();
}
