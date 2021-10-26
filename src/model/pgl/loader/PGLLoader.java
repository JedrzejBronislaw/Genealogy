package model.pgl.loader;

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
import model.LifeStatus;
import model.MyDate;
import model.Person;
import model.Sex;
import model.Tree;
import model.pgl.Section;
import model.pgl.PGLFields;
import model.pgl.PGL;
import model.pgl.comparator.PGLComparator;
import model.pgl.comparator.PGLDiffReport;
import model.pgl.loader.Relation.Type;
import model.pgl.saver.TreeToPGLMapper;
import tools.Tools;

public class PGLLoader {

	@AllArgsConstructor
	class IndexedString {
		int i;
		String value;
	}
	
	
	private BufferedReader brFile;
    
	
	public PGLLoader(String path) throws FileNotFoundException {
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
		try{
			loadFromFile(tree);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public PGLDiffReport loadAndAnalize(Tree tree)
	{
		try{
			PGL pgl = loadFromFile(tree);
			return analize(tree, pgl);
		} catch (IOException e) {
			return null;
		}
	}
	
	private PGL loadFromFile(Tree tree) throws IOException
	{
		String line;
		Section section = null;
		List<Relation> relations = new ArrayList<Relation>();
		PGL pgl = new PGL();
		
		while ((line = brFile.readLine()) != null) {
			line = line.trim();
			
			if (isSectionHeader(line))
				section = pgl.newSection(sectionName(line));
			else
				addValue(line, section);
		}
		
		loadToTree(tree, pgl, relations);
		relations.forEach(relation -> relation.applyFor(tree));

		return pgl;
	}

	private PGLDiffReport analize(Tree tree, PGL pgl) {
		return new PGLDiffReport(new PGLComparator(
				pgl, "file",
				new TreeToPGLMapper().map(tree), "loaded")
				.compare());
	}

	private void addValue(String line, Section section) {
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

	private boolean isMainSection(Section section) {
		return section.getName().toUpperCase().equals(
				PGLFields.mainSectionName.toUpperCase());
	}

	private boolean isSectionHeader(String line) {
		return line.startsWith("[")
			&& line.endsWith("]");
	}

	private void loadMetadataToTree(Tree tree, Section section) {
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
	
	private void loadToTree(Tree tree, PGL pgl, List<Relation> relations) {
		pgl.getSection(PGLFields.mainSectionName).ifPresent(mainSection ->
			loadMetadataToTree(tree, mainSection)
		);
		
		pgl.forEachSection(section ->
			loadDataToTree(tree, section, relations)
		);
	}
	
	private void loadDataToTree(Tree tree, Section section, List<Relation> relations) {
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
		section.value(PGLFields.lifeStatus).map(LifeStatus::get) .ifPresent(person::setLifeStatus);
		section.value(PGLFields.sex).map(Sex::get)               .ifPresent(person::setSex);
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
	
	private String splitLine(String test) {
		return test.replace(PGLFields.lineSeparator, System.lineSeparator());
	}
	
	private List<IndexedString> multiVal(Section section, String key, int size) {
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
