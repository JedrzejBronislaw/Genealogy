package model.familyRelations;

import java.util.List;

import lombok.NoArgsConstructor;
import model.Marriage;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeEditor;
import model.tools.ManWoman;

@NoArgsConstructor
public class RelationEditor {

	private TreeEditor tree = null;

	public RelationEditor(Tree tree) {
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
		ManWoman manWoman = new ManWoman(parentA, parentB);
		if (!manWoman.success()) return false;
		
		Person father = manWoman.getMan();
		Person mother = manWoman.getWoman();

		setMotherChildRel(mother, child);
		setFatherChildRel(father, child);
		
		return true;
	}

	public boolean setParentChildrenRel(Person parent, List<Person> children) {
		if (parent == null || children == null) return false;
		if (parent.getSex() != Sex.MAN &&
			parent.getSex() != Sex.WOMAN) return false;
		
		delChildrenRelations(parent);
		
		if (parent.getSex() == Sex.MAN)
			children.forEach(child -> {
				delFatherRelation(child);
				setFatherChildRel(parent, child);
			});
		
		if (parent.getSex() == Sex.WOMAN)
			children.forEach(child -> {
				delMotherRelation(child);
				setMotherChildRel(parent, child);
			});
		
		return true;
	}

	public boolean delChildrenRelations(Person parent) {
		if (parent == null) return false;
		
		if (parent.getSex() == Sex.MAN)
			for (Person child : parent.getChildren())
				delFatherRelation(child);
		
		if (parent.getSex() == Sex.WOMAN)
			for (Person child : parent.getChildren())
				delMotherRelation(child);
		
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
		ManWoman manWoman = new ManWoman(spouseA, spouseB);
		if (!manWoman.success()) return false;
		
		Person husband = manWoman.getMan();
		Person wife = manWoman.getWoman();

		husband.addMarriage(wife, date, place);
		wife.addMarriage(husband, date, place);
		
		addToTree(husband, wife);
		
		return true;
	}

	public boolean delMarriagesRel(Person person) {
		Marriage[] marriages = person.getMarriages();
		
		for (Marriage marriage : marriages)	delMarriageRel(marriage);
		
		return true;
	}

	public boolean delMarriageRel(Marriage marriage) {
		return delMarriageRel(marriage.getWife(), marriage.getHusband());
	}

	public boolean delMarriageRel(Person person1, Person person2) {
		person2.delSpouseRelation(person1);
		person1.delSpouseRelation(person2);
		return true;
	}
	
	private void addToTree(Person... persons) {
		if (tree == null) return;
		
		for (Person person : persons)
			tree.addIfIsOutside(person);
	}
}
