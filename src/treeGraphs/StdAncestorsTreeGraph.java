package treeGraphs;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import model.Person;

public class StdAncestorsTreeGraph extends TreeGraph {


	private static int marginX = 20;
	private static int marginY = 20;
	private static int rowHeight = 40;
//	private static int cloumnWidth = 177;

	private static int verticalOffset = 15;
	private static int horizontalOffset = 50;
	
	private int y2 = 0;
	private int[] columnsWidths;

	private Font font = new Font("Times", Font.PLAIN, 13);
	
	public StdAncestorsTreeGraph() {}
	public StdAncestorsTreeGraph(Person person) {
		this.mainPerson = person;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		g.setFont(font);
		nameDisplay.setGraphics(g);
		
		clickMap.clear();
		if (mainPerson == null)
			return;
		
		width = calculateColumnsWidths(g.getFontMetrics());
		
		y2 = marginY;
		drawRoot(g, mainPerson, 0, 0);
		height  = y2-verticalOffset;
		
		height  += marginY;
		width += marginX;
	}
	
	private int calculateColumnsWidths(FontMetrics fm) {
		columnsWidths = new int[mainPerson.rootSize()+2];
		
		//Calculate names max width
		columnsWidths[0] = nameDisplay.getWidth(mainPerson);
		parentsWidth(fm, mainPerson, 1);
		
		//Calculate right bound (with place for arrows)
		columnsWidths[0] += horizontalOffset;
		for (int i=1; i<columnsWidths.length; i++)
			columnsWidths[i] += columnsWidths[i-1] + horizontalOffset;
		
		//Calculate left bound (right bound of left neighbor)
		for (int i=columnsWidths.length-1; i>0; i--)
			columnsWidths[i] = columnsWidths[i-1];
		columnsWidths[0] = 0;
		
		//move all by margin
		for (int i=0; i<columnsWidths.length; i++)
			columnsWidths[i] += marginX;

		return columnsWidths[columnsWidths.length-1] - horizontalOffset;
	}
	
	private int parentsWidth(FontMetrics fm, Person person, int generation)
	{
		int mother  = (person.getMother()  == null) ? 0 : nameDisplay.getWidth(person.getMother());
		int father = (person.getFather() == null) ? 0 : nameDisplay.getWidth(person.getFather());
		int wider = (father > mother) ? father : mother;
		
		if (columnsWidths[generation] < wider)
			columnsWidths[generation] = wider;

		if (person.getMother()  != null) parentsWidth(fm, person.getMother(),  generation+1);
		if (person.getFather() != null) parentsWidth(fm, person.getFather(), generation+1);
		
		return wider;
	}

	private int drawRoot(Graphics2D g, Person person, int generation, int y1)
	{		
		int nameHeight  = nameDisplay.getHeight(person);
		int nameWidth = nameDisplay.getWidth(person);

		Person father = person.getFather();
		Person mother  = person.getMother();
		int fatherY, motherY;
		int x,y;
		
		if ((mother != null) || (father != null))
		{
			if (father != null)
				fatherY = drawRoot(g, father, generation+1, y2);
			else {
				fatherY = y1;
				y2 = y1 + rowHeight;
			}
			if (mother != null)
				motherY = drawRoot(g, mother, generation+1, y2);
			else {
				motherY = fatherY + rowHeight;
				y2 = y2 + rowHeight;
			}
			
			x = columnsWidths[generation];
			y = ((motherY-fatherY) / 2) + fatherY;
			nameDisplay.print(person, x, y+nameHeight);
			clickMap.addArea(person, x, y+nameHeight, x+nameWidth, y);
			
			//arrowhead
			g.drawLine(x+nameWidth+10, y+nameHeight/2, x+nameWidth+20, y+nameHeight/2 -3);
			g.drawLine(x+nameWidth+10, y+nameHeight/2, x+nameWidth+20, y+nameHeight/2 +3);
			//line to child
			g.drawLine(x+nameWidth+10, y+nameHeight/2, columnsWidths[generation+1]-15, y+nameHeight/2);
			//vertical line
			g.drawLine(columnsWidths[generation+1]-15, fatherY+nameHeight/2, columnsWidths[generation+1]-15, motherY+nameHeight/2);
			//line to father
			g.drawLine(columnsWidths[generation+1]-15, fatherY+nameHeight/2, columnsWidths[generation+1]-5, fatherY+nameHeight/2);
			//line to mother
			g.drawLine(columnsWidths[generation+1]-15, motherY+nameHeight/2, columnsWidths[generation+1]-5, motherY+nameHeight/2);
			
			return y;
		} else
		{
			x = columnsWidths[generation];
			nameDisplay.print(person, x, y1+nameHeight);
			//click map
			clickMap.addArea(person, x, y1+nameHeight, columnsWidths[generation+1]-horizontalOffset, y1);
			y2 = y1+nameHeight+verticalOffset;
			return y1;
		}
	}
}
