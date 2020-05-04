package model.familyRelations;

import lombok.NoArgsConstructor;
import model.Person;
import model.Person.Sex;
import model.Tree;
import model.TreeEditor;

@NoArgsConstructor
public class Editor {

	private TreeEditor tree = null;

	public Editor(Tree tree) {
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
	
	
	private void addToTree(Person... persons) {
		if (tree == null) return;
		
		for (Person person : persons)
			tree.addIfIsOutside(person);
	}
}
