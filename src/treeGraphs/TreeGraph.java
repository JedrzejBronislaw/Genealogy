package treeGraphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import model.Person;
import nameDisplaying.Name;
import nameDisplaying.SimpleNameDisplaying;
import tools.Injection;
import treeGraphs.ClickMap.ClickArea;
import treeGraphs.painter.Painter;
import windows.CardScreen;
import windows.Window;

public abstract class TreeGraph {

	@Setter @Getter
	protected Person mainPerson;
	protected int height;
	protected int width;

	protected Name nameDisplay = new SimpleNameDisplaying();
	
	@Setter Window cardWindow = null;
	@Setter CardScreen card = null;
	
	protected ClickMap clickMap = new ClickMap();
	
	protected Painter painter;
	
	public abstract void draw();
	
	@Setter
	protected Consumer<Person> personClickAction;
	
	public void setPainter(Painter painter) {
		this.painter = painter;
		nameDisplay.setPainter(painter);
	}
	public void setNameDisplay(Name nameDisplay) {
		this.nameDisplay = nameDisplay;
		nameDisplay.setPainter(painter);
	}
	
	public void clik(int x, int y) {
		Person person = clickMap.whoIsThere(x, y);
		if (person != null)
		{
			if (cardWindow != null)
				cardWindow.setEkran(new CardScreen(person));
			else
			if (card != null)
				card.setOsoba(person);
			else
				Injection.run(personClickAction, person);
		}
	}
	
	protected void drawClickAreas(Graphics2D g)
	{
		int n = clickMap.size();
		ClickArea area;
		Color oldColor = g.getColor();
		Stroke oldStroke = g.getStroke();

		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1));
		
		for (int i=0; i<n; i++) {
			area = clickMap.getArea(i);
			g.drawRect(area.left, area.top, area.right-area.left, area.bottom-area.top);
		}

		g.setColor(oldColor);
		g.setStroke(oldStroke);
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
}