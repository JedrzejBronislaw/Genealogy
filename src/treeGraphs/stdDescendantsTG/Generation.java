package treeGraphs.stdDescendantsTG;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Generation {

	private List<PersonLocation> persons = new LinkedList<>();
	
	public Generation() {}
	public Generation(PersonLocation...locations) {
		for(PersonLocation personLocation : locations) persons.add(personLocation);
	}
	public Generation(List<PersonLocation> generation) {
		generation.forEach(persons::add);
	}
	
	public int computeWidth() {
		int minX = persons.stream().map(pLoc -> pLoc.getLocation().getX()).min(Integer::compare).get();
		int maxX = persons.stream().map(pLoc -> pLoc.getLocation().getX() + pLoc.getWidth()).max(Integer::compare).get();
		
		return maxX - minX;
	}
	
	public void forEach(Consumer<PersonLocation> action) {
		persons.forEach(action::accept);
	}
	
	public void setPersonsWidth(int width) {
		persons.stream().filter(person -> !person.isSpouse()).forEach(person -> person.setWidth(width));
	}
}
