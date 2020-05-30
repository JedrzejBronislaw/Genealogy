package treeGraphs;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import model.Person;
import treeGraphs.painter.Direction;
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
	private static final int lineMargin = 5;
	private	static final int betweenGenerationsSpace = minParentLineLength + childArrowLength + lineMargin*2;
	private static final int betweenSpousesSpace = 4;
	private static final int betweenSiblingsSpace = 10;
	private static final int betwennCousisnsSpace = 20;

	private MyFont font = new MyFont("Times", Style.REGULAR, 13);
	private static final MyColor ringsColor = new MyColor(255, 215, 0);

	@Setter @Getter
	private SpaceType spaceType = SpaceType.OnlyBetweenSiblings;
//	private SpaceType spaceType = SpaceType.BetweenSiblingsAndCousins;
	
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
		
		width = computeWidths();
		height = drawFamily(mainPerson, marginesX, marginesY);

		height += marginesY * 2;
		width  += marginesX * 2;
	}
	
	private int computeWidths() {
		columnsWidths = new int[1 + mainPerson.descendantGenerations()];
		computeGenerationsWidth(mainPerson, 0);
		return calculateColumnsWidths();
	}

	private int calculateColumnsWidths() {
		int totalWidth = 0;
		
		for (int i=0; i<columnsWidths.length; i++)
			totalWidth += columnsWidths[i] + betweenGenerationsSpace;
		totalWidth -= betweenGenerationsSpace;
		
		return totalWidth;
	}	
	
	private void computeGenerationsWidth(Person person, int generation)
	{
		checkWidth(person, generation);
		
		Arrays.asList(person.getSpouses()).forEach(spouse ->
			checkWidth(spouse, generation, spouseIndentation));
		
		Arrays.asList(person.getChildren()).forEach(child ->
			computeGenerationsWidth(child, generation+1));
	}
	private void checkWidth(Person person, int generation) {
		checkWidth(person, generation, 0);
	}
	private void checkWidth(Person person, int generation, int appendix) {
		columnsWidths[generation] = Math.max(
				columnsWidths[generation],
				nameDisplayer.getWidth(person) + appendix);
	}
	
	private int drawFamily(Person person, int x, int y) {
		int treeHeight = drawFamily(mainPerson, marginesX, marginesY, 0, true);
		
		if (spaceType == SpaceType.OnlyBetweenSiblings)
			treeHeight -= betweenSiblingsSpace;
		
		return treeHeight;
	}
	private int drawFamily(Person person, int x, int y, int generation, boolean isLastChild) {
		
		draw(person, x, y);
		
		int nameHeight     = nameDisplayer.getHeight(person);
		int spousesHeight  = drawSpouses(person, x, y);
		int childrenHeight = drawChildren(person, x, y, generation);
		
		//return
		int parentsHeight = nameHeight + spousesHeight;
		int familyHeight = Math.max(parentsHeight, childrenHeight);

		if (spaceType == SpaceType.BetweenSiblingsAndCousins) {
			if (person.isChildless()) {
				familyHeight = parentsHeight;
				if (!isLastChild) familyHeight += betweenSiblingsSpace;
			} else if (!isLastChild)
				familyHeight =  Math.max(parentsHeight+betweenSiblingsSpace, childrenHeight+betwennCousisnsSpace);
		}
		
		return familyHeight;
	}
	
	private int drawChildren(Person person, int x, int y, int generation) {
		if (person.isChildless()) return 0;

		int childrenHeight = 0;
		
		int nameHeight = nameDisplayer.getHeight(person);
		int nameWidth  = nameDisplayer.getWidth(person);
		
		int verticalLineX = x + columnsWidths[generation] + lineMargin + minParentLineLength;
		int parentX = x + nameWidth + lineMargin;
		int arrowHeadX = verticalLineX + childArrowLength;
		
		int parentY = y+nameHeight/2;
		int arrowHeadY = 0;
		
		Point parentPoint = new Point(parentX, parentY);
		Point verticalLineTop = new Point(verticalLineX, parentY);
		
		painter.drawLine(parentPoint, verticalLineTop);
		
		for (int i=0; i<person.numberOfChildren(); i++) {
			Person child = person.getChild(i);
			
			arrowHeadY = y + childrenHeight + nameHeight/2;
			drawChildArrow(person, child, arrowHeadX, arrowHeadY, verticalLineX);
			
			childrenHeight += drawFamily(
					child,
					arrowHeadX + lineMargin,
					y + childrenHeight,
					generation + 1,
					i==person.numberOfChildren()-1);
			
			if (spaceType == SpaceType.OnlyBetweenSiblings)
				childrenHeight += betweenSiblingsSpace;
		}
		
		painter.drawVLineTo(verticalLineTop, arrowHeadY);

		return childrenHeight;
	}
	
	private void drawChildArrow(Person person, Person child, int arrowHeadX, int arrowHeadY, int verticalLineX) {
		Point arrowHead = new Point(arrowHeadX, arrowHeadY);
		Point linePoint = new Point(verticalLineX, arrowHeadY);
		
		painter.drawLine(linePoint, arrowHead);
		painter.drawArrowhead(arrowHead, Direction.RIGHT);
		drawMarriageNumber(child, person, verticalLineX, arrowHeadY);
	}
	
	private int drawSpouses(Person person, int x, int y) {
		int spousesHeight = 0;
		int nameHeight = nameDisplayer.getHeight(person);
		
		for (int i=0; i<person.numberOfMarriages(); i++)
			spousesHeight += drawSpouse(person.getSpouse(i), x, y + nameHeight + spousesHeight + betweenSpousesSpace);
		
		return spousesHeight;
	}
	
	private void drawMarriageNumber(Person child, Person parent, int lineLeft, int lineY) {
		if (parent.numberOfMarriages() < 2) return;
		
		String marriageNumber = child.parentsMarriageNumber(parent) + "";
		if (marriageNumber.equals("0")) return;

		MyColor oldColor = painter.getColor();
		MyFont  oldFont  = painter.getTextStyle();

		painter.setTextStyle(
				oldFont.getName(),
				oldFont.getStyle(),
				(int)(oldFont.getSize()*.75));
		int textHeight = painter.getTextHeight();
		int textWidth  = painter.getTextWidth(marriageNumber);
		
		int leftX  = lineLeft + 3;
		int textLeft   = leftX;
		int textBotton = (int) (lineY + Math.ceil(textHeight/2f));
		
		painter.setColor(MyColor.WHITE);
		painter.drawHLine(new Point(leftX, lineY), textWidth);
		painter.setColor(oldColor);
		
		painter.drawText(marriageNumber, new Point(textLeft, textBotton));
		painter.setTextStyle(oldFont);
	}
	
	private int drawSpouse(Person person, int x, int y)
	{
		int nameHeight = nameDisplayer.getHeight(person);
		
		draw(person, x+spouseIndentation, y);
		drawRings(x, y, spouseIndentation, nameHeight);
		
		return nameHeight + betweenSpousesSpace;
	}

	private void drawRings(int x, int y, int width, int height) {
		MyColor color = painter.getColor();
		final float commonPart = (float)0.3; 
		float ringsRatio = (float) (2.0-commonPart);
		float areaRatio  = width/height;
		int ringHeight, ringWidth;
		int ringsWidth;
		int x2;
		
		if (ringsRatio > areaRatio) {
			//width is the limit
			ringsWidth = width;
			ringWidth = ringHeight = (int) (ringsWidth/(2-commonPart));
		} else {
			//height is the limit
			ringWidth = ringHeight = height;
			ringsWidth = (int) (ringWidth*(2-commonPart));
		}
		
		painter.setColor(ringsColor);
		
		x += (width  - ringsWidth)/2;
		y += (height - ringHeight) /2;
		x2 = (int)(x+(ringWidth*(1-commonPart)));
		
		Point ring1 = new Point(x,  y);
		Point ring2 = new Point(x2, y);
		painter.drawRing(ring1, ring1.addVector(ringWidth, ringHeight));
		painter.drawRing(ring2, ring2.addVector(ringWidth, ringHeight));
		
		painter.setColor(color);
	}
}