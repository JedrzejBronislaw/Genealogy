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
import model.pgl.reader.Relation.Type;
import model.pgl.virtual.INISection;
import model.pgl.virtual.VirtualPGL;
import tools.Tools;

public class PGLReader {

	private static final String MAIN_SECTION_NAME = "MAIN";
	
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
		return section.getName().toUpperCase().equals(MAIN_SECTION_NAME);
	}

	private boolean isSectionHeader(String line) {
		return line.startsWith("[")
			&& line.endsWith("]");
	}

	private void loadMetadataToTree(Tree tree, INISection section) {
		section.value("ost_otw").flatMap(this::loadDate).ifPresent(tree::setLastOpen);
		section.value("wersja") .flatMap(this::loadDate).ifPresent(tree::setLastModification);
		section.value("ile")    .flatMap(this::strToInt).ifPresent(tree::setNumberOfPersons);
		
		multiVal(section, "nazw", 10).forEach(surname -> {
			if (!surname.value.isEmpty()) tree.addCommonSurname(surname.value);
		});
	}

	private Optional<Date> loadDate(String textDate) {
		final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		
		return     tryLoadDate(textDate, sdf1).
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
		virtualPGL.get(MAIN_SECTION_NAME).ifPresent(mainSection ->
			loadMetadataToTree(tree, mainSection)
		);
		
		virtualPGL.forEachSession(section ->
			loadDataToTree(tree, section, relations)
		);
	}
	
	private void loadDataToTree(Tree tree, INISection section, List<Relation> relations) {
		if (isMainSection(section)) return;
		
		Person person = new Person();
		String personId = section.getName();
		int numOfChildren  = 0;
		int numOfMarriages = 0;
		
		section.value("imie")                      .ifPresent(person::setFirstName);
		section.value("nazwisko")                  .ifPresent(person::setLastName);
		section.value("datur").map(MyDate::new)    .ifPresent(person::setBirthDate);
		section.value("datsm").map(MyDate::new)    .ifPresent(person::setDeathDate);
		section.value("miejur")                    .ifPresent(person::setBirthPlace);
		section.value("miejsm")                    .ifPresent(person::setDeathPlace);
		section.value("zyje").map(this::lifeStatus).ifPresent(person::setLifeStatus);
		section.value("plec").map(this::sex)       .ifPresent(person::setSex);
		section.value("ps")                        .ifPresent(person::setAlias);
		section.value("parafia")                   .ifPresent(person::setBaptismParish);
		section.value("mpoch")                     .ifPresent(person::setBurialPlace);

		section.value("kontakt").map(this::splitLine).ifPresent(person::setContact);
		section.value("uwagi")  .map(this::splitLine).ifPresent(person::setComments);
		
		section.value("ojciec").ifPresent(v -> relations.add(new Relation(v, Type.FATHER, personId)));
		section.value("matka") .ifPresent(v -> relations.add(new Relation(v, Type.MOTHER, personId)));
		numOfChildren  = section.value("dzieci")    .flatMap(this::strToInt).orElse(0);
		numOfMarriages = section.value("malzenstwa").flatMap(this::strToInt).orElse(0);

		multiVal(section, "dziecko",  numOfChildren). forEach(v -> relations.add(new Relation(v.value, Type.CHILD,  personId, v.i)));
		multiVal(section, "malzonek", numOfMarriages).forEach(v -> relations.add(new Relation(v.value, Type.SPOUSE, personId, v.i)));
		multiVal(section, "malzdata", numOfMarriages).forEach(v -> Relation.addDateToRelation( relations, personId, Type.SPOUSE, v.i, v.value));
		multiVal(section, "malzmjsc", numOfMarriages).forEach(v -> Relation.addPlaceToRelation(relations, personId, Type.SPOUSE, v.i, v.value));

		tree.addPerson(personId, person);
	}

	private LifeStatus lifeStatus(String status) {
		return status.equals("0") ? LifeStatus.NO : LifeStatus.YES;
	}

	private Sex sex(String sex) {
		return sex.equals("0") ? Sex.WOMAN : Sex.MAN;
	}
	
	private String splitLine(String test) {
		return test.replace("$", System.lineSeparator());
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
