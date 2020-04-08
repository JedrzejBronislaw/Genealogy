package model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Person {
	public enum LifeStatus{YES, NO, UNDEFINED};
	public enum Sex{WOMEN, MAN, UNDEFINED};
	
	@Setter @Getter private String firstName;
	@Setter @Getter private String lastname;
	@Setter @Getter private String alias;
	@Setter @Getter private LifeStatus lifeStatus = LifeStatus.UNDEFINED;
	@Setter @Getter private Sex sex = Sex.UNDEFINED;
	@Setter @Getter private MyDate birthDate;
	@Setter @Getter private MyDate deathDate;
	@Setter @Getter private String birthPlace;
	@Setter @Getter private String deathPlace;
	@Setter @Getter private String contact;
	@Setter @Getter private String comments;
	@Setter @Getter private String baptismParish;
	@Setter @Getter private String burialPlace;
	
	@Setter @Getter private Person father;
	@Setter @Getter private Person mother;
	private List<Person> children = new ArrayList<Person>(); 
	private List<Marriage> marriages = new ArrayList<Marriage>(); 
	

	public void addChild(Person child) {
		children.add(child);
	}
	public Person getChild(int number) {
		return children.get(number);
	}
	public Person[] getChildren() {
		Person[] outcome = new Person[children.size()];
		outcome = children.toArray(outcome);
		return outcome;
	}
	public void addMarriages(Person malzonek) {
		Marriage marriage = new Marriage();
		if (sex == Sex.MAN)
		{
			marriage.setMaz(this);
			marriage.setZona(malzonek);
			marriages.add(marriage);
		} else
		if (sex == Sex.WOMEN)
		{
			marriage.setMaz(malzonek);
			marriage.setZona(this);
			marriages.add(marriage);
		}
	}
	public void addMarriages(Person spouse, String date, String place) {
		Marriage wedding = new Marriage();
		
		wedding.setData(date);
		wedding.setMiejsce(place);
		if (sex == Sex.MAN)
		{
			wedding.setMaz(this);
			wedding.setZona(spouse);
			marriages.add(wedding);
		} else
		if (sex == Sex.WOMEN)
		{
			wedding.setMaz(spouse);
			wedding.setZona(this);
			marriages.add(wedding);
		}
	}
	public void addWeddingDate(Person spouse, String date)
	{
		for(Marriage m : marriages)
		{
			if (((sex == Sex.MAN) && (m.getZona() == spouse)) ||
				((sex == Sex.WOMEN)  && (m.getMaz()  == spouse)))
			{
				m.setData(date);
				return;
			}
		}
	}
	public void addWeddingVenue(Person spouse, String place)
	{
		for(Marriage m : marriages)
		{
			if (((sex == Sex.MAN) && (m.getZona() == spouse)) ||
				((sex == Sex.WOMEN)  && (m.getMaz()  == spouse)))
			{
				m.setMiejsce(place);
				return;
			}
		}
	}
	
	public Marriage getMarriages(int number) {
		return marriages.get(number);
	}
	
	public Person getSpouse(int number) {
		if (sex == Sex.MAN)
			return marriages.get(number).getZona();
		if (sex == Sex.WOMEN)
			return marriages.get(number).getMaz();
		
		return null;
	}

	public Person[] getSiblings() {
		Person[] fathersChildren = (father!=null) ? father.getChildren() : new Person[0];
		Person[] mothersChildren = (mother !=null) ? mother.getChildren()  : new Person[0];
		Person[] siblings;
		LinkedHashSet<Person> set = new LinkedHashSet<Person>();

		for (Person p : fathersChildren)
			set.add(p);
		for (Person p : mothersChildren)
			set.add(p);
		
		set.remove(this);
		
		siblings = new Person[set.size()];
		siblings = set.toArray(siblings);
		
		return siblings;
	}
	
	public int numberOfChildren() {return children.size();}
	public int numberOfMarriages() {return marriages.size();}
	public String nameSurname() 
	{
		String outcome = "";
		if (firstName != null)		outcome += getFirstName() + " ";
		if (lastname != null)	outcome += getLastname();
		
		return outcome.trim();
	}
	
	public int rootSize()
	{
		int outcome = 0;
		if (mother != null) outcome = mother.rootSize()+1;
		if (father != null) outcome = Math.max(outcome, father.rootSize()+1);
		
		return outcome;
	}
	
	public int descendantGenerations()
	{
		int outcome = 0;
		for (int i=0; i<numberOfChildren(); i++)
			outcome = Math.max(outcome, children.get(i).descendantGenerations()+1);
		
		return outcome;
	}
	public int parentsMarriageNumber(Person parent) {
		int outcome = 0;
		Person parent2;
		
		for (int i=0; i < parent.numberOfMarriages(); i++)
		{
			parent2 = parent.getSpouse(i);
			if ((getFather() == parent2) || (getMother() == parent2))
			{
				outcome = i+1;
				break;
			}
		}
			
		if ((getMother() != parent) && (getFather() != parent))
			outcome *= -1;
			
		return outcome;
	}
}
