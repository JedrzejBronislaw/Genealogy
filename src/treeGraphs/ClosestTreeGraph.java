package treeGraphs;

import model.Person;
import treeGraphs.painter.Direction;
import treeGraphs.painter.Handle;
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
	private static final MyFont sibilingsFont  = new MyFont("Arial", Style.ITALIC, 12);


	private int areaWidth;
	private int mainNameX, mainNameY;
	private int mainNameWidth, mainNameHeight;
	private int motherNameWidth, fatherNameWidth;
	private int textHeight, siblingHeight;
	private int siblingsWidth, siblingsHeight;
	private int spousesWidth, spousesHeight;
	private int childrenWidth, childrenHeight;
	
	boolean calculated = false;
	
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
		
		painter.startDrawing();
		
		if(!calculated) calculate();

		drawLines();
		
		painter.setTextStyle(mainPersonFont);
		drawMainPerson();

		painter.setTextStyle(familyFont);
		drawParents();
		drawSiblings();
		drawSpouses();
		drawChildren();
	}
	
	private void calculate() {
		Person mother = mainPerson.getMother();
		Person father = mainPerson.getFather();
		Person[] siblings = mainPerson.getSiblings();
		String name = mainPerson.nameSurname();
		String motherName = (mother != null) ? mother.nameSurname() : "";
		String fatherName = (father != null) ? father.nameSurname() : "";
		
		
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

		painter.setTextStyle(sibilingsFont);
		siblingHeight  = painter.getTextHeight();
		siblingsWidth  = siblingsWidth(siblings);
		siblingsHeight = siblings.length * (siblingHeight + spaceBetweenSiblings);
		
		painter.setTextStyle(familyFont);
		spousesWidth  = spousesWidth();
		spousesHeight = mainPerson.numberOfMarriages() * (textHeight + spaceBetweenSpouses);
		childrenWidth  = childrenWidth();
		childrenHeight = mainPerson.numberOfChildren() * (textHeight + spaceBetweenChildren) - spaceBetweenChildren;
		
		int ownFamilyWidth = Math.max(
				spouceIndentation + spousesWidth,
				childIndentation  + childrenWidth);
		areaWidth = Math.max(mainNameWidth, fatherNameWidth+motherNameWidth+minSpaceBetweenParents);
		areaWidth = Math.max(areaWidth, ownFamilyWidth + minSpaceBetweenOwnFamilyAndSiblings + siblingsWidth);
		
		height = Math.max(
				mainNameY + siblingsHeight,
				mainNameY + spousesHeight);
		if (!mainPerson.isChildless())
			height = Math.max(height, mainNameY + spousesHeight + aboveChildrenSpace + childrenHeight);

		width = areaWidth;
		
		height += marginY;	//not twice, because it is already in mainNameY
		width  += marginX * 2;
		
		calculated = true;
	}
	
	private void drawLines() {
		Person father = mainPerson.getFather();
		Person mother = mainPerson.getMother();
		boolean hasSiblings = mainPerson.getSiblings().length > 0;
		
		//between parents
		int fatherX = marginX + fatherNameWidth + lineMargin;
		int motherX = marginX + areaWidth - motherNameWidth - lineMargin;
		int betweenParentsX = middle(fatherX, motherX);
		int parentsMiddleHeight = marginY + textHeight / 2;
		int abovePersonY = marginY + parentsSpaceHeight - lineMargin;
		
		final Point betweenParentsPoint = new Point(betweenParentsX, parentsMiddleHeight);

		if (father != null) painter.drawHLineTo(betweenParentsPoint, fatherX);
		if (mother != null) painter.drawHLineTo(betweenParentsPoint, motherX);

		
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
		if (!mainPerson.isChildless()) {
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
		Handle fHandle, mHandle;
		Person mother = mainPerson.getMother();
		Person father = mainPerson.getFather();
		String motherName = (mother != null) ? mother.nameSurname() : "";
		String fatherName = (father != null) ? father.nameSurname() : "";

		int y = marginY + textHeight;
		fHandle = painter.drawText(fatherName, new Point(marginX, y));
		mHandle = painter.drawText(motherName, new Point(marginX + areaWidth - motherNameWidth, y));
		
		if (father != null) setHandleEvents(fHandle, father);
		if (mother != null) setHandleEvents(mHandle, mother);
	}
	
	private void drawSiblings() {
		int x = marginX + areaWidth - siblingsWidth;
		int y = mainNameY;
		Person[] siblings = mainPerson.getSiblings();
		
		MyFont oldFont = painter.getTextStyle();
		painter.setTextStyle(sibilingsFont);

		int height = painter.getTextHeight();
		int posY;
		Person sibling;
		Handle handle;
		
		for (int i=0; i<siblings.length; i++) {
			sibling = siblings[i];
			posY = y + (height + spaceBetweenSiblings) * (i+1);
			handle = painter.drawText(sibling.nameSurname(), new Point(x, posY));
			
			setHandleEvents(handle, sibling);
		}
		
		painter.setTextStyle(oldFont);
	}
	
	private void drawSpouses() {
		int x = mainNameX + spouceIndentation;
		int y = mainNameY;
		
		int height = painter.getTextHeight();
		int posY;
		Person spouse;
		Handle handle;
		
		for (int i=0; i<mainPerson.numberOfMarriages(); i++) {
			spouse = mainPerson.getSpouse(i);
			posY = y + (height + spaceBetweenSpouses) * (i+1);
			handle = painter.drawText(spouse.nameSurname(), new Point(x, posY));
			
			setHandleEvents(handle, spouse);
		}		
	}
	
	private void drawChildren() {
		int x = mainNameX + childIndentation;
		int y = mainNameY + spousesHeight + aboveChildrenSpace - spaceBetweenChildren;
		
		int height = painter.getTextHeight();
		int posY;
		Person child;
		Handle handle;
		
		for (int i=0; i<mainPerson.numberOfChildren(); i++) {
			child  = mainPerson.getChild(i);
			posY = y + (height + spaceBetweenChildren) * (i+1);
			handle = painter.drawText(child.nameSurname(), new Point(x, posY));

			setHandleEvents(handle, child);
		}		
	}

	private int siblingsWidth(Person[] sibilings) {
		MyFont oldFont = painter.getTextStyle();
		painter.setTextStyle(sibilingsFont);
		
		int maxWidth = 0;
		
		for (Person sibiling: sibilings)
			maxWidth = Math.max(
					maxWidth,
					painter.getTextWidth(sibiling.nameSurname()));

		painter.setTextStyle(oldFont);
		return maxWidth;
	}

	private int spousesWidth() {
		int maxWidth = 0;
		Person spouse;
		
		for (int i=0; i<mainPerson.numberOfMarriages(); i++) {
			spouse = mainPerson.getSpouse(i);
			maxWidth = Math.max(
					maxWidth,
					painter.getTextWidth(spouse.nameSurname()));
		}
		
		return maxWidth;
	}

	private int childrenWidth() {
		int maxWidth = 0;
		Person child;
		
		for (int i=0; i<mainPerson.numberOfChildren(); i++) {
			child = mainPerson.getChild(i);
			maxWidth = Math.max(
					maxWidth,
					painter.getTextWidth(child.nameSurname()));
		}
		
		return maxWidth;
	}
}
