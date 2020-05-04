package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

public class Tree {

	private Map<String, Person> persons = new TreeMap<String, Person>();
	@Setter @Getter private Date lastOpen;
	@Setter @Getter private Date lastModification;
	@Setter @Getter private int numberOfPersons;
	private List<String> commonSurnames = new ArrayList<String>();

	public String[] getCommonSurnames() {
		String[] outcome = new String[commonSurnames.size()];
		outcome = commonSurnames.toArray(outcome);
		return outcome;
	}

	public void addCommonSurname(String commonSurname) {
		commonSurnames.add(commonSurname);
	}

	
	public void addPerson(String id, Person person)
	{
		persons.put(id, person);
		numberOfPersons = persons.size();
	}
	
	public Person getPerson(String id)
	{
		return persons.get(id);
	}
	
	public int numberOfPersons()
	{
		return persons.size();
	}
	
	public String[] getIDs()
	{
		String[] ids = new String[persons.size()];
		return persons.keySet().toArray(ids);
	}

	public String getID(Person person) {
		if (person == null) return null;
		
		for(Entry<String, Person> treeItem : persons.entrySet())
			if (treeItem.getValue() == person)
				return treeItem.getKey();
		
		return null;
	}
	
	public Person randomPerson()
	{
		String[] ids = getIDs();
		Random r = new Random();
		
		return persons.get(ids[r.nextInt(ids.length)]);
	}
	
	public Person[] getAll() {
		Person[] outcome = new Person[persons.size()];

		outcome = persons.values().toArray(outcome);
		
		return outcome;
	}

	public Marriage getMarriage(String personID1, String personID2) {
		Person person1 = getPerson(personID1);
		Person person2 = getPerson(personID2);
		
		return person1.getMarriage(person2);
	}
	public Marriage getMarriage(Person person1, String personID2) {
		Person person2 = getPerson(personID2);
		
		return person1.getMarriage(person2);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Tree)) return false;
		Tree tree = (Tree) obj;

		return new SimpleTreeComparator().equals(this, tree);
	}
}
