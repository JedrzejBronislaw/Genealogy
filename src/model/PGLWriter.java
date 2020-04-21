package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Person.LifeStatus;
import model.Person.Sex;

@RequiredArgsConstructor
public class PGLWriter {
	
	@NonNull
	private File file;
	private FileWriter writer;
	
	private Tree tree;
	
	public boolean save(Tree tree) {
		try {
			if (tree == null) return false;
			
			writer = new FileWriter(file);
			
			this.tree = tree;
			String[] ids = tree.getIDs();
			
			writeMainSection(tree);
			List.of(ids).forEach(id -> {writePerson(id, tree.getPerson(id));});
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	private void writeMainSection(Tree tree) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");

		writer.write("[Main]");
		
		writeNewLine();
		writer.write("ost_otw=" + format.format(tree.getLastOpen()));
		writeNewLine();
		writer.write("wersja=" + format.format(tree.getLastModification()));
		writeNewLine();
		writer.write("ile=" + tree.numberOfPersons());
		writeNewLine();

		String[] names = tree.getCommonSurnames();
		for(int i=0; i<names.length; i++) {
			writer.write("nazw" + (i+1) + "=" + names[i]);
			writeNewLine();			
		}
	}

	private boolean writePerson(String id, Person person) {
		try {
			writePersonId(id);
			saveProperty("imie", person.getFirstName());
			saveProperty("nazwisko", person.getLastName());
			saveProperty("ps", person.getAlias());
			
			saveProperty("datur", person.getBirthDate());
			saveProperty("miejur", person.getBirthPlace());
			saveProperty("datsm", person.getDeathDate());
			saveProperty("miejsm", person.getDeathPlace());
			
			saveProperty(person.getLifeStatus());
			saveProperty(person.getSex());
			saveProperty("parafia", person.getBaptismParish());
			saveProperty("mpoch", person.getBurialPlace());
			
			saveProperty("kontakt", person.getContact());
			saveProperty("uwagi", person.getComments());
			
			
			saveProperty("ojciec", person.getFather());
			saveProperty("matka", person.getMother());
			
			saveMarriages(person);	
			saveChildren(person);
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private void writePersonId(String id) throws IOException {
		writer.write("[" + id + "]");
		writeNewLine();
	}

	private void saveProperty(String name, String value) throws IOException {
		if (value == null || value.isEmpty()) return;
		
		writer.write(name + "=" + value);
		writeNewLine();
	}

	private void saveProperty(String name, int value) throws IOException {
		writer.write(name + "=" + value);
		writeNewLine();
	}

	private void saveProperty(String name, MyDate value) throws IOException {
		if (value == null) return;
		
		writer.write(name + "=" + value.toString());
		writeNewLine();
	}

	private void saveProperty(String name, Person value) throws IOException {
		if (value == null) return;
		String id = tree.getID(value);
		if (id == null) return;
		
		writer.write(name + "=" + id);
		writeNewLine();
	}

	private void saveProperty(LifeStatus value) throws IOException {
		if (value == null || value == LifeStatus.UNDEFINED) return;
		
		if (value == LifeStatus.NO)  writer.write("zyje=0");
		if (value == LifeStatus.YES) writer.write("zyje=1");
		writeNewLine();
	}

	private void saveProperty(Sex value) throws IOException {
		if (value == null || value == Sex.UNDEFINED) return;
		
		if (value == Sex.WOMAN) writer.write("plec=0");
		if (value == Sex.MAN)   writer.write("plec=1");
		writeNewLine();
	}

	private void saveMarriages(Person person) throws IOException {
		saveProperty("malzenstwa", person.numberOfMarriages());
		
		for(int i=0; i<person.numberOfMarriages(); i++) {
			saveProperty("malzonek" + (i+1), tree.getID(person.getSpouse(i)));
			saveProperty("malzdata" + (i+1), person.getMarriages(i).getDate());
			saveProperty("malzmjsc" + (i+1), person.getMarriages(i).getPlace());
		}
	}

	private void saveChildren(Person person) throws IOException {
		saveProperty("dzieci", person.numberOfChildren());
		
		for(int i=0; i<person.numberOfChildren(); i++)
			saveProperty("dziecko" + (i+1), tree.getID(person.getChild(i)));
	}

	private void writeNewLine() throws IOException {
		writer.write("\n");
	}
}
