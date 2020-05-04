package model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TreeEditor {

	private final Tree tree;

	public Person getPerson(int id) {
		return tree.getPerson(id+"");
	}
	
	public String generateId() {
		int id = tree.numberOfPersons();
		
		while (getPerson(id) != null) id++;
		
		return id+"";
	}
	
	public boolean isInTree(Person person) {
		return tree.getID(person) != null;
	}
	
	public void addIfIsOutside(Person person) {
		if (isInTree(person)) return;
		
		tree.addPerson(generateId(), person);
	}
}
