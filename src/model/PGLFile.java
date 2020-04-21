package model;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Person.Sex;
import model.Person.LifeStatus;
import tools.Tools;

public class PGLFile {

	private class INISection
	{
		String name;
		private Map<String, String> keys = new HashMap<String, String>();
		
		public INISection(String name) {
			this.name = name;
		}
		
		public void addKey(String name, String value)
		{
			keys.put(name, value);
		}
		
		public String getValue(String keyName)
		{
			return keys.get(keyName);
		}
	}
	private static class Relation {
		public enum Type {FATHER, MOTHER, CHILD, SPOUSE};
		String subject;
		Type type;
		String object;
		int number;
		String date;
		String place;

		public Relation(String subject, Type type, String object) {
			this.subject = subject;
			this.type = type;
			this.object = object;
		}
		public Relation(String subject, Type type, String object, int number) {
			this.subject = subject;
			this.type = type;
			this.object = object;
			this.number = number;
		}


		static void addDateToRelation(List<Relation> relationList, String object, Type relationType, int number, String date)
		{
			for (Relation r : relationList)
				if ((r.object.equals(object)) && (r.type.equals(relationType)) && (r.number == number))
				{
					r.date = date;
					return;
				}
		}

		static void addPlaceToRelation(List<Relation> relationList, String object, Type relationType, int number, String place)
		{
			for (Relation r : relationList)
				if ((r.object.equals(object)) && (r.type.equals(relationType)) && (r.number == number))
				{
					r.place = place;
					return;
				}
		}
	}
	
//	private DataInputStream file;
	BufferedReader brFile;
    
	
	public PGLFile(String path) throws FileNotFoundException {
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
		
//		file = dis;		
		brFile = new BufferedReader(new InputStreamReader(dis));
	}
	
	
	public boolean load(Tree tree)
	{
		String line;
		String[] splitting;
		INISection section = null;
//		Person p = null;
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
					
				} else if (section != null)
				{
					splitting = line.split("=", 2);
					if (splitting.length == 2)
						section.addKey(splitting[0], splitting[1]);
				}
				line = brFile.readLine();
			}
			
			if (section != null)
				loadDataToTree(tree, section, relations);
			
		} catch (IOException e)
		{
			return false;
		}
		
		for (Relation r : relations)
		{
			if (r.type == Relation.Type.FATHER)	tree.getPerson(r.object).setFather(tree.getPerson(r.subject)); else
			if (r.type == Relation.Type.MOTHER)	tree.getPerson(r.object).setMother(tree.getPerson(r.subject)); else
			if (r.type == Relation.Type.CHILD)	tree.getPerson(r.object).addChild(tree.getPerson(r.subject)); else
			if (r.type == Relation.Type.SPOUSE)	
			{
				tree.getPerson(r.object).addMarriages(tree.getPerson(r.subject));
				tree.getPerson(r.object).addWeddingDate(tree.getPerson(r.subject), r.date);
				tree.getPerson(r.object).addWeddingVenue(tree.getPerson(r.subject), r.place);
			}
		}
		
		return true;
	}

	private void loadMetadataToTree(Tree tree, INISection section)
	{
		String value;
		
		value = section.getValue("ost_otw");
		if (value != null)
			tree.setLastOpen(loadDate(value));
		
		value = section.getValue("wersja");
		if (value != null)
			tree.setLastModification(loadDate(value));
		
		value = section.getValue("ile");
		if (value != null)
			try {tree.setNumberOfPersons(Integer.parseInt(value));} catch (NumberFormatException e) {}
		
		for (int i=1; i<=10; i++)
			{value = section.getValue("nazw"+i);	if ((value != null) && (!value.equals(""))) tree.addCommonSurname(value);}
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
		Person person =  new Person();
		String value;
		int numOfChildren=0, numOfMarriages=0;
		
		value = section.getValue("imie");			if (value != null) person.setFirstName(value);
		value = section.getValue("nazwisko");		if (value != null) person.setLastName(value);
		value = section.getValue("datur");			if (value != null) person.setBirthDate(new MyDate(value));
		value = section.getValue("datsm");			if (value != null) person.setDeathDate(new MyDate(value));
		value = section.getValue("miejur");		if (value != null) person.setBirthPlace(value);
		value = section.getValue("miejsm");		if (value != null) person.setDeathPlace(value);
		value = section.getValue("zyje");			if (value != null) person.setLifeStatus(value.equals("0") ? LifeStatus.NO : LifeStatus.YES);
		value = section.getValue("plec");			if (value != null) person.setSex(value.equals("0") ? Sex.WOMAN : Sex.MAN);
		value = section.getValue("ps");			if (value != null) person.setAlias(value);
		value = section.getValue("parafia");		if (value != null) person.setBaptismParish(value);
		value = section.getValue("mpoch");			if (value != null) person.setBurialPlace(value);

		value = section.getValue("kontakt");		if (value != null) person.setContact(value.replace("$", "\n"));
		value = section.getValue("uwagi");			if (value != null) person.setComments(value.replace("$", "\n"));
		

		value = section.getValue("ojciec");		if (value != null) relations.add(new Relation(value, Relation.Type.FATHER, section.name));
		value = section.getValue("matka");			if (value != null) relations.add(new Relation(value, Relation.Type.MOTHER, section.name));
		value = section.getValue("dzieci");		if (value != null) numOfChildren = strToInt(value,0);
		value = section.getValue("malzenstwa");	if (value != null) numOfMarriages = strToInt(value,0);
		for (int i=1; i<=numOfChildren; i++)
			{value = section.getValue("dziecko"+i);	if (value != null) relations.add(new Relation(value, Relation.Type.CHILD, section.name, i));}
		for (int i=1; i<=numOfMarriages; i++)
			{value = section.getValue("malzonek"+i);	if (value != null) relations.add(new Relation(value, Relation.Type.SPOUSE, section.name, i));}
		for (int i=1; i<=numOfMarriages; i++)
			{value = section.getValue("malzdata"+i);	if (value != null) Relation.addDateToRelation(relations, section.name, Relation.Type.SPOUSE, i, value);}
		for (int i=1; i<=numOfMarriages; i++)
			{value = section.getValue("malzmjsc"+i);	if (value != null) Relation.addPlaceToRelation(relations, section.name, Relation.Type.SPOUSE, i, value);}

		tree.addPerson(section.name, person);		
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
