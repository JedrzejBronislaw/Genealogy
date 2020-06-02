package treeGraphs;

import model.Person;
import tools.Maxer;
import treeGraphs.painter.Direction;
import treeGraphs.painter.MyFont;
import treeGraphs.painter.MyFont.Style;
import treeGraphs.painter.Point;

public class ClosestTreeGraph extends TreeGraph {


	private static final int marginX = 20;
	private static final int marginY = 20;
	private static final int lineMargin = 5;
	private static final int minSpaceBetweenParents = 50;
	private static final int parentsSpaceHeight = 30;
	private static final int spaceBetweenSiblings = 7;
	private static final int spaceBetweenSpouses = 7;
	private static final int spaceBetweenChildren = 7;
	private static final int spouceIndentation = 20;
	private static final int childIndentation = 20;
	private static final int aboveChildrenSpace = 30;
	private static final int minSpaceBetweenOwnFamilyAndSiblings = 30;

	private static final MyFont mainPersonFont = new MyFont("Times", Style.BOLD,   25);
	private static final MyFont familyFont     = new MyFont("Arial", Style.BOLD,   12);
	private static final MyFont siblingsFont   = new MyFont("Arial", Style.ITALIC, 12);


	private int areaWidth;
	private int mainNameX, mainNameY;
	private int mainNameWidth, mainNameHeight;
	private int motherNameWidth, fatherNameWidth;
	private int textHeight, siblingHeight;
	private int siblingsWidth, siblingsHeight;
	private int spousesWidth, spousesHeight;
	private int childrenWidth, childrenHeight;
	
	private boolean calculated = false;
	
	private Person[] siblings;
	
	@Override
	public void setMainPerson(Person osoba) {
		this.mainPerson = osoba;
		calculated = false;
	}
	
	public ClosestTreeGraph() {}
	public ClosestTreeGraph(Person mainPerson) {
		this.mainPerson = mainPerson;
	}
	
	@Override
	public void draw() {
		if (mainPerson == null) return;
		MyFont oldFont = painter.getTextStyle();
		
		painter.startDrawing();
		
		if(!calculated) calculate();
		drawLines();
		drawPeople();
		
		painter.setTextStyle(oldFont);
	}

	private void drawPeople() {
		painter.setTextStyle(mainPersonFont);
		drawMainPerson();

		painter.setTextStyle(familyFont);
		drawParents();
		drawSpouses();
		drawChildren();
		
		painter.setTextStyle(siblingsFont);
		drawSiblings();
	}
	
	private void calculate() {
		siblings = mainPerson.getSiblings();
		String name = mainPerson.nameSurname();
		String motherName = (mother() == null) ? "" : mother().nameSurname();
		String fatherName = (father() == null) ? "" : father().nameSurname();
		
		
		painter.setTextStyle(mainPersonFont);
		mainNameHeight = painter.getTextHeight();
		mainNameWidth  = painter.getTextWidth(name);
		
		mainNameX = marginX;
		mainNameY = marginY + mainNameHeight;
		if (mainPerson.hasAnyParent())
			mainNameY += parentsSpaceHeight;
		
		painter.setTextStyle(familyFont);
		textHeight = painter.getTextHeight();
		
		motherNameWidth = painter.getTextWidth(motherName);
		fatherNameWidth = painter.getTextWidth(fatherName);

		spousesWidth  = maxWidth(mainPerson.getSpouses());
		spousesHeight = mainPerson.numberOfMarriages() * (textHeight + spaceBetweenSpouses);
		
		childrenWidth  = maxWidth(mainPerson.getChildren());
		childrenHeight = mainPerson.numberOfChildren() * (textHeight + spaceBetweenChildren) - spaceBetweenChildren;

		painter.setTextStyle(siblingsFont);
		siblingHeight  = painter.getTextHeight();
		siblingsWidth  = maxWidth(siblings);
		siblingsHeight = siblings.length * (siblingHeight + spaceBetweenSiblings);
		
		
		int ownFamilyWidth = Math.max(
				spouceIndentation + spousesWidth,
				childIndentation  + childrenWidth);
		areaWidth = Maxer.max(
				mainNameWidth,
				fatherNameWidth + motherNameWidth + minSpaceBetweenParents,
				ownFamilyWidth + minSpaceBetweenOwnFamilyAndSiblings + siblingsWidth
				);
		
		Maxer maxheight = new Maxer().add(
				mainNameY + siblingsHeight,
				mainNameY + spousesHeight);
		if (mainPerson.hasChild())
			maxheight.add(mainNameY + spousesHeight + aboveChildrenSpace + childrenHeight);

		width  = areaWidth       + marginX * 2;
		height = maxheight.get() + marginY;	//not twice, because it is already in mainNameY
		
		calculated = true;
	}
	
	private Person mother() {
		return mainPerson.getMother();
	}
	
	private Person father() {
		return mainPerson.getFather();
	}
	
	private void drawLines() {
		boolean hasSiblings = siblings.length > 0;
		
		//between parents
		int fatherX = marginX + fatherNameWidth + lineMargin;
		int motherX = marginX + areaWidth - motherNameWidth - lineMargin;
		int betweenParentsX = middle(fatherX, motherX);
		int parentsMiddleHeight = marginY + textHeight / 2;
		int abovePersonY = marginY + parentsSpaceHeight - lineMargin;
		
		final Point betweenParentsPoint = new Point(betweenParentsX, parentsMiddleHeight);

		if (father() != null) painter.drawHLineTo(betweenParentsPoint, fatherX);
		if (mother() != null) painter.drawHLineTo(betweenParentsPoint, motherX);

		
		//parents
		if (mainPerson.hasAnyParent()) {
			painter.drawVLineTo(betweenParentsPoint, abovePersonY);
			drawArrowhead(betweenParentsX, abovePersonY);
		}
		
		
		//siblings
		if (hasSiblings) {
			int aboveSiblingsX = Math.max(
					marginX + areaWidth - siblingsWidth / 2,
					marginX + middle(areaWidth, mainNameWidth));
			int aboveSiblingsY = mainNameY + spaceBetweenSiblings - lineMargin;
			int betweenPersonAndParents = middle(parentsMiddleHeight, abovePersonY);
			
			Point parentLineCentre   = new Point(betweenParentsX, betweenPersonAndParents);
			Point blendSibilingsLine = new Point(aboveSiblingsX,  betweenPersonAndParents);
			Point aboveSibilings     = new Point(aboveSiblingsX,  aboveSiblingsY);

			painter.drawLine(blendSibilingsLine, parentLineCentre);
			painter.drawLine(blendSibilingsLine, aboveSibilings);
			drawArrowhead(aboveSibilings);
		}
		
		
		//children
		if (mainPerson.hasChild()) {
			int aboveChildrenX   = marginX + childIndentation + childrenWidth / 2;
			int aboveChildrenY   = mainNameY + spousesHeight + aboveChildrenSpace - lineMargin;
			int topChildrenArrow = mainNameY + spousesHeight + lineMargin;
			
			final Point aboveChildren = new Point(aboveChildrenX, aboveChildrenY);
			
			painter.drawVLineTo(aboveChildren, topChildrenArrow);
			drawArrowhead(aboveChildren);
		}
	}

	private int middle(int coord1, int coord2) {
		return coord1 + (coord2 - coord1) / 2;
	}

	private void drawArrowhead(int x, int y) {
		drawArrowhead(new Point(x, y));
	}
	private void drawArrowhead(Point point) {
		painter.drawArrowhead(point, Direction.DOWN);
	}

	private void drawMainPerson() {
		String name = mainPerson.nameSurname();
		painter.drawText(name, new Point(mainNameX, mainNameY));
	}
	
	private void drawParents() {
		int y = marginY;
		draw(father(), marginX, y);
		draw(mother(), marginX + areaWidth - motherNameWidth, y);
	}
	
	private void drawSiblings() {
		int x = marginX + areaWidth - siblingsWidth;
		int y = mainNameY + spaceBetweenSiblings;
		
		int height = painter.getTextHeight();
		
		for (Person sibling : siblings) {
			draw(sibling, x, y);
			y += height + spaceBetweenSiblings;
		}
	}
	
	private void drawSpouses() {
		int x = mainNameX + spouceIndentation;
		int y = mainNameY + spaceBetweenSpouses;
		
		int height = painter.getTextHeight();

		for (Person spouse : mainPerson.getSpouses()) {
			draw(spouse, x, y);
			y += height + spaceBetweenSpouses;
		}		
	}
	
	private void drawChildren() {
		int x = mainNameX + childIndentation;
		int y = mainNameY + spousesHeight + aboveChildrenSpace;
		
		int height = painter.getTextHeight();
		
		for (Person child : mainPerson.getChildren()) {
			draw(child, x, y);
			y += height + spaceBetweenChildren;
		}
	}

	private int maxWidth(Person[] persons) {
		Maxer maxWidth = new Maxer();
		
		for (Person person : persons)
			maxWidth.add(painter.getTextWidth(person.nameSurname()));
		
		return maxWidth.get();
	}
}
