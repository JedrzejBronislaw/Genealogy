package treeGraphs;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import model.Person;
import other.PersonDetails;
import treeGraphs.painter.Point;

public class DrawingDescendantTreeGraphCalculation {
	
	enum BranchType {Deterministic, Random}
	
	public class TreeNode {
		@Getter private Point coords;
		@Getter private int generation;
		@Getter private Person person;
		@Getter private List<TreeNode> links;
		
		public TreeNode(Person person, int generation, Point coords) {
			this.person = person;
			this.generation = generation;
			this.coords = coords;
			links = new ArrayList<TreeNode>();
		}
		
		public boolean isLeaf() {
			return links.size() == 0;
		}

		public int getX() {
			return coords.getX();
		}
		public int getY() {
			return coords.getY();
		}

		public void move(int right, int down) {
			coords = coords.addVector(right, down);
		}

		public void addLink(TreeNode node) {
			getLinks().add(node);
		}
	}

	private int InitialDistanceBetweenGenerations = 50;
	
	private Person mainPerson;
	private List<TreeNode> results = new ArrayList<TreeNode>();
	private float startAngle = 155;
	private float wholeAngle = 130;
	private int middleOffsetX = 0, middleOffsetY = 0;
	private boolean calculated = false;
	private BranchType branchLength = BranchType.Deterministic;
	private BranchType branchAngle  = BranchType.Deterministic;

	private float branchLengthDifferenceToParent = -5;

	@Getter	private int height = 0;
	@Getter	private int width = 0;
	private int offsetX = 0;
	private int offsetY = 0;
	
	@Getter
	private int descendantGenerations;
	
	
	public DrawingDescendantTreeGraphCalculation setBranchLengthDifferenceToParent(int difference) {
		branchLengthDifferenceToParent = difference;
		return this;
	}
	
	public DrawingDescendantTreeGraphCalculation setInitialDistanceBetweenGenerations(int initialDistance) {
		this.InitialDistanceBetweenGenerations = initialDistance;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setSpaceBetweenYoungestGeneration(int distance) {
		float x = (distance-InitialDistanceBetweenGenerations)/(mainPerson.descendantGenerations()-1);
		
		branchLengthDifferenceToParent = x;
		return this;
	}
	
	public DrawingDescendantTreeGraphCalculation setBranchLength(BranchType branchLength) {
		this.branchLength = branchLength;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setBranchAngle(BranchType branchAngle) {
		this.branchAngle = branchAngle;
		return this;
	}
	
	public DrawingDescendantTreeGraphCalculation setStartAngle(float angleInDegrees) {
		this.startAngle = angleInDegrees;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setWholeAngle(float angleInDegrees) {
		this.wholeAngle = angleInDegrees;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setSymmetricalAngle(float widthInDegrees) {
		this.wholeAngle = widthInDegrees;
		this.startAngle = 180-(180-wholeAngle)/2;
		
		return this;
	}

	public DrawingDescendantTreeGraphCalculation setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		return this;
	}
	
	public Dimension getDimension() {
		return new Dimension(width, height);
	}
	
	public Point getRoot() {
		return new Point(middleOffsetX, middleOffsetY);
	}
	
	
	public DrawingDescendantTreeGraphCalculation(Person mainPerson) {
		this.mainPerson = mainPerson;
	}

	public List<TreeNode> get() {
		if (!calculated) calculate();
		return results;
	}
	
	
	private void calculate()
	{
		descendantGenerations = mainPerson.descendantGenerations();
		drawBranch(mainPerson, 1, new Point(0, 0), 0);
		computeDimension();
		
		results.forEach(node ->	node.move(
				middleOffsetX + offsetX,
				middleOffsetY + offsetY
			));
		
		calculated = true;
	}
	

	private void computeDimension() {
		int maxX, minX, maxY, minY;
		maxX = minX = maxY = minY = 0;
		
		for (TreeNode node : results){
			maxX = Math.max(maxX, node.getX());
			maxY = Math.max(maxY, node.getY());
			
			minX = Math.min(minX, node.getX());
			minY = Math.min(minY, node.getY());
		}

		middleOffsetX = -minX;
		middleOffsetY = -minY;
		
		height = maxY - minY;
		width  = maxX - minX;
	}

	private TreeNode drawBranch(Person person, double angleOffset, Point parentCoords, int generation) {
		int width = PersonDetails.descendantsBranchesWidth(mainPerson);
		int partialWidth = 0;
		int currentWidth = 0;
		Point point;
		double distance;
		double angle = 0; 
		
		Random r = new Random();
		
		TreeNode treeNode = new TreeNode(person, generation, parentCoords);
		results.add(treeNode);		

		for (Person child : person.getChildren()) {
			currentWidth = PersonDetails.descendantsBranchesWidth(child);
			distance = (generation+1) * InitialDistanceBetweenGenerations;
			
			if (branchLength == BranchType.Deterministic)
				distance += generation*(generation+1)/2*branchLengthDifferenceToParent;
			if (branchLength == BranchType.Random)
				distance -= r.nextInt(InitialDistanceBetweenGenerations/2);
				
			if (branchAngle == BranchType.Deterministic)
				angle = angleOffset - (partialWidth+((double)currentWidth/2))/width;
			if (branchAngle == BranchType.Random)
				angle = angleOffset - (partialWidth+((double)currentWidth*(r.nextDouble()/2+0.25)))/width;
			
			
			point = findPoint(distance, angle);
			
			treeNode.addLink(drawBranch(
					child,
					angleOffset - (double)partialWidth / width,
					point,
					generation+1
				));
			
			partialWidth += currentWidth;
		}
		return treeNode;
	}
	
	private Point findPoint(double distance, double angle) {
		angle = areaPercentageToRad(angle);
		return radAngleToPoint(angle, distance, new Point(middleOffsetX, middleOffsetY));
	}
	
	public double areaPercentageToRad(double percentage) {
		percentage *= wholeAngle/360;
		percentage  = circlePercentageToRad(percentage);
		percentage += degreeToRad(startAngle-wholeAngle);
		
		return percentage;
	}
	

	public int generations() {
		return descendantGenerations;
	}
	
	
	public static double degreeToRad(double degree) {
		return (degree/360 * 2*Math.PI) % (2*Math.PI);
	}
	
	public static double circlePercentageToRad(double percentage) {
		return percentage * 2*Math.PI;
	}

	public static Point radAngleToPoint(double angle, double distace, Point point) {
		int x = (int) (distace *  Math.cos(angle)) + point.getX();
		int y = (int) (distace * -Math.sin(angle)) + point.getY();
		
		return new Point(x, y);
	}
}
