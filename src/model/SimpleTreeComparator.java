package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleTreeComparator {
	private Tree tree1;
	private Tree tree2;
	private String[] ids;
	
	public boolean equals(Tree tree1, Tree tree2) {
		if (tree1 == tree2) return true;
		if (tree1 == null || tree2 == null) return false;
		
		this.tree1 = tree1;
		this.tree2 = tree2;
		
		if (!equalsMetadata())   return false;
		this.ids = tree1.getIDs();
		if (!equalsEachPerson()) return false;
		if (!equalsRelations())  return false;
		
		return true;
	}

	
	
	private boolean equalsMetadata() {
		if (!equalsDates()) return false;
		if (tree1.getNumberOfPersons() != tree2.getNumberOfPersons()) return false;
		if (!equalsCommonSurnames()) return false;
		
		return true;
	}
	
	private boolean equalsEachPerson() {
		Person person1;
		Person person2;
		
		for (int i=0; i<ids.length; i++) {
			person1 = tree1.getPerson(ids[i]);
			person2 = tree2.getPerson(ids[i]);
			if (!person1.equals(person2)) return false;
		}
		
		return true;
	}

	private boolean equalsRelations() {
		for (int i=0; i<ids.length; i++) {
			Person person1 = tree1.getPerson(ids[i]);
			Person person2 = tree2.getPerson(ids[i]);
			
			if (person1.numberOfChildren()  != person2.numberOfChildren())  return false;
			if (person1.numberOfMarriages() != person2.numberOfMarriages()) return false;

			if (!equalsParents(person1, person2)) return false;
			if (!equalsChildren(person1, person2)) return false;
			if (!equalsMarriages(person1, person2)) return false;;
		}
		
		return true;
	}

	
	private boolean equalsParents(Person person1, Person person2) {
		String father1 = tree1.getID(person1.getFather());
		String father2 = tree2.getID(person2.getFather());

		String mother1 = tree1.getID(person1.getMother());
		String mother2 = tree2.getID(person2.getMother());

		if (!equalsO(father1, father2)) return false;
		if (!equalsO(mother1, mother2)) return false;
		
		return true;
	}

	private boolean equalsChildren(Person person1, Person person2) {
		List<String> childrenIds1 = getChildrenIdList(person1, tree1);
		List<String> childrenIds2 = getChildrenIdList(person2, tree2);
		
		for(String id : childrenIds1)
			if (!childrenIds2.contains(id)) return false;
		
		return true;
	}

	private boolean equalsMarriages(Person person1, Person person2) {
		List<String> spouseIds1 = getSpousesIdList(person1, tree1);
		List<String> spouseIds2 = getSpousesIdList(person2, tree2);

		for(String id : spouseIds1)
			if (!spouseIds2.contains(id)) return false;
			else {
				Marriage m1 = tree1.getMarriage(person1, id);
				String place1 = m1.getPlace();
				String date1 = m1.getDate();

				Marriage m2 = tree2.getMarriage(person2, id);
				String place2 = m2.getPlace();
				String date2 = m2.getDate();

				if (!equalsO(place1, place2)) return false;
				if (!equalsO(date1,  date2))  return false;
			}
		
		return true;
	}

	
	private List<String> getSpousesIdList(Person person, Tree tree) {
		List<String> spouse = new ArrayList<>();

		for(int i=0; i<person.numberOfMarriages(); i++)
			spouse.add(tree.getID(person.getSpouse(i)));
		
		return spouse;
	}

	private List<String> getChildrenIdList(Person person, Tree tree) {
		List<String> childrenID = new ArrayList<>();
		Person[] children = person.getChildren();

		for(int i=0; i<children.length; i++)
			childrenID.add(tree.getID(children[i]));
		
		return childrenID;
	}

	
	private boolean equalsDates() {
		Date lo1 = tree1.getLastOpen();
		Date lo2 = tree2.getLastOpen();

		if (!equalsO(lo1, lo2)) return false;
		
		Date lm1 = tree1.getLastModification();
		Date lm2 = tree2.getLastModification();
		
		if (!equalsO(lm1, lm2)) return false;
		
		return true;
	}

	private boolean equalsCommonSurnames() {
		String[] names1 = tree1.getCommonSurnames();
		String[] names2 = tree2.getCommonSurnames();
		int length1 = names1.length;
		int length2 = names2.length;
		
		if (length1 != length2) return false;
		for (int i=0; i<length1; i++)
			if (!names1[i].equals(names2[i])) return false;
		
		return true;
	}
	
	
	private boolean equalsO(Object o1, Object o2) {
		if (o1 == null && o2 != null)      return false;
		if (o1 != null && !o1.equals(o2)) return false;
		
		return true;
	}
}
