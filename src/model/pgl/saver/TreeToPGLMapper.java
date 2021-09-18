package model.pgl.saver;

import java.util.stream.Stream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Person;
import model.Tree;
import model.pgl.Section;
import model.pgl.PGLFields;
import model.pgl.PGL;

@RequiredArgsConstructor
public class TreeToPGLMapper {

	private Tree tree;
	private PGL pgl;

	public PGL map(@NonNull Tree tree) {
		pgl = new PGL();
		this.tree = tree;
		
		writeMainSection(tree, pgl.newSection(PGLFields.mainSectionName));
		Stream.of(tree.getIDs()).forEach(this::writePerson);
		
		return pgl;
	}

	private void writeMainSection(Tree tree, Section mainSection) {
		SectionWriter writer = new SectionWriter(mainSection);
		
		writer.saveProperty(PGLFields.lastOpen,         tree.getLastOpen());
		writer.saveProperty(PGLFields.lastModification, tree.getLastModification());
		writer.saveProperty(PGLFields.numberOfPersons,  tree.numberOfPersons());

		String[] names = tree.getCommonSurnames();
		for(int i=0; i<names.length; i++)
			writer.saveProperty(PGLFields.commonSurname(i+1), names[i]);
	}

	private boolean writePerson(String id) {
		Person person = tree.getPerson(id);
		SectionWriter writer = new SectionWriter(pgl.newSection(id));
		
		writer.saveProperty(PGLFields.firstName, person.getFirstName());
		writer.saveProperty(PGLFields.lastName,  person.getLastName());
		writer.saveProperty(PGLFields.alias,     person.getAlias());
		
		writer.saveProperty(PGLFields.birthDate,  person.getBirthDate());
		writer.saveProperty(PGLFields.birthPlace, person.getBirthPlace());
		writer.saveProperty(PGLFields.deathDate,  person.getDeathDate());
		writer.saveProperty(PGLFields.deathPlace, person.getDeathPlace());
		
		writer.saveProperty(person.getLifeStatus());
		writer.saveProperty(person.getSex());
		writer.saveProperty(PGLFields.baptismParish, person.getBaptismParish());
		writer.saveProperty(PGLFields.burialPlace,   person.getBurialPlace());
		
		writer.saveProperty(PGLFields.contact,  person.getContact());
		writer.saveProperty(PGLFields.comments, person.getComments());
		
		saveProperty(writer, PGLFields.father, person.getFather());
		saveProperty(writer, PGLFields.mother, person.getMother());
		
		saveMarriages(writer, person);
		saveChildren(writer, person);
		
		return true;
	}



	private void saveProperty(SectionWriter writer, String name, Person value) {
		if (value == null) return;
		String id = tree.getID(value);
		if (id == null) return;

		writer.saveProperty(name, id);
	}

	private void saveMarriages(SectionWriter writer, Person person) {
		int numberOfMarriages = person.numberOfMarriages();

		if (numberOfMarriages < 1) return;
		
		writer.saveProperty(PGLFields.marriages, numberOfMarriages);

		for(int i=0; i<numberOfMarriages; i++) {
			writer.saveProperty(PGLFields.spouse(i+1),       tree.getID(person.getSpouse(i)));
			writer.saveProperty(PGLFields.weddingDate(i+1),  person.getMarriage(i).getDate());
			writer.saveProperty(PGLFields.weddingPlace(i+1), person.getMarriage(i).getPlace());
		}
	}

	private void saveChildren(SectionWriter writer, Person person) {
		int numberOfChildren = person.numberOfChildren();
		
		if (numberOfChildren < 1) return;
		
		writer.saveProperty(PGLFields.children, numberOfChildren);

		for(int i=0; i<numberOfChildren; i++)
			writer.saveProperty(PGLFields.child(i+1), tree.getID(person.getChild(i)));
	}
}
