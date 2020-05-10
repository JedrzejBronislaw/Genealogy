package model.familyRelations;

import java.util.List;

import lombok.NoArgsConstructor;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeEditor;

@NoArgsConstructor
public class Editor {

	private TreeEditor tree = null;

	public Editor(Tree tree) {
		if (tree != null)
			this.tree = new TreeEditor(tree);
	}
	
	public boolean setMotherChildRel(Person mother, Person child) {
		if (mother.getSex() != Sex.WOMAN) return false;
		
		delMotherRelation(child);
		child.setMother(mother);
		mother.addChild(child);
	
		addToTree(mother, child);
		
		return true;
	}

	public boolean setFatherChildRel(Person father, Person child) {
		if (father.getSex() != Sex.MAN) return false;
		
		delFatherRelation(child);
		child.setFather(father);
		father.addChild(child);

		addToTree(father, child);
		
		return true;
	}
	
	public boolean setParentsChildRel(Person parentA, Person parentB, Person child) {
		Person father = null;
		Person mother = null;

		Person[] parents = new Person[]{parentA, parentB};
		
		for(Person parent : parents) {
			if (parent.getSex() == Sex.MAN)   father = parent;
			if (parent.getSex() == Sex.WOMAN) mother = parent;
		}
		
		if (father == null || mother == null) return false;
		
		setMotherChildRel(mother, child);
		setFatherChildRel(father, child);
		
		return true;
	}

	public boolean setParentChildrenRel(Person parent, List<Person> children) {
		if (parent == null || children == null) return false;
		if (parent.getSex() != Sex.MAN &&
			parent.getSex() != Sex.WOMAN) return false;
		
		
		if (parent.getSex() == Sex.MAN) {
			for (Person child : parent.getChildren())
				delFatherRelation(child);
			
			children.forEach(child -> {
				delFatherRelation(child);
				setFatherChildRel(parent, child);
			});
		}
		
		if (parent.getSex() == Sex.WOMAN) {
			for (Person child : parent.getChildren())
				delMotherRelation(child);
		
			children.forEach(child -> {
				delMotherRelation(child);
				setMotherChildRel(parent, child);
			});
		}
		
		return true;
	}
	
	public boolean delMotherRelation(Person person) {
		Person mother = person.getMother();

		person.setMother(null);
		if(mother != null) mother.delChildRelation(person);
		
		return true;
	}
	
	public boolean delFatherRelation(Person person) {
		Person father = person.getFather();

		person.setFather(null);
		if(father != null) father.delChildRelation(person);
		
		return true;
	}
	
	public boolean createMarriageRel(Person spouseA, Person spouseB) {
		return createMarriageRel(spouseA, spouseB, null, null);
	}
	
	public boolean createMarriageRel(Person spouseA, Person spouseB, String date, String place) {
		Person husband = null;
		Person wife = null;

		Person[] spouses = new Person[]{spouseA, spouseB};
		
		for(Person spouse : spouses) {
			if (spouse.getSex() == Sex.MAN) husband = spouse;
			if (spouse.getSex() == Sex.WOMAN)  wife = spouse;
		}
		
		if (husband == null || wife == null) return false;
		
		husband.addMarriage(wife, date, place);
		wife.addMarriage(husband, date, place);
		
		addToTree(spouses);
		
		return true;
	}

	public boolean delMarriageRel(Person wife, Person husband) {
		husband.delSpouseRelation(wife);
		wife.delSpouseRelation(husband);
		return true;
	}
	
	private void addToTree(Person... persons) {
		if (tree == null) return;
		
		for (Person person : persons)
			tree.addIfIsOutside(person);
	}
}
