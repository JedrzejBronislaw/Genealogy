package treeGraphs;

import java.awt.Font;

import model.Person;
import tools.Injection;
import treeGraphs.painter.Direction;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Point;

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
	public void draw() {
		painter.startDrawing();
		
		painter.setTextStyle(font);
		
		if (mainPerson == null)
			return;
		
		width = calculateColumnsWidths();
		
		y2 = marginY;
		drawRoot(mainPerson, 0, 0);
		height  = y2-verticalOffset;
		
		height  += marginY;
		width += marginX;
	}
	
	private int calculateColumnsWidths() {
		columnsWidths = new int[mainPerson.rootSize()+2];
		
		//Calculate names max width
		columnsWidths[0] = nameDisplay.getWidth(mainPerson);
		parentsWidth(mainPerson, 1);
		
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
	
	private int parentsWidth(Person person, int generation)
	{
		int mother = (person.getMother()  == null) ? 0 : nameDisplay.getWidth(person.getMother());
		int father = (person.getFather() == null) ? 0 : nameDisplay.getWidth(person.getFather());
		int wider = (father > mother) ? father : mother;
		
		if (columnsWidths[generation] < wider)
			columnsWidths[generation] = wider;

		if (person.getMother() != null) parentsWidth(person.getMother(),  generation+1);
		if (person.getFather() != null) parentsWidth(person.getFather(), generation+1);
		
		return wider;
	}

	private int drawRoot(Person person, int generation, int y1)
	{		
		int nameHeight = nameDisplay.getHeight(person);
		int nameWidth  = nameDisplay.getWidth(person);

		Person father = person.getFather();
		Person mother = person.getMother();
		int fatherY, motherY;
		int x,y;
		Handle handle;
		
		if ((mother != null) || (father != null))
		{
			if (father != null)
				fatherY = drawRoot(father, generation+1, y2);
			else {
				fatherY = y1;
				y2 = y1 + rowHeight;
			}
			if (mother != null)
				motherY = drawRoot(mother, generation+1, y2);
			else {
				motherY = fatherY + rowHeight;
				y2 = y2 + rowHeight;
			}
			
			x = columnsWidths[generation];
			y = ((motherY-fatherY) / 2) + fatherY;
			handle = nameDisplay.print(person, x, y+nameHeight);
			handle.setOnMouseClick(() -> Injection.run(personClickAction, person));
			
			//arrowhead
			painter.drawArrowhead(new Point(x+nameWidth+10, y+nameHeight/2), Direction.LEFT);
			//line to child
			painter.drawLine(new Point(x+nameWidth+10, y+nameHeight/2), new Point(columnsWidths[generation+1]-15, y+nameHeight/2));
			//vertical line
			painter.drawLine(new Point(columnsWidths[generation+1]-15, fatherY+nameHeight/2), new Point(columnsWidths[generation+1]-15, motherY+nameHeight/2));
			//line to father
			painter.drawLine(new Point(columnsWidths[generation+1]-15, fatherY+nameHeight/2), new Point(columnsWidths[generation+1]-5, fatherY+nameHeight/2));
			//line to mother
			painter.drawLine(new Point(columnsWidths[generation+1]-15, motherY+nameHeight/2), new Point(columnsWidths[generation+1]-5, motherY+nameHeight/2));
			
			return y;
		} else
		{
			x = columnsWidths[generation];
			handle = nameDisplay.print(person, x, y1+nameHeight);
			handle.setOnMouseClick(() -> Injection.run(personClickAction, person));
			
			y2 = y1+nameHeight+verticalOffset;
			return y1;
		}
	}
}
