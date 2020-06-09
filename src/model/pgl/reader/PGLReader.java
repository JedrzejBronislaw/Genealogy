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
		section.value("ost_otw").ifNotNull(v -> tree.setLastOpen(loadDate(v)));
		section.value("wersja"). ifNotNull(v -> tree.setLastModification(loadDate(v)));
		section.value("ile").    ifNotNull(v -> {
			try {
				tree.setNumberOfPersons(Integer.parseInt(v));
			} catch (NumberFormatException e) {}
		});
		
		multiVal(section, "nazw", 10).forEach(surname -> {
			if (!surname.value.isEmpty()) tree.addCommonSurname(surname.value);
		});
	}

	private Date loadDate(String textDate) {
		final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		
		Date date;
		
		date = tryLoadDate(textDate, sdf1);
		if(date == null)
			date = tryLoadDate(textDate, sdf2);

		return date;
	}

	private Date tryLoadDate(String textDate, SimpleDateFormat format) {
		Date date;
		try {
			date = format.parse(textDate);
		} catch (ParseException e) {
			date = null;
		}
		
		return date;
	}
	
	private void loadToTree(Tree tree, VirtualPGL virtualPGL, List<Relation> relations) {
		virtualPGL.get(MAIN_SECTION_NAME).ifPresent(mainSection ->
			loadMetadataToTree(tree, mainSection)
		);
		
		virtualPGL.forEachSession(section ->
			loadDataToTree(tree, section, relations)
		);
	}
	
	private void loadDataToTree(Tree tree, INISection section, List<Relation> relations)
	{
		if (isMainSection(section)) return;
		
		Person person = new Person();
		String value;
		int numOfChildren  = 0;
		int numOfMarriages = 0;
		
		value = section.getValue("imie");           if (value != null) person.setFirstName(value);
		value = section.getValue("nazwisko");       if (value != null) person.setLastName(value);
		value = section.getValue("datur");          if (value != null) person.setBirthDate(new MyDate(value));
		value = section.getValue("datsm");          if (value != null) person.setDeathDate(new MyDate(value));
		value = section.getValue("miejur");         if (value != null) person.setBirthPlace(value);
		value = section.getValue("miejsm");         if (value != null) person.setDeathPlace(value);
		value = section.getValue("zyje");           if (value != null) person.setLifeStatus(value.equals("0") ? LifeStatus.NO : LifeStatus.YES);
		value = section.getValue("plec");           if (value != null) person.setSex(value.equals("0") ? Sex.WOMAN : Sex.MAN);
		value = section.getValue("ps");             if (value != null) person.setAlias(value);
		value = section.getValue("parafia");        if (value != null) person.setBaptismParish(value);
		value = section.getValue("mpoch");          if (value != null) person.setBurialPlace(value);

		value = section.getValue("kontakt");        if (value != null) person.setContact( value.replace("$", System.lineSeparator()));
		value = section.getValue("uwagi");          if (value != null) person.setComments(value.replace("$", System.lineSeparator()));
		

		value = section.getValue("ojciec");         if (value != null) relations.add(new Relation(value, Type.FATHER, section.getName()));
		value = section.getValue("matka");          if (value != null) relations.add(new Relation(value, Type.MOTHER, section.getName()));
		value = section.getValue("dzieci");         if (value != null) numOfChildren  = strToInt(value, 0);
		value = section.getValue("malzenstwa");     if (value != null) numOfMarriages = strToInt(value, 0);
		multiVal(section, "dziecko",  numOfChildren). forEach(    v -> relations.add(new Relation(v.value, Type.CHILD,  section.getName(), v.i)));
		multiVal(section, "malzonek", numOfMarriages).forEach(    v -> relations.add(new Relation(v.value, Type.SPOUSE, section.getName(), v.i)));
		multiVal(section, "malzdata", numOfMarriages).forEach(    v -> Relation.addDateToRelation( relations, section.getName(), Type.SPOUSE, v.i, v.value));
		multiVal(section, "malzmjsc", numOfMarriages).forEach(    v -> Relation.addPlaceToRelation(relations, section.getName(), Type.SPOUSE, v.i, v.value));

		tree.addPerson(section.getName(), person);
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

	private int strToInt(String stringValue, int defaultValue)
	{
		int integerValue;
		try{
			integerValue = Integer.parseInt(stringValue);
		} catch (NumberFormatException e) {
			integerValue = defaultValue;
		}
		
		return integerValue;
	}
}
