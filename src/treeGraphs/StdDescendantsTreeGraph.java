package treeGraphs;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Person;
import treeGraphs.painter.Direction;
import treeGraphs.painter.MyColor;
import treeGraphs.painter.MyFont;
import treeGraphs.painter.MyFont.Style;
import treeGraphs.painter.Point;

public class StdDescendantsTreeGraph extends TreeGraph{
	
	@AllArgsConstructor
	private static class Coords {
		int arrowY;
		int familyHeight;
		
		static final Coords EMPTY = new Coords(0, 0);
		
		public boolean isEmpty() {
			return familyHeight == 0;
		}
	}


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
	
	//marriage number
	private static final int beforeMarriageNumLineLength = 2;
	private static final int marriageNumSpace = 5;
	private static final int marriageNumMargin = 2;
	private static final int marriageNumOffset = beforeMarriageNumLineLength + marriageNumMargin;
	private static final int afterMarriageNumLineLength = childArrowLength - marriageNumOffset - marriageNumSpace;

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
		
		width  = computeWidths();
		height = drawFamily(mainPerson, marginesX, marginesY).familyHeight;
		
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
	
	private Coords drawFamily(Person person, int x, int y) {
		return drawFamily(person, x, y, 0, true);
	}
	private Coords drawFamily(Person person, int x, int y, int generation, boolean isLastChild) {
		
		int nameHeight = nameDisplayer.getHeight(person);
		
		Coords childrenCoords = drawChildren(person, x, y, generation);
		

		int personY = (childrenCoords.isEmpty()) ? y : childrenCoords.arrowY-nameHeight/2;
		draw(person, x, personY);
		int spousesHeight  = drawSpouses(person, x, personY + nameHeight + betweenSpousesSpace);

		int personHeight = y - personY + nameHeight;
		int familyHeight = computeFamilyHeight(person, isLastChild, generation, personHeight, spousesHeight, childrenCoords.familyHeight);

		return new Coords(personY + nameHeight/2, familyHeight);
	}
	
	private int drawSpouses(Person person, int x, int y) {
		int spousesHeight = 0;
		
		for (int i=0; i<person.numberOfMarriages(); i++)
			spousesHeight += drawSpouse(person.getSpouse(i), x, y + spousesHeight);
		
		return spousesHeight;
	}
	
	private int drawSpouse(Person person, int x, int y)
	{
		int nameHeight = nameDisplayer.getHeight(person);
		
		draw(person, x+spouseIndentation, y);
		drawRings(x, y, spouseIndentation, nameHeight);
		
		return nameHeight + betweenSpousesSpace;
	}

	private void drawRings(int x, int y, final int width, final int height) {
		final MyColor oldColor;
		final float commonPart = .3f;
		final float ringsRatio = 2 - commonPart;
		final float areaRatio  = width / height;
		
		final int ringHeight, ringWidth;
		final int ringsWidth;
		final int x2;
		
		if (ringsRatio > areaRatio) {
			//width is the limit
			ringsWidth = width;
			ringWidth  = ringHeight = (int)(ringsWidth / ringsRatio);
		} else {
			//height is the limit
			ringWidth  = ringHeight = height;
			ringsWidth = (int)(ringWidth * ringsRatio);
		}
		
		oldColor = painter.changeColor(ringsColor);
		
		x += (width  - ringsWidth) / 2;
		y += (height - ringHeight) / 2;
		x2 = (int)(x + (ringWidth * (1-commonPart)));
		
		Point ring1 = new Point(x,  y);
		Point ring2 = new Point(x2, y);
		painter.drawRing(ring1, ring1.addVector(ringWidth, ringHeight));
		painter.drawRing(ring2, ring2.addVector(ringWidth, ringHeight));
		
		painter.setColor(oldColor);
	}
	
	private Coords drawChildren(Person person, int x, int y, int generation) {
		if (person.isChildless()) return Coords.EMPTY;

		int childrenHeight = 0;
		
		int nameWidth  = nameDisplayer.getWidth(person);
		
		int verticalLineX = x + columnsWidths[generation] + lineMargin + minParentLineLength;
		int parentX = x + nameWidth + lineMargin;
		int arrowheadX = verticalLineX + childArrowLength;
		
		int arrowheadY = 0;
		
		int firstChildArrowY = 0;
		
		for (int i=0; i<person.numberOfChildren(); i++) {
			Person child = person.getChild(i);
			
			Coords coords = drawFamily(
					child,
					arrowheadX + lineMargin,
					y + childrenHeight,
					generation + 1,
					i==person.numberOfChildren()-1);
			
			arrowheadY = coords.arrowY;
			drawChildArrow(person, child, arrowheadX, arrowheadY, verticalLineX);
			
			if (i == 0)
				firstChildArrowY = arrowheadY;

			childrenHeight += coords.familyHeight;
					
			if (spaceType == SpaceType.OnlyBetweenSiblings)
				childrenHeight += betweenSiblingsSpace;
		}
		
		int parentY = firstChildArrowY;
		Point parentPoint = new Point(parentX, parentY);
		Point verticalLineTop = new Point(verticalLineX, parentY);
		
		painter.drawLine(parentPoint, verticalLineTop);
		painter.drawVLineTo(verticalLineTop, arrowheadY);

		return new Coords(firstChildArrowY, childrenHeight);
	}

	private void drawChildArrow(Person person, Person child, int arrowheadX, int arrowheadY, int verticalLineX) {
		Point arrowhead = new Point(arrowheadX, arrowheadY);
		Point linePoint = new Point(verticalLineX, arrowheadY);
		
		if(drawMarriageNumber(child, person, verticalLineX, arrowheadY)) {
			painter.drawHLine(linePoint, beforeMarriageNumLineLength);
			painter.drawHLine(arrowhead, -afterMarriageNumLineLength);
		} else
			painter.drawLine(linePoint, arrowhead);
			
		painter.drawArrowhead(arrowhead, Direction.RIGHT);
	}
	
	private boolean drawMarriageNumber(Person child, Person parent, int lineLeft, int lineY) {
		if (parent.numberOfMarriages() < 2) return false;
		
		String marriageNumber = child.parentsMarriageNumber(parent) + "";
		if (marriageNumber.equals("0")) return false;

		int oldFontSize  = painter.changeTextSize(.75);
		
		int textHeight = painter.getTextHeight();
		int textLeft   = lineLeft + marriageNumOffset;
		int textBotton = (int) (lineY + Math.ceil(textHeight/2f));
		
		painter.drawText(marriageNumber, new Point(textLeft, textBotton));
		painter.setTextSize(oldFontSize);
		
		return true;
	}
	
	private int computeFamilyHeight(Person person, boolean isLastChild, int generation, int personHeight, int spousesHeight, int childrenHeight) {
		int parentsHeight = personHeight + spousesHeight;
		int familyHeight = Math.max(parentsHeight, childrenHeight);
		
		
		if (spaceType == SpaceType.OnlyBetweenSiblings) {
			if (generation == 0)
				familyHeight = Math.max(parentsHeight, childrenHeight - betweenSiblingsSpace);
		}
		
		
		if (spaceType == SpaceType.BetweenSiblingsAndCousins) {
			if (person.isChildless()) {
				familyHeight = parentsHeight;
				if (!isLastChild) familyHeight += betweenSiblingsSpace;
			} else if (!isLastChild)
				familyHeight =  Math.max(parentsHeight+betweenSiblingsSpace, childrenHeight+betwennCousisnsSpace);
		}
		
		
		return familyHeight;
	}
}
