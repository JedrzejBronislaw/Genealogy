package treeGraphs.stdDescendantsTG;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Person;

public interface PersonLocationContainter {

	Stream<PersonLocation> getPersonLocationsStream();
	
	default List<Person> getPersons() {
		return getPersonLocationsStream().map(p -> p.getPerson()).collect(Collectors.toList());
	}
	default List<PersonLocation> getPersonLocations() {
		return getPersonLocationsStream().collect(Collectors.toList());
	}
	

	int flatSize();
	int deepSize();
	default int size() {return deepSize();}
}
