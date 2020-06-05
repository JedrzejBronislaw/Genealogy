package treeGraphs;

import java.awt.Dimension;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import model.Person;
import tools.Injection;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;
import treeGraphs.painter.nameDisplayers.NameDisplayer;
import treeGraphs.painter.nameDisplayers.SimpleNameDisplayer;

public abstract class TreeGraph {

	@Setter @Getter
	protected Person mainPerson;
	@Getter protected int height;
	@Getter protected int width;

	@Getter
	protected NameDisplayer nameDisplayer = new SimpleNameDisplayer();
	protected Painter painter;
	
	public abstract void draw();

	@Setter
	protected Consumer<Person> personDoubleClickAction;
	@Setter
	protected Consumer<Person> personSingleClickAction;
	
	public void setPainter(Painter painter) {
		this.painter = painter;
		nameDisplayer.setPainter(painter);
	}
	public void setNameDisplayer(NameDisplayer nameDisplayer) {
		this.nameDisplayer = nameDisplayer;
		nameDisplayer.setPainter(painter);
	}
	
	public Dimension getDimension() {
		return new Dimension(width, height);
	}

	protected void setHandleEvents(Handle handle, Person person) {
		if (handle == null || person == null) return;
		
		handle.setOnMouseSingleClick(() -> Injection.run(personSingleClickAction, person));
		handle.setOnMouseDoubleClick(() -> Injection.run(personDoubleClickAction, person));
	}
	
	protected int draw(Person person, int x, int y) {
		int nameHeight = nameDisplayer.getHeight(person);
		Handle handle  = nameDisplayer.print(person, x, y+nameHeight);
		setHandleEvents(handle, person);
		
		return nameHeight;
	}
}