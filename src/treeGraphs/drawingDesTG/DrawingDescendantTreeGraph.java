package treeGraphs.drawingDesTG;

import java.awt.Dimension;
import java.util.List;

import lang.Internationalization;
import lombok.Setter;
import other.PersonDetails;
import treeGraphs.TreeGraph;
import treeGraphs.painter.Gradient;
import treeGraphs.painter.Handle;
import treeGraphs.painter.MultiHandle;
import treeGraphs.painter.MyColor;
import treeGraphs.painter.Point;

public class DrawingDescendantTreeGraph extends TreeGraph {

	@Setter
	private float wholeAngle = 150;

	private static final int marginX = 50;
	private static final int marginY = 50;

	private static final int detailsX = 10;
	private static final int detailsY = 10;
	private static final int detailsLineHeight = 10;

	private static final int maxBranchThickness = 5;
	
	private DrawingDescendantTreeGraphCalculation calculation;
	private List<TreeNode> plan;
	
	
	@Override
	public void draw() {
		painter.startDrawing();
		
		calculate();
		drawDetails();
		drawTree();

		setDimensions();
	}

	private void calculate() {
		calculation = new DrawingDescendantTreeGraphCalculation(mainPerson)
				.setSymmetricalAngle(wholeAngle)
				.setSpaceBetweenYoungestGeneration(15)
				.setOffset(marginX, marginY);
		plan = calculation.get();
	}
	
	private void drawDetails() {
		Point coords = new Point(detailsY, detailsX);
		
		coords = drawDetail(mainPerson.nameSurname(), coords);
		coords = drawDetail(Internationalization.get("descendant_generations") + ": " +calculation.generations(), coords);
		coords = drawDetail(Internationalization.get("tree_width") + ": " + PersonDetails.descendantsBranchesWidth(mainPerson), coords);
		coords = drawDetail(Internationalization.get("children_number") + ": " + mainPerson.numberOfChildren(), coords);
		coords = drawDetail(Internationalization.get("persons_number") + ": " + plan.size(), coords);
	}
	private Point drawDetail(String text, Point coords) {
		painter.drawText(text, coords);
		return coords.addVector(0, detailsLineHeight);
	}
	
	private void drawTree() {
		drawBranches();
		drawPersons();
	}

	private void drawBranches() {
		Gradient gradinent = new Gradient(MyColor.BLACK, MyColor.BROWN);
		
		for (TreeNode node : plan)
			for (TreeNode child : node.getLinks()) {
				float percent = (float) node.getGeneration() / calculation.generations();
				float fThickness = maxBranchThickness * (1-percent);

				int thickness = (int) Math.ceil(fThickness);
				
				painter.setColor(gradinent.getIntermediateColor(percent));
				painter.setLineStyle(thickness);
				painter.drawLine(node.getCoords(), child.getCoords());
			}
		painter.setLineStyle(1);
	}

	private void drawPersons() {
		Handle handle;
		
		for (TreeNode node : plan) {
			if (node.isLeaf()) {
				handle = drawLeaf(node.getCoords(), MyColor.GREEN);
				setHandleEvents(handle, node.getPerson());
			}
			//TODO click handling for not-leafs
		}
	}

	private Handle drawLeaf(final Point center, final MyColor color)
	{
		MyColor oldColor = painter.getColor();
		int oldThickness = painter.getLineThickness();
		Handle h1, h2;
		
		painter.setColor(color);
		painter.setLineStyle(1);
		h1 = painter.drawCircle(center, 5);
		h2 = painter.drawRectangle(center, center.addVector(5, -5));
		
		painter.setColor(oldColor);
		painter.setLineStyle(oldThickness);
		
		return new MultiHandle(h1, h2);
	}

	
	private void setDimensions() {
		Dimension dimension = calculation.getDimension();

		height = dimension.height + marginY * 2;
		width  = dimension.width  + marginX * 2;
	}
}
