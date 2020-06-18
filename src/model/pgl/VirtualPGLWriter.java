package model.pgl;

import java.util.stream.Stream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Person;
import model.Tree;
import model.pgl.virtual.INISection;
import model.pgl.virtual.VirtualPGL;

@RequiredArgsConstructor
public class VirtualPGLWriter {

	private Tree tree;
	private VirtualPGL pgl;

	public VirtualPGL write(@NonNull Tree tree) {
		pgl = new VirtualPGL();
		this.tree = tree;
		
		writeMainSection(tree, pgl.newSection(PGLFields.mainSectionName));
		Stream.of(tree.getIDs()).forEach(this::writePerson);
		
		return pgl;
	}

	private void writeMainSection(Tree tree, INISection mainSection) {
		SectionDataWriter writer = new SectionDataWriter(mainSection);
		
		writer.saveProperty(PGLFields.lastOpen,         tree.getLastOpen());
		writer.saveProperty(PGLFields.lastModification, tree.getLastModification());
		writer.saveProperty(PGLFields.numberOfPersons,  tree.numberOfPersons());

		String[] names = tree.getCommonSurnames();
		for(int i=0; i<names.length; i++)
			writer.saveProperty(PGLFields.commonSurname(i+1), names[i]);
	}

	private boolean writePerson(String id) {
		Person person = tree.getPerson(id);
		SectionDataWriter writer = new SectionDataWriter(pgl.newSection(id));
		
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



	private void saveProperty(SectionDataWriter writer, String name, Person value) {
		if (value == null) return;
		String id = tree.getID(value);
		if (id == null) return;

		writer.saveProperty(name, id);
	}

	private void saveMarriages(SectionDataWriter writer, Person person) {
		writer.saveProperty(PGLFields.marriages, person.numberOfMarriages());

		for(int i=0; i<person.numberOfMarriages(); i++) {
			writer.saveProperty(PGLFields.spouse(i+1),       tree.getID(person.getSpouse(i)));
			writer.saveProperty(PGLFields.weddingDate(i+1),  person.getMarriage(i).getDate());
			writer.saveProperty(PGLFields.weddingPlace(i+1), person.getMarriage(i).getPlace());
		}
	}

	private void saveChildren(SectionDataWriter writer, Person person) {
		writer.saveProperty(PGLFields.children, person.numberOfChildren());

		for(int i=0; i<person.numberOfChildren(); i++)
			writer.saveProperty(PGLFields.child(i+1), tree.getID(person.getChild(i)));
	}
}
