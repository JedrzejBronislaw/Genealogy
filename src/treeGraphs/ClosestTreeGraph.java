package treeGraphs;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import lombok.Setter;
import model.Person;

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
	private static final int arrowheadWidth = 3;
	private static final int arrowheadHeight  = 5;

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
	public void draw(Graphics2D g) {
		g.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		clickMap.clear();
		if (mainPerson == null)
			return;
		
		if(makeCalculations)
		{
			calculations(g);
			makeCalculations = false;
		}
		
		String name = mainPerson.nameSurname();
		Person[] siblings = mainPerson.getSiblings();
		
		
		g.setFont(mainPersonFont);
		g.drawString(name, xName, yName);
		
		g.setFont(familyFont);

		drawLines(g, siblings.length > 0);
		drawParents(g, areaWidth);
		drawSiblings(g, siblings, marginX+areaWidth-siblingsWidth, yName);
		drawSpouses(g, xName+spouceIndentation, yName);
		drawChildren(g, xName+childIndentation, yName+aboveChildrenSpace-spaceBetweenChildren+spousesHeight);		
	}
	
	private void calculations(Graphics2D g)
	{
		Person mother = mainPerson.getMother();
		Person father = mainPerson.getFather();
		Person[] siblings = mainPerson.getSiblings();
		String name = mainPerson.nameSurname();
		String motherName  = (mother != null)  ? mother.nameSurname()  : "";
		String fatherName = (father != null) ? father.nameSurname() : "";
		
		
		g.setFont(mainPersonFont);
		FontMetrics fm = g.getFontMetrics();
		nameHeight  = fm.getAscent()-fm.getDescent();
		nameWidth = fm.stringWidth(name);
		
		xName = marginX;
		yName = marginY+nameHeight;
		if ((father != null) || (mother != null))
		yName += underParentsSpace;
		
		g.setFont(familyFont);
		fm = g.getFontMetrics();
		textHeight = fm.getAscent()-fm.getDescent();
		motherNameWidth = fm.stringWidth(motherName);
		fatherNameWidth = fm.stringWidth(fatherName);

		siblingsWidth = siblingsWidth(g, siblings);
		siblingsHeight  = siblings.length * (textHeight+spaceBetweenSiblings);
		spousesWidth = spousesWidth(g);
		spousesHeight  = mainPerson.numberOfMarriages() * (textHeight+spaceBetweenSpouses);
		childrenWidth = childrenWidth(g);
		childrenHeight  = mainPerson.numberOfChildren() * (textHeight+spaceBetweenChildren)-spaceBetweenChildren;
		
		areaWidth = nameWidth;// + 100;
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
	
	private void drawLines(Graphics2D g, boolean hasSiblings)
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
			g.drawLine(xFromFather, yLineTop, xLine, yLineTop);
		if (mother != null)
			g.drawLine(xFromMother, yLineTop, xLine, yLineTop);

		//from parents line
		if ((father != null) || (mother != null))
		{
			g.drawLine(xLine, yLineTop, xLine, yLineBottom);
			drawArrowhead(g, xLine, yLineBottom);
		}
		
		//to siblings line
		if (hasSiblings)
		{
			int xAboveSiblings = marginX+areaWidth-siblingsWidth/2;
			if (xAboveSiblings < marginX+nameWidth+(areaWidth-nameWidth)/2)
				xAboveSiblings = marginX+nameWidth+(areaWidth-nameWidth)/2;
			int yAboveSiblings = yName+spaceBetweenSiblings-lineMargin;
			g.drawLine(xLine, yLineTop+(yLineBottom-yLineTop)/2, xAboveSiblings, yLineTop+(yLineBottom-yLineTop)/2);
			g.drawLine(xAboveSiblings, yLineTop+(yLineBottom-yLineTop)/2, xAboveSiblings, yAboveSiblings);
			drawArrowhead(g, xAboveSiblings, yAboveSiblings);
		}
		
		//to children line
		if (mainPerson.numberOfChildren() > 0)
		{
			int xToChildren = marginX+childIndentation+childrenWidth/2;
			int yToChildrenTop = yName+spousesHeight+lineMargin;
			int yToChildrenBottom = yName+spousesHeight+aboveChildrenSpace-lineMargin;
			g.drawLine(xToChildren, yToChildrenTop, xToChildren, yToChildrenBottom);
			drawArrowhead(g, xToChildren, yToChildrenBottom);
		}
	}
	
	private void drawArrowhead(Graphics2D g, int x, int y)
	{
		g.drawLine(x, y, x-arrowheadWidth, y-arrowheadHeight);
		g.drawLine(x, y, x+arrowheadWidth, y-arrowheadHeight);
	}
	
	private void drawParents(Graphics2D g, int areaWidth)
	{
		Person mother = mainPerson.getMother();
		Person father = mainPerson.getFather();
		String motherName  = (mother != null)  ? mother.nameSurname()  : "";
		String fatherName = (father != null) ? father.nameSurname() : "";

		
		g.drawString(fatherName, marginX, marginY+textHeight);
		g.drawString(motherName,  marginX+areaWidth-motherNameWidth, marginY+textHeight);
		if (father != null)
			clickMap.addArea(father, marginX, marginY+textHeight, marginX+fatherNameWidth, marginY);
		if (mother != null)
			clickMap.addArea(mother, marginX+areaWidth-motherNameWidth, marginY+textHeight, marginX+areaWidth, marginY);
	}
	
	private void drawSiblings(Graphics2D g, Person[] siblings, int x, int y)
	{
		Font f = g.getFont();
		g.setFont(new Font(f.getName(), Font.PLAIN, f.getSize()));
		
		FontMetrics fm = g.getFontMetrics();
		int height = fm.getAscent()-fm.getDescent();
		int width;
		
		for (int i=0; i<siblings.length; i++)
		{
			width = fm.stringWidth(siblings[i].nameSurname());
			g.drawString(siblings[i].nameSurname(), x, y+(i+1)*(height+spaceBetweenSiblings));
			clickMap.addArea(siblings[i], x, y+(i+1)*(height+spaceBetweenSiblings), x+width, y+(i+1)*(height+spaceBetweenSiblings)-height);
		}
		
		g.setFont(f);
	}
	
	private void drawSpouses(Graphics2D g, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int height = fm.getAscent()-fm.getDescent();
		int width;
		Person spouse;
		
		for (int i=0; i<mainPerson.numberOfMarriages(); i++)
		{
			spouse = mainPerson.getSpouse(i);
			width = fm.stringWidth(spouse.nameSurname());
			g.drawString(spouse.nameSurname(), x, y+(i+1)*(height+spaceBetweenSpouses));
			clickMap.addArea(spouse, x, y+(i+1)*(height+spaceBetweenSpouses), x+width, y+(i+1)*(height+spaceBetweenSpouses)-height);
		}		
	}
	
	private void drawChildren(Graphics2D g, int x, int y)
	{
		FontMetrics fm = g.getFontMetrics();
		int height = fm.getAscent()-fm.getDescent();
		int width;
		Person child;
		
		for (int i=0; i<mainPerson.numberOfChildren(); i++)
		{
			child = mainPerson.getChild(i);
			width = fm.stringWidth(child.nameSurname());
			g.drawString(child.nameSurname(), x, y+(i+1)*(height+spaceBetweenChildren));
			clickMap.addArea(child, x, y+(i+1)*(height+spaceBetweenChildren), x+width, y+(i+1)*(height+spaceBetweenChildren)-height);
		}		
	}

	private int siblingsWidth(Graphics2D g, Person[] sibilings) {
		Font f = g.getFont();
		g.setFont(new Font(f.getName(), Font.PLAIN, f.getSize()));
		
		FontMetrics fm = g.getFontMetrics();
		int maxWidth = 0;
		
		for(Person sibiling: sibilings)
			if (maxWidth < fm.stringWidth(sibiling.nameSurname()))
				maxWidth = fm.stringWidth(sibiling.nameSurname());

		g.setFont(f);
		return maxWidth;
	}

	private int spousesWidth(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		int maxWidth = 0;
		Person spouse;
		
		for (int i=0; i<mainPerson.numberOfMarriages(); i++)
		{
			spouse = mainPerson.getSpouse(i);
			if (maxWidth < fm.stringWidth(spouse.nameSurname()))
				maxWidth = fm.stringWidth(spouse.nameSurname());
		}
		return maxWidth;
	}

	private int childrenWidth(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		int maxWidth = 0;
		Person child;
		
		for (int i=0; i<mainPerson.numberOfChildren(); i++)
		{
			child = mainPerson.getChild(i);
			if (maxWidth < fm.stringWidth(child.nameSurname()))
				maxWidth = fm.stringWidth(child.nameSurname());
		}
		return maxWidth;
	}
}