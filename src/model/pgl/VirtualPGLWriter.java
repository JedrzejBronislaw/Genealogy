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

	private static final String MAIN_SECTION_NAME = "MAIN";

	private Tree tree;
	private VirtualPGL pgl;

	public VirtualPGL write(@NonNull Tree tree) {
		pgl = new VirtualPGL();
		this.tree = tree;
		
		writeMainSection(tree, pgl.newSection(MAIN_SECTION_NAME));
		Stream.of(tree.getIDs()).forEach(this::writePerson);
		
		return pgl;
	}

	private void writeMainSection(Tree tree, INISection mainSection) {
		SectionDataWriter writer = new SectionDataWriter(mainSection);
		
		writer.saveProperty("ost_otw", tree.getLastOpen());
		writer.saveProperty("wersja",  tree.getLastModification());
		writer.saveProperty("ile",     tree.numberOfPersons());

		String[] names = tree.getCommonSurnames();
		for(int i=0; i<names.length; i++)
			writer.saveProperty("nazw" + (i+1), names[i]);
	}

	private boolean writePerson(String id) {
		Person person = tree.getPerson(id);
		SectionDataWriter writer = new SectionDataWriter(pgl.newSection(id));
		
		writer.saveProperty("imie", person.getFirstName());
		writer.saveProperty("nazwisko", person.getLastName());
		writer.saveProperty("ps", person.getAlias());
		
		writer.saveProperty("datur", person.getBirthDate());
		writer.saveProperty("miejur", person.getBirthPlace());
		writer.saveProperty("datsm", person.getDeathDate());
		writer.saveProperty("miejsm", person.getDeathPlace());
		
		writer.saveProperty(person.getLifeStatus());
		writer.saveProperty(person.getSex());
		writer.saveProperty("parafia", person.getBaptismParish());
		writer.saveProperty("mpoch", person.getBurialPlace());
		
		writer.saveProperty("kontakt", person.getContact());
		writer.saveProperty("uwagi", person.getComments());
		
		saveProperty(writer, "ojciec", person.getFather());
		saveProperty(writer, "matka", person.getMother());
		
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
		writer.saveProperty("malzenstwa", person.numberOfMarriages());

		for(int i=0; i<person.numberOfMarriages(); i++) {
			writer.saveProperty("malzonek" + (i+1), tree.getID(person.getSpouse(i)));
			writer.saveProperty("malzdata" + (i+1), person.getMarriage(i).getDate());
			writer.saveProperty("malzmjsc" + (i+1), person.getMarriage(i).getPlace());
		}
	}

	private void saveChildren(SectionDataWriter writer, Person person) {//throws IOException {
		writer.saveProperty("dzieci", person.numberOfChildren());

		for(int i=0; i<person.numberOfChildren(); i++)
			writer.saveProperty("dziecko" + (i+1), tree.getID(person.getChild(i)));
	}
}
