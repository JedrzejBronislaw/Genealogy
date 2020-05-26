package treeGraphs;

import lombok.Getter;
import lombok.Setter;
import model.Person;
import treeGraphs.painter.Direction;
import treeGraphs.painter.Handle;
import treeGraphs.painter.MyColor;
import treeGraphs.painter.MyFont;
import treeGraphs.painter.MyFont.Style;
import treeGraphs.painter.Point;

public class StdDescendantsTreeGraph extends TreeGraph{

	public enum SpaceType {OnlyBetweenSiblings, BetweenSiblingsAndCousins};
	
	private static final int marginesX = 20;
	private static final int marginesY = 20;
	private static final int spouseIndentation = 20;
	private	static final int minParentLineLength = 3;
	private	static final int childArrowLength = 15;
	private				 int betweenGenerationsSpace;
	private static final int lineMargin = 5;
	private static final int betweenSpousesSpace = 4;
	private static final int betweenSiblingsSpace = 10;
	private static final int betwennCousisnsSpace = 20;

	private MyFont font = new MyFont("Times", Style.REGULAR, 13);
	private static final MyColor ringsColor = new MyColor(255, 215, 0);

	@Setter @Getter
	private SpaceType spaceType = SpaceType.OnlyBetweenSiblings;
	
	private int[] columnsWidths;

	public StdDescendantsTreeGraph() {}
	public StdDescendantsTreeGraph(Person person) {
		mainPerson = person;
	}

	@Override
	public void draw() {
		
		if (mainPerson == null) return;
		
		painter.startDrawing();
		painter.setTextStyle(font);
		painter.setColor(MyColor.BLACK);
		
		betweenGenerationsSpace = minParentLineLength + childArrowLength + lineMargin*2;
		
		width = calculateColumnsWidths();
		if (spaceType == SpaceType.OnlyBetweenSiblings)
			height = drawFamily(mainPerson, marginesX, marginesY, 0) - betweenSiblingsSpace;
		else
			height = drawFamily(mainPerson, marginesX, marginesY, 0, true);
		
		height += marginesY * 2;
		width  += marginesX * 2;
	}

	private int calculateColumnsWidths() {
		columnsWidths = new int[mainPerson.descendantGenerations()+1];
		int totalWidth = 0;
		
		branchesWidth(mainPerson, 0);
		
		for (int i=0; i<columnsWidths.length; i++)
			totalWidth += columnsWidths[i] + betweenGenerationsSpace;
		totalWidth -= betweenGenerationsSpace;
		
		return totalWidth;
	}	
	
	private void branchesWidth(Person person, int generation)
	{
		int width;
		Person spouse;

		//compare own width
		width = nameDisplayer.getWidth(person);
		if (width > columnsWidths[generation])
			columnsWidths[generation] = width;

		//compare widths all spouses
		for (int i=0; i<person.numberOfMarriages(); i++)
		{
			spouse = person.getSpouse(i);
			width = nameDisplayer.getWidth(spouse) + spouseIndentation;
			if (width > columnsWidths[generation])
				columnsWidths[generation] = width;			
		}
		
		//recurring invoke for all children
		for (int i=0; i<person.numberOfChildren(); i++)
			branchesWidth(person.getChild(i), generation+1);
	}
	
	private int drawFamily(Person person, int x, int y, int generation)
	{
		int offset = 0;
		int spouseOffset = 0;
		int nameHeight = nameDisplayer.getHeight(person);
		int nameWidth  = nameDisplayer.getWidth(person);
		Handle handle;
		
		int lineX;
		
		y += nameHeight;
		handle = nameDisplayer.print(person, x, y);
		setHandleEvents(handle, person);
		
		for (int i=0; i<person.numberOfMarriages(); i++)
			spouseOffset += drawSpouse(person.getSpouse(i), x, y+spouseOffset+betweenSpousesSpace) + betweenSpousesSpace;
	
		if (person.numberOfChildren() > 0)
		{
			lineX = x + columnsWidths[generation] + lineMargin + minParentLineLength;

			//line from parent
			painter.drawLine(new Point(x+nameWidth+lineMargin, y-nameHeight/2), new Point(lineX, y-nameHeight/2));
			for (int i=0; i<person.numberOfChildren(); i++)
			{
				//line to child
				painter.drawLine(new Point(lineX, y+offset-nameHeight/2), new Point(lineX+childArrowLength, y+offset-nameHeight/2));
				painter.drawArrowhead(new Point(lineX+childArrowLength, y+offset-nameHeight/2), Direction.RIGHT);
				drawMarriageNumber(person.getChild(i), person, lineX, y+offset, y+offset-nameHeight/2);

				//vertical line
				painter.drawLine(new Point(lineX, y-nameHeight/2), new Point(lineX, y-nameHeight/2+offset));
				offset += drawFamily(person.getChild(i), lineX+childArrowLength+lineMargin, y-nameHeight+offset, generation+1) + betweenSiblingsSpace;
			}
		}

		return Math.max(spouseOffset+nameHeight, offset);
	
		
	}
	
	private void drawMarriageNumber(Person child, Person parent, int x1, int y1, int lineY) {
		if (parent.numberOfMarriages() > 1)
		{
			MyColor tempColor;
			int marriageNumber = child.parentsMarriageNumber(parent);
			if (marriageNumber != 0)
			{
				tempColor = painter.getColor();
				painter.setColor(MyColor.WHITE);
				painter.drawLine(new Point(x1+2, lineY), new Point(x1+2+painter.getTextWidth(marriageNumber+""), lineY));
				painter.setColor(tempColor);
				painter.drawText(marriageNumber+"", new Point(x1+3, y1));
			}
		}
	}
	
	private int drawFamily(Person person, int x, int y, int generation, boolean last)
	{
		int offset = 0;
		int spouseOffset = 0;
		int nameHeight = nameDisplayer.getHeight(person);
		int nameWidth  = nameDisplayer.getWidth(person);
		Handle handle;
		
		int lineX;
		
		y += nameHeight;
		handle = nameDisplayer.print(person, x, y);
		setHandleEvents(handle, person);
		
		for (int i=0; i<person.numberOfMarriages(); i++)
			spouseOffset += drawSpouse(person.getSpouse(i), x, y+spouseOffset+betweenSpousesSpace) + betweenSpousesSpace;
	
		if (person.numberOfChildren() > 0)
		{
			lineX = x + columnsWidths[generation] + lineMargin + minParentLineLength;

			//line from parent
			painter.drawLine(new Point(x+nameWidth+lineMargin, y-nameHeight/2), new Point(lineX, y-nameHeight/2));
			for (int i=0; i<person.numberOfChildren(); i++)
			{
				//line to child
				painter.drawLine(new Point(lineX, y+offset-nameHeight/2), new Point(lineX+childArrowLength, y+offset-nameHeight/2));
				painter.drawArrowhead(new Point(lineX+childArrowLength, y+offset-nameHeight/2), Direction.RIGHT);
				drawMarriageNumber(person.getChild(i), person, lineX, y+offset, y+offset-nameHeight/2);

				//vertical line
				painter.drawLine(new Point(lineX, y-nameHeight/2), new Point(lineX, y-nameHeight/2+offset));
				offset += drawFamily(person.getChild(i), lineX+childArrowLength+lineMargin, y-nameHeight+offset, generation+1, i==person.numberOfChildren()-1);
			}
		}

		if (last)
			return Math.max(spouseOffset+nameHeight, offset);
		
		if (person.numberOfChildren() == 0)
			return spouseOffset+nameHeight+betweenSiblingsSpace;
		
		return Math.max(spouseOffset+nameHeight+betweenSiblingsSpace, offset+betwennCousisnsSpace);
	}
	
	private int drawSpouse(Person person, int x, int y)
	{
		Handle handle;
		int nameHeight = nameDisplayer.getHeight(person);
		
		handle = nameDisplayer.print(person, x+spouseIndentation, y+nameHeight);
		setHandleEvents(handle, person);
		drawRings(x, y, spouseIndentation, nameHeight);
		
		return nameHeight;
	}

	private void drawRings(int x, int y, int width, int height) {
		MyColor color = painter.getColor();
		final float commonPart = (float)0.3; 
		float ringsRatio = (float) (2.0-commonPart);
		float areaRatio  = width/height;
		int ringHeight, ringWidth;
		int ringsWidth;
		int x2;
		
		if (ringsRatio > areaRatio)
		{
			//width is the limit
			ringsWidth = width;
			ringWidth = ringHeight = (int) (ringsWidth/(2-commonPart));
		}
		else
		{
			//height is the limit
			ringWidth = ringHeight = height;
			ringsWidth = (int) (ringWidth*(2-commonPart));
		}
		
		painter.setColor(ringsColor);
		
		x += (width-ringsWidth)/2;
		y += (height -ringHeight) /2;
		x2 = (int)(x+(ringWidth*(1-commonPart)));
		
		Point ring1 = new Point(x , y);
		Point ring2 = new Point(x2, y);
		painter.drawRing(ring1, ring1.addVector(ringWidth, ringHeight));
		painter.drawRing(ring2, ring2.addVector(ringWidth, ringHeight));
		
		painter.setColor(color);
	}
}