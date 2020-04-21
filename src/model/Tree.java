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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Tree)) return false;
		Tree tree = (Tree) obj;
		
		if (lastOpen  == null    && tree.getLastOpen() != null) return false;
		if (lastModification  == null    && tree.getLastModification() != null) return false;

		if (lastOpen != null && !lastOpen.equals(tree.getLastOpen())) return false;
		if (lastModification != null && !lastModification.equals(tree.getLastModification())) return false;
		
		if (numberOfPersons != tree.numberOfPersons) return false;
		if (commonSurnames.size() != tree.commonSurnames.size()) return false;
		
		for (int i=0; i<commonSurnames.size(); i++)
			if (!commonSurnames.get(i).equals(tree.commonSurnames.get(i))) return false;
		
		String[] ids = getIDs();
		for (int i=0; i<ids.length; i++)
			if (!getPerson(ids[i]).equals(tree.getPerson(ids[i]))) return false;
		
		//relations
		for (int i=0; i<ids.length; i++) {
			Person person1 = getPerson(ids[i]);
			Person person2 = tree.getPerson(ids[i]);
			
			if (person1.numberOfChildren() != person2.numberOfChildren()) return false;
			if (person1.numberOfMarriages() != person2.numberOfMarriages()) return false;

			String father1 =      getID(person1.getFather());
			String father2 = tree.getID(person2.getFather());

			String mother1 =      getID(person1.getMother());
			String mother2 = tree.getID(person2.getMother());

			if (father1 == null && father2 != null) return false;
			if (mother1 == null && mother2 != null) return false;

			if (father1 != null && !father1.equals(father2)) return false;
			if (mother1 != null && !mother1.equals(mother2)) return false;
			
			//children
			Person[] children1 = person1.getChildren();
			Person[] children2 = person2.getChildren();
			
			List<String> childrenIds1 = new ArrayList<>();
			List<String> childrenIds2 = new ArrayList<>();

			for(int index=0; index<children1.length; index++)
				childrenIds1.add(getID(children1[index]));
			for(int index=0; index<children2.length; index++)
				childrenIds2.add(tree.getID(children2[index]));
			
			for(String id : childrenIds1)
				if (!childrenIds2.contains(id)) return false;
			
			
			//marriages
			List<String> marrIds1 = new ArrayList<>();
			List<String> marrIds2 = new ArrayList<>();

			for(int index=0; index<person1.numberOfMarriages(); index++)
				marrIds1.add(     getID(person1.getSpouse(index)));

			for(int index=0; index<person2.numberOfMarriages(); index++)
				marrIds2.add(tree.getID(person2.getSpouse(index)));
		
			for(String id : marrIds1)
				if (!marrIds2.contains(id)) return false;
				else {
					Marriage m1 = getMarriage(this, person1, id);
					String place1 = m1.getPlace();
					String date1 = m1.getDate();

					Marriage m2 = getMarriage(tree, person2, id);
					String place2 = m2.getPlace();
					String date2 = m2.getDate();

					if (place1 == null && place2 != null) return false;
					if (place1 != null && !place1.equals(place2)) return false;
					
					if (date1 == null && date2 != null) return false;
					if (date1 != null && !date1.equals(date2)) return false;
				}
			
			
		}
		
		return true;
	}
	

	private Marriage getMarriage(Tree tree, Person p1, String p2) {
		return getMarriage(tree, p1, tree.getPerson(p2));
	}
	
	private Marriage getMarriage(Tree tree, Person p1, Person p2) {

		for(int i=0; i<p1.numberOfMarriages(); i++) {
			Marriage m = p1.getMarriages(i);
			if ((m.getHusband() == p1 && m.getWife() == p2) ||
				(m.getHusband() == p2 && m.getWife() == p1))
				return m;
		}
		
		return null;
	}
}
