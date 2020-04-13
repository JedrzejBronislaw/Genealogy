package treeGraphs;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import model.Person;
import other.PersonDetails;

public class DrawingDescendantTreeGraphCalculation {
	
	enum BranchLengthType {Deterministic, Random}
	enum BranchAngleType {Deterministic, Random}
	
	public class TreeNode
	{
		@Getter @Setter private int x, y;
		@Getter @Setter private int generation;
		@Getter @Setter private Person person;
		@Getter @Setter private List<TreeNode> links;
		
		public TreeNode(Person person, int generation, int x, int y) {
			this.person = person;
			this.generation = generation;
			this.x = x;
			this.y = y;
			links = new ArrayList<TreeNode>();
		}		
	}

	private int InitialDistanceBetweenGenerations = 50;
	
	private Person mainPerson;
	private List<TreeNode> results;
	private float startAngle = 155;
	private float wholeAngle = 130;
	private int middleOffsetX = 0, middleOffsetY = 0;
	private boolean calculated = false;
	private BranchLengthType branchLength = BranchLengthType.Deterministic;
	private BranchAngleType branchAngle = BranchAngleType.Deterministic;

	private float branchLengthDifferenceToParent = -5;

	private int height = 0;
	private int width = 0;
	private int offsetX = 0;
	private int offsetY = 0;
	
	
	
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
	
	public DrawingDescendantTreeGraphCalculation setBranchLength(BranchLengthType branchLength) {
		this.branchLength = branchLength;
		return this;
	}
	public DrawingDescendantTreeGraphCalculation setBranchAngle(BranchAngleType branchAngle) {
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
	
	
	public DrawingDescendantTreeGraphCalculation(Person mainPerson) {
		this.mainPerson = mainPerson;
		results = new ArrayList<TreeNode>();
	}

	public List<TreeNode> get() {
		if (!calculated)
			calculate();
		
		return results;
	}
	
	
	private void calculate()
	{
		drawBranch(mainPerson, 1, 0, 0, 0);
		computeDimension();
		
		for (TreeNode node : results)
		{
			node.x += middleOffsetX + offsetX;
			node.y += middleOffsetY + offsetY;
		}
		
		calculated = true;
	}
	

	private void computeDimension() {

		int maxX, minX, maxY, minY;
		maxX = minX = maxY = minY = 0;
		
		
		for (TreeNode mode : results){
			if (mode.x > maxX) maxX = mode.x; 
			else if (mode.x < minX) minX = mode.x;

			if (mode.y > maxY) maxY = mode.y; 
			else if (mode.y < minY) minY = mode.y;
		}

		middleOffsetX = -minX;
		middleOffsetY = -minY;
		
		height  = maxY-minY;
		width = maxX-minX;
	}
	
	public int getHeight() {return height;}
	public int getWidth() {return width;}
	public Dimension getDimension() {return new Dimension(width, height);}
	public Point getRoot() {return new Point(middleOffsetX, middleOffsetY);}

	private TreeNode drawBranch(Person person, double angleOffset, int parentX, int parentY, int generation)
	{
		int width = PersonDetails.descendantsBranchesWidth(mainPerson);//-1;
		int partialWidth = 0;
		int currentWidth = 0;
		Person child;
		Point point;
		double distance;
		double angle = 0; 
		
		Random r = new Random();
		
		TreeNode treeNode = new TreeNode(person, generation, parentX, parentY);
		results.add(treeNode);		

		for (int i=0; i<person.numberOfChildren(); i++)
		{
			child = person.getChild(i);
			currentWidth = PersonDetails.descendantsBranchesWidth(child);
			distance = (generation+1) * InitialDistanceBetweenGenerations;
			
			if (branchLength == BranchLengthType.Deterministic)
				distance += generation*(generation+1)/2*branchLengthDifferenceToParent ;
			else if (branchLength == BranchLengthType.Random)
				distance -= r.nextInt(InitialDistanceBetweenGenerations/2);
			
			if (branchAngle == BranchAngleType.Deterministic)
				angle = angleOffset - (partialWidth+((double)currentWidth/2))/width;
			else if (branchAngle == BranchAngleType.Random)
				angle = angleOffset - (partialWidth+((double)currentWidth*(r.nextDouble()/2+0.25)))/width;
			
			
			point = findPoint(distance, angle);
			
			treeNode.getLinks().add(drawBranch(child, angleOffset - (double)(partialWidth)/width, point.x, point.y, generation+1));
			
			partialWidth += currentWidth;
		}
		return treeNode;
	}
	public static double degreeToRad(double degree)
	{
		return  (degree/360*2*Math.PI) % (2*Math.PI);
	}
	
	public static double circlePercentageToRad(double percentage)
	{
		return percentage*2*Math.PI;
	}
	
	public double areaPercentageToRad(double percentage)
	{
		percentage *= wholeAngle/360;
		percentage  = circlePercentageToRad(percentage);
		percentage += degreeToRad(startAngle-wholeAngle);
		
		return percentage;
	}
	
	public static Point radAngleToPoint(double angle, double distace, Point point)
	{
		int x = (int) (distace *  Math.cos(angle)) + point.x;
		int y = (int) (distace * -Math.sin(angle)) + point.y;
		
		return new Point(x,y);
	}
	
	private Point findPoint(double distance, double angle)
	{
		angle = areaPercentageToRad(angle);
		
		Point point = radAngleToPoint(angle, distance, new Point(middleOffsetX, middleOffsetY));

		return point;
	}

	public DrawingDescendantTreeGraphCalculation setOffset(int offsetX,
			int offsetY) {

		this.offsetX = offsetX;
		this.offsetY = offsetY;

		return this;
	}
}
