package model;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TreeExplorer {

	private final Tree tree;
	
	
	public List<Person> getLeaves()
	{
		Person[] all = tree.getAll();
		List<Person> leaves = new ArrayList<Person>();
		
		for (Person person : all)
			if (person.isChildless())
				leaves.add(person);
				
		return leaves;
	}

	public List<Person> getRoots()
	{
		return getRoots(false);
	}
	public List<Person> getRoots(boolean spouseToo)
	{
		Person[] all = tree.getAll();
		List<Person> roots = new ArrayList<Person>();

		for (Person person : all)
			if (person.hasNoParents())
				if (!spouseToo || areSpousesRoots(person))
					roots.add(person);

		return roots;
	}

	private boolean areSpousesRoots(Person person) {
		for (Person spouse : person.getSpouses())
			if (spouse.hasAnyParent()) return false;

		return true;
	}
}
