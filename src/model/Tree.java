package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
	
	public Person randomPerson()
	{
		String[] ids = getIDs();
		Random r = new Random();
		
		return persons.get(ids[r.nextInt(ids.length)]);
	}
	
	public ArrayList<Person> leaves()
	{
		String[] ids = getIDs();
		ArrayList<Person> outcome = new ArrayList<Person>();
		Person temp;
		
		for (int i=0; i<ids.length; i++)
		{
			temp = persons.get(ids[i]);
			if (temp.numberOfChildren() == 0)
				outcome.add(temp);
		}
		
		return outcome;
	}

	public ArrayList<Person> roots()
	{
		return roots(false);
	}
	public ArrayList<Person> roots(boolean spouseToo)
	{
		String[] ids = getIDs();
		ArrayList<Person> outcome = new ArrayList<Person>();
		Person temp;
		boolean spouseRoot;
		
		for (int i=0; i<ids.length; i++)
		{
			temp = persons.get(ids[i]);
			if ((temp.getMother() == null) && (temp.getFather() == null))
			{
				spouseRoot = true;
				if (spouseToo)
				for (int j=0; j<temp.numberOfMarriages(); j++)
					if ((temp.getSpouse(j).getMother() != null) || (temp.getSpouse(j).getFather() != null))
					{
						spouseRoot = false;
						break;
					}
				if (spouseRoot) outcome.add(temp);
			}
		}
		
		return outcome;
	}

	public Person[] getAll() {
		Person[] outcome = new Person[persons.size()];

		outcome = persons.values().toArray(outcome);
		
		return outcome;
	}
	
}
