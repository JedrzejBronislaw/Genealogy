package treeGraphs;

import lombok.AllArgsConstructor;
import model.Person;
import treeGraphs.painter.Direction;
import treeGraphs.painter.MyColor;
import treeGraphs.painter.MyFont;
import treeGraphs.painter.MyFont.Style;
import treeGraphs.painter.Point;

public class StdAncestorsTreeGraph extends TreeGraph {
	
	@AllArgsConstructor
	private class Coords {
		int personY;
		int branchBottom;
	}


	private static final int marginX = 20;
	private static final int marginY = 20;

	private static final int betweenParents = 15;
	private static final int emptyPersonHeight = 0;
	private static final int arrowSpaceWidth = 50;

	//arrow parameters
	private static final int childSpace = 10;
	private static final int parentSpace = 5;
	private static final int parentLineLenght = 10;
	private static final int parentsVericalLineSpace = parentLineLenght + parentSpace;
	
	private int[] gensWidths;
	private int[] gensXCoordinate;

	private MyFont font = new MyFont("Times", Style.REGULAR, 13);
	
	public StdAncestorsTreeGraph() {}
	public StdAncestorsTreeGraph(Person person) {
		this.mainPerson = person;
	}
	
	@Override
	public void draw() {
		if (mainPerson == null) return;
		
		painter.startDrawing();
		
		painter.setTextStyle(font);
		painter.setColor(MyColor.BLACK);
		
		
		calculateColumnsWidthsAndXCoords();
		width = getAllColumnsWidth();
		
		Coords coords;
		coords = drawBranch(mainPerson, 0, marginY);
		height = coords.branchBottom-betweenParents;
		
		height += marginY;
		width  += marginX;
	}
	
	private void calculateColumnsWidthsAndXCoords() {
		int numOfColumns = mainPerson.rootSize()+2;
		gensWidths      = new int[numOfColumns];
		gensXCoordinate = new int[numOfColumns];
		
		//Calculate names max width
		computeGenerationsWidth(mainPerson);
		
		//Calculate right bound (with place for arrows)
		gensXCoordinate[0] += gensWidths[0] + arrowSpaceWidth;
		for (int i=1; i<numOfColumns; i++)
			gensXCoordinate[i] = gensWidths[i] + gensXCoordinate[i-1] + arrowSpaceWidth;
		
		//Calculate left bound (right bound of left neighbor)
		for (int i=numOfColumns-1; i>0; i--)
			gensXCoordinate[i] = gensXCoordinate[i-1];
		gensXCoordinate[0] = 0;
		
		//move all by margin
		for (int i=0; i<numOfColumns; i++)
			gensXCoordinate[i] += marginX;
	}
	
	private int getAllColumnsWidth() {
		return gensXCoordinate[gensXCoordinate.length-1] - arrowSpaceWidth;
	}

	private void computeGenerationsWidth(Person person) {
		computeGenerationsWidth(person, 0);
	}
	private void computeGenerationsWidth(Person person, int generation) {
		if (person == null) return;
		
		gensWidths[generation] = Math.max(
				gensWidths[generation],
				nameDisplayer.getWidth(person));
		
		computeGenerationsWidth(person.getFather(), generation+1);
		computeGenerationsWidth(person.getMother(), generation+1);
	}
	
	private Coords drawBranch(Person person, int generation, int branchY)
	{		
		if (person == null)
			return new Coords(branchY, branchY+emptyPersonHeight+betweenParents);
		if (!hasAnyParent(person))
			return drawLeaf(person, gensXCoordinate[generation], branchY);
		
		
		int nameHeight = nameDisplayer.getHeight(person);
		int nameWidth  = nameDisplayer.getWidth(person);

		int branchX = gensXCoordinate[generation];
		int personX, personY;
		
		Person father = person.getFather();
		Person mother = person.getMother();
		Coords fatherBranch, motherBranch;

		
		fatherBranch = drawBranch(father, generation+1, branchY);
		motherBranch = drawBranch(mother, generation+1, fatherBranch.branchBottom);
		
		personX = branchX;
		personY = middleY(fatherBranch, motherBranch);
		
		draw(person, personX, personY);
		
		//Draw arrows
		int childX        = personX + nameWidth + childSpace;
		int verticalLineX = gensXCoordinate[generation+1] - parentsVericalLineSpace;
		
		int childY  = personY + nameHeight/2;
		int fatherY = fatherBranch.personY + nameHeight/2;
		int motherY = motherBranch.personY + nameHeight/2;
		
		Point childPoint         = new Point(childX, childY);
		Point verticalLineTop    = new Point(verticalLineX, fatherY);
		Point verticalLineBottom = new Point(verticalLineX, motherY);
		
		//line to child
		painter.drawLine(childPoint, new Point(verticalLineX, childY));
		painter.drawArrowhead(childPoint, Direction.LEFT);
		//vertical line
		painter.drawLine(verticalLineTop, verticalLineBottom);
		//line to father
		painter.drawLine(verticalLineTop, verticalLineTop.addVector(parentLineLenght, 0));
		//line to mother
		painter.drawLine(verticalLineBottom, verticalLineBottom.addVector(parentLineLenght, 0));
		
		return new Coords(personY, motherBranch.branchBottom);
	}

	private Coords drawLeaf(Person person, int leafX, int leafY) {
		int nameHeight = nameDisplayer.getHeight(person);
		
		draw(person, leafX, leafY);
		
		return new Coords(leafY, leafY + nameHeight + betweenParents);
	}
	
	private int middleY(Coords fatherBranch, Coords motherBranch) {
		int topY    = motherBranch.personY;
		int bottomY = fatherBranch.personY;
		
		return ((topY-bottomY) / 2) + bottomY;
	}
	
	private boolean hasAnyParent(Person person) {
		return (person.getMother() != null)
			|| (person.getFather() != null);
	}
}
