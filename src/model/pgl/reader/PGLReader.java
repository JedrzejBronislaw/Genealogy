package model.pgl.reader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import model.MyDate;
import model.Person;
import model.Person.LifeStatus;
import model.Person.Sex;
import model.Tree;
import model.pgl.PGLFields;
import model.pgl.reader.Relation.Type;
import model.pgl.virtual.INISection;
import model.pgl.virtual.VirtualPGL;
import tools.Tools;

public class PGLReader {

	@AllArgsConstructor
	class IndexedString {
		int i;
		String value;
	}
	
	
	private BufferedReader brFile;
    
	
	public PGLReader(String path) throws FileNotFoundException {
		openFile(path);
	}
	
	private void openFile(String path) throws FileNotFoundException
	{
		FileInputStream fis;
		DataInputStream dis;
		
		if (path.charAt(1) != ':')
			path = Tools.dirWithJarPath() + path;
		
		fis = new FileInputStream(path);
		dis = new DataInputStream(fis);
		
		brFile = new BufferedReader(new InputStreamReader(dis));
	}
	
	
	public boolean load(Tree tree)
	{
		String line;
		INISection section = null;
		List<Relation> relations = new ArrayList<Relation>();
		VirtualPGL virtualPGL = new VirtualPGL();
		
		try{
			while ((line = brFile.readLine()) != null) {
				
				line = line.trim();
				if (isSectionHeader(line))
					section = virtualPGL.newSection(sectionName(line));
				else
					addValue(line, section);
				
			}

			loadToTree(tree, virtualPGL, relations);
			
		} catch (IOException e) {
			return false;
		}
		
		relations.forEach(relation -> relation.applyFor(tree));
		
		return true;
	}

	private void addValue(String line, INISection section) {
		if (section == null) return;
		
		String[] keyValue = line.split("=", 2);
		
		if (keyValue.length == 2)
			section.addKey(keyValue[0], keyValue[1]);
	}

	private String sectionName(String line) {
		if (isSectionHeader(line))
			return line.substring(1, line.length()-1);
		else
			return null;
	}

	private boolean isMainSection(INISection section) {
		return section.getName().toUpperCase().equals(PGLFields.mainSectionName);
	}

	private boolean isSectionHeader(String line) {
		return line.startsWith("[")
			&& line.endsWith("]");
	}

	private void loadMetadataToTree(Tree tree, INISection section) {
		section.value(PGLFields.lastOpen)        .flatMap(this::loadDate).ifPresent(tree::setLastOpen);
		section.value(PGLFields.lastModification).flatMap(this::loadDate).ifPresent(tree::setLastModification);
		section.value(PGLFields.numberOfPersons) .flatMap(this::strToInt).ifPresent(tree::setNumberOfPersons);
		
		multiVal(section, PGLFields.commonSurname, 10).forEach(surname -> {
			if (!surname.value.isEmpty()) tree.addCommonSurname(surname.value);
		});
	}

	private Optional<Date> loadDate(String textDate) {
		final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		
		return     tryLoadDate(textDate, PGLFields.dataFormat).
		  or(() -> tryLoadDate(textDate, sdf1)).
		  or(() -> tryLoadDate(textDate, sdf2));
	}

	private Optional<Date> tryLoadDate(String textDate, SimpleDateFormat format) {
		Date date;
		try {
			date = format.parse(textDate);
		} catch (ParseException e) {
			date = null;
		}
		
		return Optional.ofNullable(date);
	}
	
	private void loadToTree(Tree tree, VirtualPGL virtualPGL, List<Relation> relations) {
		virtualPGL.get(PGLFields.mainSectionName).ifPresent(mainSection ->
			loadMetadataToTree(tree, mainSection)
		);
		
		virtualPGL.forEachSection(section ->
			loadDataToTree(tree, section, relations)
		);
	}
	
	private void loadDataToTree(Tree tree, INISection section, List<Relation> relations) {
		if (isMainSection(section)) return;
		
		Person person = new Person();
		String personId = section.getName();
		int numOfChildren  = 0;
		int numOfMarriages = 0;
		
		section.value(PGLFields.firstName)                       .ifPresent(person::setFirstName);
		section.value(PGLFields.lastName)                        .ifPresent(person::setLastName);
		section.value(PGLFields.birthDate).map(MyDate::new)      .ifPresent(person::setBirthDate);
		section.value(PGLFields.deathDate).map(MyDate::new)      .ifPresent(person::setDeathDate);
		section.value(PGLFields.birthPlace)                      .ifPresent(person::setBirthPlace);
		section.value(PGLFields.deathPlace)                      .ifPresent(person::setDeathPlace);
		section.value(PGLFields.lifeStatus).map(this::lifeStatus).ifPresent(person::setLifeStatus);
		section.value(PGLFields.sex).map(this::sex)              .ifPresent(person::setSex);
		section.value(PGLFields.alias)                           .ifPresent(person::setAlias);
		section.value(PGLFields.baptismParish)                   .ifPresent(person::setBaptismParish);
		section.value(PGLFields.burialPlace)                     .ifPresent(person::setBurialPlace);

		section.value(PGLFields.contact) .map(this::splitLine).ifPresent(person::setContact);
		section.value(PGLFields.comments).map(this::splitLine).ifPresent(person::setComments);
		
		section.value(PGLFields.father).ifPresent(v -> relations.add(new Relation(v, Type.FATHER, personId)));
		section.value(PGLFields.mother).ifPresent(v -> relations.add(new Relation(v, Type.MOTHER, personId)));
		numOfChildren  = section.value(PGLFields.children) .flatMap(this::strToInt).orElse(0);
		numOfMarriages = section.value(PGLFields.marriages).flatMap(this::strToInt).orElse(0);

		multiVal(section, PGLFields.child,  numOfChildren). forEach(v -> relations.add(new Relation(v.value, Type.CHILD,  personId, v.i)));
		multiVal(section, PGLFields.spouse, numOfMarriages).forEach(v -> relations.add(new Relation(v.value, Type.SPOUSE, personId, v.i)));
		multiVal(section, PGLFields.weddingDate,  numOfMarriages).forEach(v -> Relation.addDateToRelation( relations, personId, Type.SPOUSE, v.i, v.value));
		multiVal(section, PGLFields.weddingPlace, numOfMarriages).forEach(v -> Relation.addPlaceToRelation(relations, personId, Type.SPOUSE, v.i, v.value));

		tree.addPerson(personId, person);
	}

	private LifeStatus lifeStatus(String status) {
		return status.equals("0") ? LifeStatus.NO : LifeStatus.YES;
	}

	private Sex sex(String sex) {
		return sex.equals("0") ? Sex.WOMAN : Sex.MAN;
	}
	
	private String splitLine(String test) {
		return test.replace(PGLFields.lineSeparator, System.lineSeparator());
	}
	
	private List<IndexedString> multiVal(INISection section, String key, int size) {
		String value;
		List<IndexedString> list = new ArrayList<>();
		
		for (int i=1; i<=size; i++) {
			value = section.getValue(key + i);
			if (value != null) list.add(new IndexedString(i, value));
		}
		
		return list;
	}
	
	private Optional<Integer> strToInt(String stringValue) {
		Optional<Integer> integerValue;
		try{
			integerValue = Optional.of(Integer.parseInt(stringValue));
		} catch (NumberFormatException e) {
			integerValue = Optional.empty();
		}
		
		return integerValue;
	}
}
