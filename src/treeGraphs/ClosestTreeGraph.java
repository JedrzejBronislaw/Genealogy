package treeGraphs;

import java.awt.Font;

import lombok.Setter;
import model.Person;
import tools.Injection;
import treeGraphs.painter.Direction;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Point;

public class ClosestTreeGraph extends TreeGraph {


	private static final int marginX = 20;
	private static final int marginY = 20;
	private static final int lineMargin = 5;
	private static final int minSpaceBetweenParents = 50;
	private static final int underParentsSpace = 30;
	private static final int spaceBetweenSiblings = 7;
	private static final int spaceBetweenSpouses = 7;
	private static final int spaceBetweenChildren = 7;
	private static final int spouceIndentation = 20;
	private static final int childIndentation = 20;
	private static final int aboveChildrenSpace = 30;
	private static final int minSpaceBetweenSpousesChildrenAndSiblings = 30;

	private static final Font mainPersonFont = new Font("Times", Font.BOLD, 25);
	private static final Font familyFont   = new Font("Arial", Font.BOLD, 12);


	private int areaWidth;
	private int xName, yName;
	private int nameWidth, nameHeight;
	private int motherNameWidth, fatherNameWidth;
	private int textHeight;
	private int siblingsWidth, siblingsHeight;
	private int spousesWidth, spousesHeight;
	private int childrenWidth, childrenHeight;
	
	@Setter
	boolean makeCalculations = true;
	
	@Override
	public void setMainPerson(Person osoba) {
		this.mainPerson = osoba;
		makeCalculations = true;
	}
	
	public ClosestTreeGraph() {}
	public ClosestTreeGraph(Person mainPerson) {
		this.mainPerson = mainPerson;
	}
	
	@Override
	public void draw() {
		painter.startDrawing();
		
		if (mainPerson == null)
			return;
		
		if(makeCalculations)
		{
			calculations();
			makeCalculations = false;
		}
		
		String name = mainPerson.nameSurname();
		Person[] siblings = mainPerson.getSiblings();
		
		painter.setTextStyle(mainPersonFont);
		painter.drawText(name, new Point(xName, yName));
		
		painter.setTextStyle(familyFont);

		drawLines(siblings.length > 0);
		drawParents(areaWidth);
		drawSiblings(siblings, marginX+areaWidth-siblingsWidth, yName);
		drawSpouses(xName+spouceIndentation, yName);
		drawChildren(xName+childIndentation, yName+aboveChildrenSpace-spaceBetweenChildren+spousesHeight);		
	}
	
	private void calculations()
	{
		Person mother = mainPerson.getMother();
		Person father = mainPerson.getFather();
		Person[] siblings = mainPerson.getSiblings();
		String name = mainPerson.nameSurname();
		String motherName  = (mother != null)  ? mother.nameSurname()  : "";
		String fatherName = (father != null) ? father.nameSurname() : "";
		
		
		painter.setTextStyle(mainPersonFont);
		nameHeight = painter.getTextHeight();
		nameWidth  = painter.getTextWidth(name);
		
		xName = marginX;
		yName = marginY+nameHeight;
		if ((father != null) || (mother != null))
			yName += underParentsSpace;
		
		painter.setTextStyle(familyFont);
		textHeight = painter.getTextHeight();
		motherNameWidth = painter.getTextWidth(motherName);
		fatherNameWidth = painter.getTextWidth(fatherName);

		siblingsWidth = siblingsWidth(siblings);
		siblingsHeight  = siblings.length * (textHeight+spaceBetweenSiblings);
		spousesWidth = spousesWidth();
		spousesHeight  = mainPerson.numberOfMarriages() * (textHeight+spaceBetweenSpouses);
		childrenWidth = childrenWidth();
		childrenHeight  = mainPerson.numberOfChildren() * (textHeight+spaceBetweenChildren)-spaceBetweenChildren;
		
		areaWidth = nameWidth;
		if (areaWidth < fatherNameWidth+motherNameWidth+minSpaceBetweenParents)
			areaWidth = fatherNameWidth+motherNameWidth+minSpaceBetweenParents;
		if (areaWidth < spouceIndentation+spousesWidth+minSpaceBetweenSpousesChildrenAndSiblings+siblingsWidth)
			areaWidth = spouceIndentation+spousesWidth+minSpaceBetweenSpousesChildrenAndSiblings+siblingsWidth;
		if (areaWidth < childIndentation+childrenWidth+minSpaceBetweenSpousesChildrenAndSiblings+siblingsWidth)
			areaWidth = childIndentation+childrenWidth+minSpaceBetweenSpousesChildrenAndSiblings+siblingsWidth;
		
		height = yName+siblingsHeight;
		if (height < yName+spousesHeight)
			height = yName+spousesHeight;
		if ((mainPerson.numberOfChildren() > 0) && (height < yName+spousesHeight+aboveChildrenSpace+childrenHeight))
			height = yName+spousesHeight+aboveChildrenSpace+childrenHeight;
		width = areaWidth;
		
		height  += marginY;	//not twice, because it is already in yName
		width += marginX*2;
	}
	
	private void drawLines(boolean hasSiblings)
	{
		Person father = mainPerson.getFather();
		Person mother  = mainPerson.getMother();
		
		//between parents line
		int xFromFather = marginX+fatherNameWidth+lineMargin;
		int xFromMother= marginX+areaWidth-motherNameWidth-lineMargin;
		int xLine = xFromFather + (xFromMother - xFromFather)/2;
		int yLineTop = marginY+textHeight/2;
		int yLineBottom = marginY+underParentsSpace-lineMargin;
		if (father != null)
			painter.drawLine(new Point(xFromFather, yLineTop), new Point(xLine, yLineTop));
		if (mother != null)
			painter.drawLine(new Point(xFromMother, yLineTop), new Point(xLine, yLineTop));

		//from parents line
		if ((father != null) || (mother != null))
		{
			painter.drawLine(new Point(xLine, yLineTop), new Point(xLine, yLineBottom));
			drawArrowhead(xLine, yLineBottom);
		}
		
		//to siblings line
		if (hasSiblings)
		{
			int xAboveSiblings = marginX+areaWidth-siblingsWidth/2;
			if (xAboveSiblings < marginX+nameWidth+(areaWidth-nameWidth)/2)
				xAboveSiblings = marginX+nameWidth+(areaWidth-nameWidth)/2;
			int yAboveSiblings = yName+spaceBetweenSiblings-lineMargin;
			painter.drawLine(new Point(xLine, yLineTop+(yLineBottom-yLineTop)/2), new Point(xAboveSiblings, yLineTop+(yLineBottom-yLineTop)/2));
			painter.drawLine(new Point(xAboveSiblings, yLineTop+(yLineBottom-yLineTop)/2), new Point(xAboveSiblings, yAboveSiblings));
			drawArrowhead(xAboveSiblings, yAboveSiblings);
		}
		
		//to children line
		if (mainPerson.numberOfChildren() > 0)
		{
			int xToChildren = marginX+childIndentation+childrenWidth/2;
			int yToChildrenTop = yName+spousesHeight+lineMargin;
			int yToChildrenBottom = yName+spousesHeight+aboveChildrenSpace-lineMargin;
			painter.drawLine(new Point(xToChildren, yToChildrenTop), new Point(xToChildren, yToChildrenBottom));
			drawArrowhead(xToChildren, yToChildrenBottom);
		}
	}
	
	private void drawArrowhead(int x, int y)
	{
		painter.drawArrowhead(new Point(x, y), Direction.DOWN);
	}
	
	private void drawParents(int areaWidth)
	{
		Handle fHandle, mHandle;
		Person mother = mainPerson.getMother();
		Person father = mainPerson.getFather();
		String motherName  = (mother != null)  ? mother.nameSurname()  : "";
		String fatherName = (father != null) ? father.nameSurname() : "";

		fHandle = painter.drawText(fatherName, new Point(marginX, marginY+textHeight));
		mHandle = painter.drawText(motherName, new Point(marginX+areaWidth-motherNameWidth, marginY+textHeight));
		
		if (father != null)
			fHandle.setOnMouseClick(() -> Injection.run(personClickAction, father));
		if (mother != null)
			mHandle.setOnMouseClick(() -> Injection.run(personClickAction, mother));
	}
	
	private void drawSiblings(Person[] siblings, int x, int y)
	{
		Font f = painter.getTextStyle();
		painter.setTextStyle(f.getName(), Font.ITALIC, f.getSize());

		int height = painter.getTextHeight();
		Handle handle;
		
		for (int i=0; i<siblings.length; i++)
		{
			Person sibling = siblings[i];
			width  = painter.getTextWidth(sibling.nameSurname());
			handle = painter.drawText(sibling.nameSurname(), new Point(x, y+(i+1)*(height+spaceBetweenSiblings)));
			
			handle.setOnMouseClick(() -> Injection.run(personClickAction, sibling));
		}
		
		painter.setTextStyle(f.getName(), f.getStyle(), f.getSize());
	}
	
	private void drawSpouses(int x, int y)
	{
		int height = painter.getTextHeight();
		Handle handle;
		
		for (int i=0; i<mainPerson.numberOfMarriages(); i++)
		{
			Person spouse;
			spouse = mainPerson.getSpouse(i);
			width  = painter.getTextWidth(spouse.nameSurname());
			handle = painter.drawText(spouse.nameSurname(), new Point(x, y+(i+1)*(height+spaceBetweenSpouses)));
			
			handle.setOnMouseClick(() -> Injection.run(personClickAction, spouse));
		}		
	}
	
	private void drawChildren(int x, int y)
	{
		int height = painter.getTextHeight();
		Handle handle;
		
		for (int i=0; i<mainPerson.numberOfChildren(); i++)
		{
			Person child;
			child  = mainPerson.getChild(i);
			width  = painter.getTextWidth(child.nameSurname());
			handle = painter.drawText(child.nameSurname(), new Point(x, y+(i+1)*(height+spaceBetweenChildren)));

			handle.setOnMouseClick(() -> Injection.run(personClickAction, child));
		}		
	}

	private int siblingsWidth(Person[] sibilings) {
		Font f = painter.getTextStyle();
		painter.setTextStyle(f.getName(), Font.PLAIN, f.getSize());
		
		int maxWidth = 0;
		
		for(Person sibiling: sibilings)
			if (maxWidth < painter.getTextWidth(sibiling.nameSurname()))
				maxWidth = painter.getTextWidth(sibiling.nameSurname());

		painter.setTextStyle(f);
		return maxWidth;
	}

	private int spousesWidth() {
		int maxWidth = 0;
		Person spouse;
		
		for (int i=0; i<mainPerson.numberOfMarriages(); i++)
		{
			spouse = mainPerson.getSpouse(i);
			if (maxWidth < painter.getTextWidth(spouse.nameSurname()))
				maxWidth = painter.getTextWidth(spouse.nameSurname());
		}
		return maxWidth;
	}

	private int childrenWidth() {
		int maxWidth = 0;
		Person child;
		
		for (int i=0; i<mainPerson.numberOfChildren(); i++)
		{
			child = mainPerson.getChild(i);
			if (maxWidth < painter.getTextWidth(child.nameSurname()))
				maxWidth = painter.getTextWidth(child.nameSurname());
		}
		return maxWidth;
	}
}