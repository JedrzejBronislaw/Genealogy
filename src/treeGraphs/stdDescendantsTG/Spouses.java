package treeGraphs.stdDescendantsTG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Person;

public class Spouses extends Area implements PersonLocationContainter {

	private GraphParameters params = GraphParameters.ZERO;
	private List<PersonLocation> spouses;

	
	public Spouses(Person[] spouses, int generation, GraphParameters params) {
		this.params = params;
		
		this.spouses = Arrays.asList(spouses).stream()
				.map(person -> new PersonLocation(person, generation, true))
				.collect(Collectors.toList());
		
		setEvents();
	}

	private void setEvents() {
		spouses.forEach(spouse -> {
			spouse.setOnChangeHeight(() -> {
				setHeight(setYCoordsAndReturnHeight());
			});
			
			spouse.setOnChangeWidth(() -> {
				setWidth(computeWidth());
			});
		});

		setOnChangeX(() -> {
			spouses.forEach(spouse -> spouse.setX(getX()));
		});
		setOnChangeY(() -> {
			setYCoordsAndReturnHeight();
		});
	}

	private int computeWidth() {
		return spouses.stream().map(spouse -> spouse.getWidth()).max(Integer::compare).get();
	}

	int setYCoordsAndReturnHeight() {
		int internalY = 0;
		
		for(PersonLocation spouse : spouses) {
			spouse.setY(getY() + internalY);
			internalY += spouse.getHeight(); //+space
		}
		
		return internalY;
	}
	
	@Override
	public Stream<PersonLocation> getPersonLocationsStream() {
		return spouses.stream();
	}

	@Override
	public int size() {
		return spouses.size();
	}
	@Override
	public int flatSize() {
		return size();
	}
	@Override
	public int deepSize() {
		return size();
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("   spouses");
		sb.append("(");
		sb.append(size());
		sb.append("):");
		
		spouses.forEach(spouse -> {
			sb.append(System.lineSeparator());
			sb.append("     - ");
			sb.append(spouse.toString());
		});
		
		return sb.toString();
	}
}
