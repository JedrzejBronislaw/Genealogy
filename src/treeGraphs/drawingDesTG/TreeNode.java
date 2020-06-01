package treeGraphs.drawingDesTG;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import model.Person;
import treeGraphs.painter.Point;

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
