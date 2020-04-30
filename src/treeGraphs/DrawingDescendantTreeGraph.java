package treeGraphs;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import other.PersonDetails;
import tools.Gradient;
import treeGraphs.DrawingDescendantTreeGraphCalculation.TreeNode;
import treeGraphs.painter.Handle;
import treeGraphs.painter.MultiHandle;
import treeGraphs.painter.Point;

public class DrawingDescendantTreeGraph extends TreeGraph {

//	private float startAngle = 155;
	private float wholeAngle = 130;
	
//	public void setStartAngle(float angleInDegrees) {
//		this.startAngle = angleInDegrees;
//	}
	public void setWholeAngle(float angleInDegrees) {
		this.wholeAngle = angleInDegrees;
	}
	public void setSymmetricalAngle(float widthInDegrees) {
		this.wholeAngle = widthInDegrees;
	}
	
	public DrawingDescendantTreeGraph() {
		setSymmetricalAngle(150);
	}
	
	@Override
	public void draw() {
		int marginX = 50, marginY = 50;
		
		painter.startDrawing();
			
		painter.drawText(mainPerson.nameSurname(), new Point(10, 20));
		painter.drawText("Pokoleñ potomków: " + mainPerson.descendantGenerations()+"", new Point(10, 30));
		painter.drawText("Szerokoœæ: " + PersonDetails.descendantsBranchesWidth(mainPerson)+"", new Point(10, 40));
		painter.drawText("Liczba dzieci: " + mainPerson.numberOfChildren(), new Point(10, 50));		
		
		painter.setLineStyle(3);
		painter.setColor(new Color(150, 75, 0));
		
		DrawingDescendantTreeGraphCalculation calculation = new DrawingDescendantTreeGraphCalculation(mainPerson)
				.setSymmetricalAngle(wholeAngle)
				.setSpaceBetweenYoungestGeneration(15)
				.setOffset(marginX, marginY);
		List<TreeNode> plan = calculation.get();

//		Gradient gradinent = new Gradient(new Color(75, 38, 0), Color.GREEN);
		Gradient gradinent = new Gradient(Color.BLACK, new Color(150, 75, 0));
		Handle handle;
		
		for (TreeNode node : plan)
			for (TreeNode child : node.getLinks())
			{
				painter.setColor(gradinent.getIntermediateColor((float)node.getGeneration()/mainPerson.descendantGenerations()));
				painter.setLineStyle(5-5*node.getGeneration()/mainPerson.descendantGenerations());
				painter.drawLine(new Point(node.getX(), node.getY()), new Point(child.getX(), child.getY()));
				painter.setLineStyle(1);
			}
		
		for (TreeNode node : plan) {
			if (node.getLinks().size() == 0) {
				handle = drawLeaf(node.getX(), node.getY(), Color.GREEN);
				setHandleEvents(handle, node.getPerson());
			}
			//TODO click handling for not-leafs
		}

		Dimension dimension = calculation.getDimension();

		height  = dimension.height + marginY * 2;
		width = dimension.width  + marginX * 2;	
	}
	

	private Handle drawLeaf(int x, int y, Color color)
	{
		Color oldColor = painter.getColor();
		int lineThickness = painter.getLineThickness();
		Handle h1, h2;
		
		painter.setColor(color);
		painter.setLineStyle(1);
		Point center = new Point(x, y);
		h1 = painter.drawCircle(center, 5);
		h2 = painter.drawRectangle(center, center.addVector(5, -5));
		
		painter.setColor(oldColor);
		painter.setLineStyle(lineThickness);
		
		return new MultiHandle(h1, h2);
	}
	
}
