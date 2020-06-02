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
		String[] splitting;
		INISection section = null;
		List<Relation> relations = new ArrayList<Relation>();
		
		try{
			line = brFile.readLine();
			while (line != null) {
				line = line.trim();
				if	((line.length() >= 2) &&
					((line.charAt(0) == '[') && (line.charAt(line.length()-1) == ']')))
				{
					//section save
					if (section != null)
						if (section.name.toUpperCase().equals("MAIN"))
							loadMetadataToTree(tree, section);
						else
							loadDataToTree(tree, section, relations);
					
					//section opening
					section = new INISection(line.substring(1, line.length()-1));
					
				} else if (section != null) {
					splitting = line.split("=", 2);
					if (splitting.length == 2)
						section.addKey(splitting[0], splitting[1]);
				}
				line = brFile.readLine();
			}
			
			if (section != null)
				loadDataToTree(tree, section, relations);
			
		} catch (IOException e) {
			return false;
		}
		
		relations.forEach(relation -> relation.applyFor(tree));
		
		return true;
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
	
	private void loadDataToTree(Tree tree, INISection section, List<Relation> relations)
	{
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

		value = section.getValue("kontakt");        if (value != null) person.setContact( value.replace("$", "\n"));
		value = section.getValue("uwagi");          if (value != null) person.setComments(value.replace("$", "\n"));
		

		value = section.getValue("ojciec");         if (value != null) relations.add(new Relation(value, Relation.Type.FATHER, section.name));
		value = section.getValue("matka");          if (value != null) relations.add(new Relation(value, Relation.Type.MOTHER, section.name));
		value = section.getValue("dzieci");         if (value != null) numOfChildren  = strToInt(value, 0);
		value = section.getValue("malzenstwa");     if (value != null) numOfMarriages = strToInt(value, 0);
		multiVal(section, "dziecko",  numOfChildren). forEach(    v -> relations.add(new Relation(v.value, Relation.Type.CHILD,  section.name, v.i)));
		multiVal(section, "malzonek", numOfMarriages).forEach(    v -> relations.add(new Relation(v.value, Relation.Type.SPOUSE, section.name, v.i)));
		multiVal(section, "malzdata", numOfMarriages).forEach(    v -> Relation.addDateToRelation( relations, section.name, Relation.Type.SPOUSE, v.i, v.value));
		multiVal(section, "malzmjsc", numOfMarriages).forEach(    v -> Relation.addPlaceToRelation(relations, section.name, Relation.Type.SPOUSE, v.i, v.value));

		tree.addPerson(section.name, person);		
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
