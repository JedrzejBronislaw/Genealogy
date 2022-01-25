package treeGraphs.stdDescendantsTG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Person;

public class Children extends Area implements PersonLocationContainter {

//	private GraphParameters params = GraphParameters.ZERO;
	private List<Family> children;
	

	public Children(Person[] children, int generation, GraphParameters params) {
//		this.params = params;
		
		this.children = Arrays.asList(children).stream()
				.map(person -> new Family(person, generation, params))
				.collect(Collectors.toList());
		
		setEvents();
	}

	private void setEvents() {
		children.forEach(child -> {
			child.setOnChangeHeight(() -> {
				setHeight(setYCoordsAndReturnHeight());
			});
			
			child.setOnChangeWidth(() -> {
				setWidth(computeWidth());
			});
		});

		setOnChangeX(() -> {
			children.forEach(child -> child.setX(getX()));
		});
		setOnChangeY(() -> {
			setYCoordsAndReturnHeight();
		});
	}
	
	private int computeWidth() {
		return children.stream().map(child -> child.getWidth()).max(Integer::compare).get();
	}

	int setYCoordsAndReturnHeight() {
		int internalY = 0;
		
		for(Family child : children) {
			child.setY(getY() + internalY);
			internalY += child.getHeight(); //+space
		}
		
		return internalY;
	}

	@Override
	public Stream<PersonLocation> getPersonLocationsStream() {
		return children.stream().flatMap(child -> child.getPersonLocationsStream());
	}

	@Override
	public int flatSize() {
		return children.size();
	}
	@Override
	public int deepSize() {
		int numOfPersons = 0;
		
		for (Family child : children)
			numOfPersons += child.deepSize();
		
		return numOfPersons;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("   children");
		sb.append("(");
		sb.append(flatSize());
		sb.append("):");
		
		children.forEach(child -> {
			sb.append(System.lineSeparator());
			sb.append("     - ");
			sb.append(child.toString());
		});
		
		return sb.toString();
	}
}
