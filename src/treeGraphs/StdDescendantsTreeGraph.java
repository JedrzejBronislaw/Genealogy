package treeGraphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import lombok.Getter;
import lombok.Setter;
import model.Person;

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
	private static final int arrowheadWidth = 5;
	private static final int arrowheadHeight  = 3;

	private Font font = new Font("Times", Font.PLAIN, 13);
	private static final Color ringsColor = new Color(255, 215, 0);

	@Setter @Getter
	private SpaceType spaceType = SpaceType.OnlyBetweenSiblings;
	
	private int[] columnsWidths;

	public StdDescendantsTreeGraph() {}
	public StdDescendantsTreeGraph(Person person) {
		mainPerson = person;
	}

	@Override
	public void draw(Graphics2D g) {
		
		if (mainPerson == null) return;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		nameDisplay.setGraphics(g);
		clickMap.clear();
		
		betweenGenerationsSpace = minParentLineLength + childArrowLength + lineMargin*2;
		
		width = calculateColumnsWidths(g.getFontMetrics());
		if (spaceType == SpaceType.OnlyBetweenSiblings)
			height = drawFamily(g, mainPerson, marginesX, marginesY, 0) - betweenSiblingsSpace;
		else
			height = drawFamily(g, mainPerson, marginesX, marginesY, 0, true);
		
		height += marginesY * 2;
		width  += marginesX * 2;
	}

	private int calculateColumnsWidths(FontMetrics fm) {
		columnsWidths = new int[mainPerson.descendantGenerations()+1];
		int totalWidth = 0;
		
		branchesWidth(fm, mainPerson, 0);
		
		for (int i=0; i<columnsWidths.length; i++)
			totalWidth += columnsWidths[i] + betweenGenerationsSpace;
		totalWidth -= betweenGenerationsSpace;
		
		return totalWidth;
	}	
	
	private void branchesWidth(FontMetrics fm, Person person, int generation)
	{
		int width;
		Person spouse;

		//compare own width
		width = nameDisplay.getWidth(person);
		if (width > columnsWidths[generation])
			columnsWidths[generation] = width;

		//compare widths all spouses
		for (int i=0; i<person.numberOfMarriages(); i++)
		{
			spouse = person.getSpouse(i);
			width = nameDisplay.getWidth(spouse) + spouseIndentation;
			if (width > columnsWidths[generation])
				columnsWidths[generation] = width;			
		}
		
		//recurring invoke for all children
		for (int i=0; i<person.numberOfChildren(); i++)
			branchesWidth(fm, person.getChild(i), generation+1);
	}
	
	private int drawFamily(Graphics2D g, Person person, int x, int y, int generation)
	{
		int offset = 0;
		int spouseOffset = 0;
		int nameHeight  = nameDisplay.getHeight(person);
		int nameWidth = nameDisplay.getWidth(person);
		
		int lineX;
		
		y += nameHeight;
		nameDisplay.print(person, x, y);
		clickMap.addArea(person, x, y, x+nameWidth, y-nameHeight);
		
		for (int i=0; i<person.numberOfMarriages(); i++)
			spouseOffset += drawSpouse(g, person.getSpouse(i), x, y+spouseOffset+betweenSpousesSpace) + betweenSpousesSpace;
	
		if (person.numberOfChildren() > 0)
		{
			lineX = x + columnsWidths[generation] + lineMargin + minParentLineLength;

			//line from parent
			g.drawLine(x+nameWidth+lineMargin, y-nameHeight/2, lineX, y-nameHeight/2);
			for (int i=0; i<person.numberOfChildren(); i++)
			{
				//line to child
				g.drawLine(lineX, y+offset-nameHeight/2, lineX+childArrowLength, y+offset-nameHeight/2);
				drawArrowhead(g, lineX+childArrowLength, y+offset-nameHeight/2);
				drawMarriageNumber(g, person.getChild(i), person, lineX, y+offset, y+offset-nameHeight/2);

				//vertical line
				g.drawLine(lineX, y-nameHeight/2, lineX, y-nameHeight/2+offset);
				offset += drawFamily(g, person.getChild(i), lineX+childArrowLength+lineMargin, y-nameHeight+offset, generation+1) + betweenSiblingsSpace;
			}
		}

		return Math.max(spouseOffset+nameHeight, offset);
	
		
	}
	
	private void drawMarriageNumber(Graphics2D g, Person child, Person parent, int x1, int y1, int lineY) {
		if (parent.numberOfMarriages() > 1)
		{
			Color tempColor;
			FontMetrics fm = g.getFontMetrics();
			int marriageNumber = child.parentsMarriageNumber(parent);
			if (marriageNumber != 0)
			{
				tempColor = g.getColor();
				g.setColor(g.getBackground());
				g.drawLine(x1+2, lineY, x1+2+fm.stringWidth(marriageNumber+""), lineY);
				g.setColor(tempColor);
				g.drawString(marriageNumber+"", x1+3, y1);
			}
		}
	}
	
	private int drawFamily(Graphics2D g, Person person, int x, int y, int generation, boolean last)
	{
		int offset = 0;
		int spouseOffset = 0;
		int nameHeight  = nameDisplay.getHeight(person);
		int nameWidth = nameDisplay.getWidth(person);

		int lineX;
		
		y += nameHeight;
		nameDisplay.print(person, x, y);
		clickMap.addArea(person, x, y, x+nameWidth, y-nameHeight);
		
		for (int i=0; i<person.numberOfMarriages(); i++)
			spouseOffset += drawSpouse(g, person.getSpouse(i), x, y+spouseOffset+betweenSpousesSpace) + betweenSpousesSpace;
	
		if (person.numberOfChildren() > 0)
		{
			lineX = x + columnsWidths[generation] + lineMargin + minParentLineLength;

			//line from parent
			g.drawLine(x+nameWidth+lineMargin, y-nameHeight/2, lineX, y-nameHeight/2);
			for (int i=0; i<person.numberOfChildren(); i++)
			{
				//line to child
				g.drawLine(lineX, y+offset-nameHeight/2, lineX+childArrowLength, y+offset-nameHeight/2);
				drawArrowhead(g, lineX+childArrowLength, y+offset-nameHeight/2);
				drawMarriageNumber(g, person.getChild(i), person, lineX, y+offset, y+offset-nameHeight/2);

				//vertical line
				g.drawLine(lineX, y-nameHeight/2, lineX, y-nameHeight/2+offset);
				offset += drawFamily(g, person.getChild(i), lineX+childArrowLength+lineMargin, y-nameHeight+offset, generation+1, i==person.numberOfChildren()-1);
			}
		}

		if (last)
			return Math.max(spouseOffset+nameHeight, offset);
		
		if (person.numberOfChildren() == 0)
			return spouseOffset+nameHeight+betweenSiblingsSpace;
		
		return Math.max(spouseOffset+nameHeight+betweenSiblingsSpace, offset+betwennCousisnsSpace);
	}
	
	private int drawSpouse(Graphics2D g, Person person, int x, int y)
	{
		int nameHeight = nameDisplay.getHeight(person);
		int nameWidth = nameDisplay.getWidth(person);
		
		nameDisplay.print(person, x+spouseIndentation, y+nameHeight);
		clickMap.addArea(person, x, y+nameHeight, x+spouseIndentation+nameWidth, y);
		drawRings(g, x, y, spouseIndentation, nameHeight);
		
		return nameHeight;
	}

	private void drawRings(Graphics2D g, int x, int y, int width, int height) {
		Color color = g.getColor();
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
		
		g.setColor(ringsColor);
		
		x += (width-ringsWidth)/2;
		y += (height -ringHeight) /2;
		x2 = (int)(x+(ringWidth*(1-commonPart)));
		

		g.drawOval(x , y, ringWidth, ringHeight);
		g.drawOval(x2, y, ringWidth, ringHeight);
		
		g.setColor(color);
	}
	
	private void drawArrowhead(Graphics2D g, int x, int y)
	{
		g.drawLine(x, y, x-arrowheadWidth, y-arrowheadHeight);
		g.drawLine(x, y, x-arrowheadWidth, y+arrowheadHeight);
	}
}