package treeGraphs;

import java.awt.Dimension;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import model.Person;
import nameDisplayers.NameDisplayer;
import nameDisplayers.SimpleNameDisplayer;
import tools.Injection;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Painter;

public abstract class TreeGraph {

	@Setter @Getter
	protected Person mainPerson;
	protected int height;
	protected int width;

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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	protected void setHandleEvents(Handle handle, Person person) {
		handle.setOnMouseSingleClick(() -> Injection.run(personSingleClickAction, person));
		handle.setOnMouseDoubleClick(() -> Injection.run(personDoubleClickAction, person));
	}
}