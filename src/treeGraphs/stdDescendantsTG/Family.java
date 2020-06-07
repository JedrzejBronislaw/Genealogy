package treeGraphs.stdDescendantsTG;

import java.util.stream.Stream;

import model.Person;

public class Family extends Area implements PersonLocationContainter {

	private GraphParameters params = GraphParameters.ZERO;
	
	private PersonLocation person;
	private Spouses spouses;
	private Children children;

	
	public Family(Person person, int generation, GraphParameters params) {
		this.params = params;
		
		this.person = new PersonLocation(person, generation);
		spouses = new Spouses(person.getSpouses(), generation, params);
		children = new Children(person.getChildren(), generation+1, params);
		
		spouses.setX(getX() + params.getSpouseIndentation());
		setEvents();
	}

	private void setEvents() {
		person.setOnChangeWidth(() -> {
			children.setX(getX() + computePersonAndSpousesWidth() + params.getBetweenGenerationsSpace());
			setWidth(computeWholeFamilyWidth());
		});
		person.setOnChangeHeight(() -> {
			spouses.setY(person.getY() + person.getHeight());
			setHeight(compHeight());
		});
		
		
		spouses.setOnChangeWidth(() -> {
			children.setX(getX() + computePersonAndSpousesWidth() + params.getBetweenGenerationsSpace());
			setWidth(computeWholeFamilyWidth());
		});
		spouses.setOnChangeHeight(() -> {
			setHeight(compHeight());
		});
		
		
		children.setOnChangeWidth(() -> {
			setWidth(computeWholeFamilyWidth());
		});
		children.setOnChangeHeight(() -> {
			setHeight(compHeight());
		});
		
		
		setOnChangeX(() -> {
			person.setX(getX());
			children.setX(getX() + computePersonAndSpousesWidth() + params.getBetweenGenerationsSpace());
			spouses.setX(getX() + params.getSpouseIndentation());
		});
		setOnChangeY(() -> {
			person.setY(getY());
			children.setY(getY());
			spouses.setY(getY() + person.getHeight());
		});
	}

	private int computePersonAndSpousesWidth() {
		return Math.max(person.getWidth(), params.getSpouseIndentation() + spouses.getWidth());
	}
	private int computeWholeFamilyWidth() {
		return computePersonAndSpousesWidth() + children.getWidth();
	}
	private int compHeight() {
		return Math.max(person.getHeight() + spouses.getHeight(), children.getHeight());
	}

	
	public Person getPerson() {
		return person.getPerson();
	}

	@Override
	public Stream<PersonLocation> getPersonLocationsStream() {
		Stream<PersonLocation> spousesAndChildren = Stream.concat(spouses.getPersonLocationsStream(), children.getPersonLocationsStream());
		return Stream.concat(Stream.of(person), spousesAndChildren);
	}

	@Override
	public int flatSize() {
		return 1 + spouses.size() + children.flatSize();
	}
	@Override
	public int deepSize() {
		return 1 + spouses.size() + children.deepSize();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Family(g=");
		sb.append(person.getGeneration());
		sb.append("): ");
		sb.append(person.toString());
		sb.append(" (");
		sb.append(spouses.size()+1);
		sb.append("+");
		sb.append(children.flatSize());
		sb.append(")");
		sb.append(System.lineSeparator());
		
		if (spouses.size() > 0) {
			sb.append(spouses.toString());
			sb.append(System.lineSeparator());
		}
		if (children.flatSize() > 0) {
			sb.append(children.toString());
			sb.append(System.lineSeparator());
		}
		
		return sb.toString().trim();
	}
}
