package treeGraphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.List;

import other.PersonDetails;
import tools.Gradient;
import treeGraphs.DrawingDescendantTreeGraphCalculation.TreeNode;

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
//		this.startAngle = 180-(180-wholeAngle)/2;
	}
	
	public DrawingDescendantTreeGraph() {
		setSymmetricalAngle(150);
	}
	
	@Override
	public void draw(Graphics2D g) {
		int marginX = 50, marginY = 50;
		
		clickMap.clear();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawString(mainPerson.nameSurname(), 10, 20);
		g.drawString("Pokoleñ potomków: " + mainPerson.descendantGenerations()+"", 10, 30);
		g.drawString("Szerokoœæ: " + PersonDetails.descendantsBranchesWidth(mainPerson)+"", 10, 40);
		g.drawString("Liczba dzieci: " + mainPerson.numberOfChildren(), 10, 50);		
		
		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(150, 75, 0));
		
		DrawingDescendantTreeGraphCalculation calculation = new DrawingDescendantTreeGraphCalculation(mainPerson)
				.setSymmetricalAngle(wholeAngle)
				.setSpaceBetweenYoungestGeneration(15)
				.setOffset(marginX, marginY);
		List<TreeNode> plan = calculation.get();

//		Gradient gradinent = new Gradient(new Color(75, 38, 0), Color.GREEN);
		Gradient gradinent = new Gradient(Color.BLACK, new Color(150, 75, 0));
		
		for (TreeNode node : plan)
			for (TreeNode child : node.getLinks())
			{
				g.setColor(gradinent.getKolorPosredni((float)node.getGeneration()/mainPerson.descendantGenerations()));
				g.setStroke(new BasicStroke(5-5*node.getGeneration()/mainPerson.descendantGenerations(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g.drawLine(node.getX(), node.getY(), child.getX(), child.getY());
				g.setStroke(new BasicStroke(1));
			}
		
		for (TreeNode node : plan) {
			if (node.getLinks().size() == 0)
				drawLeaf(g, node.getX(), node.getY(), Color.GREEN);
			clickMap.addArea(node.getPerson(), node.getX() - 5, node.getY() - 5, node.getX() + 5, node.getY() + 5);
		}

		Dimension dimension = calculation.getDimension();
//		Point root = calculation.getRoot();

		height  = dimension.height + marginY * 2;
		width = dimension.width  + marginX * 2;	
	}
	

	private void drawLeaf(Graphics2D g, int x, int y, Color color)
	{
		Color oldColor = g.getColor();
		Stroke oldStroke = g.getStroke();
		
		g.setColor(color);
		g.setStroke(new BasicStroke(1));
		g.fillOval(x-5, y-5, 10, 10);
		g.fillRect(x, y-5, 5, 5);
//		g.drawLine(x-5, y-5, x+5, y+5);
//		g.drawLine(x-5, y+5, x+5, y-5);
		
		g.setColor(oldColor);
		g.setStroke(oldStroke);
	}
	


}
