package treeGraphs.stdDescendantsTG;

import java.util.List;

import model.Person;
import model.Sex;
import model.familyRelations.RelationEditor;
import model.random.RandomPerson;
import treeGraphs.painter.Handle;
import treeGraphs.painter.Point;
import treeGraphs.painter.nameDisplayers.NameDisplayer;

public class TestFamily {
	
	private static NameDisplayer displayer = new NameDisplayer() {
		
		@Override
		protected int width(Person person) {
			return 80;
		}
		
		@Override
		protected Handle printPerson(Person person, int x, int y) {
			return null;
		}
		
		@Override
		protected int height(Person person) {
			return 10;
		}
	};
	
	Person grandfather, grandmother;
	Person daughter, son;
	Person daughtersHusband, sonsWife;
	Person granddaughter1, granddaughter2, granddaughter3, grandson;
	Person granddaughter1Husband, granddaughter3Husband;
	Person grandGranddaughter, grandGrandson;

	
	Family family;
	private List<PersonLocation> personsLocations;
	Point[] expectedLocations;
	PersonLocation[] persons;
	
	
	private void prepareArrays() {
		expectedLocations = new Point[] {
				new Point(  0,  0),
				new Point( 20, 10),
				
				new Point(100,  0),
				new Point(120, 10),
				new Point(100, 20),
				new Point(120, 30),
				
				new Point(200, 20),
				new Point(200,  0),
				new Point(200, 40),
				new Point(200, 10),
				
				new Point(220, 30),
				new Point(220, 50),
				
				new Point(300, 20),
				new Point(300, 30)
		};
		
		persons = new PersonLocation[] {
				person(grandfather),
				person(grandmother),
				
				person(daughter),
				person(daughtersHusband),
				person(son),
				person(sonsWife),
				
				person(granddaughter1),
				person(granddaughter2),
				person(granddaughter3),
				person(grandson),
				
				person(granddaughter1Husband),
				person(granddaughter3Husband),
				
				person(grandGrandson),
				person(grandGranddaughter)
		};
	}
	
	private Person prepareFamily() {
		RandomPerson r = new RandomPerson();
		RelationEditor relationEditor = new RelationEditor();
		
		grandfather = r.generate(Sex.MAN);
		grandmother = r.generate(Sex.WOMAN);

		
		daughter = r.generate(Sex.WOMAN);
		daughtersHusband = r.generate(Sex.MAN);
		son = r.generate(Sex.MAN);
		sonsWife = r.generate(Sex.WOMAN);
		

		granddaughter1 = r.generate(Sex.WOMAN);
		granddaughter2 = r.generate(Sex.WOMAN);
		granddaughter3 = r.generate(Sex.WOMAN);
		grandson = r.generate(Sex.MAN);

		granddaughter1Husband = r.generate(Sex.MAN);
		granddaughter3Husband = r.generate(Sex.MAN);

		
		grandGrandson = r.generate(Sex.MAN);
		grandGranddaughter = r.generate(Sex.WOMAN);
		
		//marriages
		relationEditor.createMarriageRel(grandfather, grandmother);
		
		relationEditor.createMarriageRel(daughter, daughtersHusband);
		relationEditor.createMarriageRel(son, sonsWife);

		relationEditor.createMarriageRel(granddaughter1, granddaughter1Husband);
		relationEditor.createMarriageRel(granddaughter3, granddaughter3Husband);
		
		//children
		relationEditor.setParentsChildRel(grandfather, grandmother, daughter);
		relationEditor.setParentsChildRel(grandfather, grandmother, son);

		relationEditor.setParentsChildRel(son, sonsWife, granddaughter1);
		relationEditor.setParentsChildRel(son, sonsWife, granddaughter3);
		relationEditor.setParentsChildRel(daughter, daughtersHusband, granddaughter2);
		relationEditor.setParentsChildRel(daughter, daughtersHusband, grandson);

		relationEditor.setParentsChildRel(granddaughter1, granddaughter1Husband, grandGrandson);
		relationEditor.setParentsChildRel(granddaughter1, granddaughter1Husband, grandGranddaughter);
		
		
		return grandfather;
	}
	
	
	public void prepare() {
		FamilyCreator familyCreator = new FamilyCreator();
		GraphParameters params = new GraphParameters.GraphParametersBuilder().
				spouseIndentation(20).
				build();
		family = familyCreator.create(prepareFamily(), displayer, params);
		
		personsLocations = family.getPersonLocations();
		
		prepareArrays();
	}

	PersonLocation person(Person person) {
		return personsLocations.stream().filter(location -> location.getPerson() == person).findFirst().get();
	}
}
