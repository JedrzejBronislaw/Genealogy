package model.pgl.reader;

import java.util.List;

import lombok.Getter;
import model.Tree;

@Getter
class Relation {
	public enum Type {FATHER, MOTHER, CHILD, SPOUSE};
	
	private String subject;
	private Type type;
	private String object;
	private int number;
	private String date;
	private String place;

	public Relation(String subject, Type type, String object) {
		this.subject = subject;
		this.type = type;
		this.object = object;
	}
	public Relation(String subject, Type type, String object, int number) {
		this.subject = subject;
		this.type = type;
		this.object = object;
		this.number = number;
	}


	public void apply(Tree tree) {
		Relation r = this;
		
		if (r.type == Relation.Type.FATHER) tree.getPerson(r.object).setFather(tree.getPerson(r.subject)); else
		if (r.type == Relation.Type.MOTHER) tree.getPerson(r.object).setMother(tree.getPerson(r.subject)); else
		if (r.type == Relation.Type.CHILD)  tree.getPerson(r.object).addChild(tree.getPerson(r.subject)); else
		if (r.type == Relation.Type.SPOUSE) {
				tree.getPerson(r.object).addMarriage(tree.getPerson(r.subject));
				tree.getPerson(r.object).addWeddingDate(tree.getPerson(r.subject), r.date);
				tree.getPerson(r.object).addWeddingVenue(tree.getPerson(r.subject), r.place);
		}
	}
	
	public boolean equals(String object, Type relationType, int number) {
		return
			this.object.equals(object) &&
			this.type.equals(relationType) &&
			this.number == number;
	}

	
	static void addDateToRelation(List<Relation> list, String object, Type type, int number, String date) {
		Relation relation = find(list, object, type, number);
		if (relation != null) relation.date = date;

	}

	static void addPlaceToRelation(List<Relation> list, String object, Type type, int number, String place) {
		Relation relation = find(list, object, type, number);
		if (relation != null) relation.place = place;
	}
	
	private static Relation find(List<Relation> list, String object, Type type, int number) {
		for (Relation relation : list)
			if (relation.equals(object, type, number))
				return relation;

		return null;
	}
}
