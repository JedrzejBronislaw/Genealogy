package model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Person {
	public enum LifeStatus{YES, NO, UNKNOWN};
	public enum Sex{WOMAN, MAN, UNKNOWN};
	
	@Setter @Getter private String firstName;
	@Setter @Getter private String lastName;
	@Setter @Getter private String alias;
	@Setter @Getter private LifeStatus lifeStatus = LifeStatus.UNKNOWN;
	@Setter @Getter private Sex sex = Sex.UNKNOWN;
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
	public boolean delChildRelation(Person child) {
		return children.remove(child);
	}
	public void delAllChildRelation() {
		children.clear();
	}
	public Person getChild(int number) {
		return children.get(number);
	}
	public Person[] getChildren() {
		Person[] outcome = new Person[children.size()];
		outcome = children.toArray(outcome);
		return outcome;
	}
	public boolean isChildless() {
		return numberOfChildren() == 0;
	}
	public boolean hasNoParents() {
		return (getMother() == null && getFather() == null);
	}
	public boolean hasAnyParent() {
		return !hasNoParents();
	}
	public void addMarriage(Person malzonek) {
		Marriage marriage = new Marriage();
		if (sex == Sex.MAN)
		{
			marriage.setHusband(this);
			marriage.setWife(malzonek);
			marriages.add(marriage);
		} else
		if (sex == Sex.WOMAN)
		{
			marriage.setHusband(malzonek);
			marriage.setWife(this);
			marriages.add(marriage);
		}
	}
	public void addMarriage(Person spouse, String date, String place) {
		Marriage wedding = new Marriage();
		
		wedding.setDate(date);
		wedding.setPlace(place);
		if (sex == Sex.MAN)
		{
			wedding.setHusband(this);
			wedding.setWife(spouse);
			marriages.add(wedding);
		} else
		if (sex == Sex.WOMAN)
		{
			wedding.setHusband(spouse);
			wedding.setWife(this);
			marriages.add(wedding);
		}
	}
	public void addWeddingDate(Person spouse, String date)
	{
		for(Marriage m : marriages)
		{
			if (((sex == Sex.MAN) && (m.getWife() == spouse)) ||
				((sex == Sex.WOMAN)  && (m.getHusband()  == spouse)))
			{
				m.setDate(date);
				return;
			}
		}
	}
	public void addWeddingVenue(Person spouse, String place)
	{
		for(Marriage m : marriages)
		{
			if (((sex == Sex.MAN) && (m.getWife() == spouse)) ||
				((sex == Sex.WOMAN)  && (m.getHusband()  == spouse)))
			{
				m.setPlace(place);
				return;
			}
		}
	}

	public boolean delSpouseRelation(Person spouse) {
		for(Marriage m : marriages) {
			if (((sex == Sex.MAN) && (m.getWife() == spouse)) ||
				((sex == Sex.WOMAN)  && (m.getHusband()  == spouse)))
					return marriages.remove(m);
		}
		return false;
	}
	public void delAllSpouseRelation() {
		marriages.clear();
	}
	
	public Marriage getMarriage(int number) {
		return marriages.get(number);
	}
	
	public Marriage getMarriage(Person p2) {

		for(int i=0; i<numberOfMarriages(); i++) {
			Marriage m = getMarriage(i);
			if ((m.getHusband() == this && m.getWife() == p2) ||
				(m.getHusband() == p2   && m.getWife() == this))
				return m;
		}
		
		return null;
	}
	
	public Person getSpouse(int number) {
		if (sex == Sex.MAN)
			return marriages.get(number).getWife();
		if (sex == Sex.WOMAN)
			return marriages.get(number).getHusband();
		
		return null;
	}
	public Person[] getSpouses() {
		int number = numberOfMarriages();
		Person[] spouses = new Person[number];
		
		for (int i=0; i<number; i++)
			spouses[i] = getSpouse(i);
		
		return spouses;
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
		if (lastName != null)	outcome += getLastName();
		
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Person)) return false;
		
		Person person = (Person) obj;
		
		if (firstName == null     && person.getFirstName() != null)     return false;
		if (lastName == null      && person.getLastName() != null)      return false;
		if (alias == null         && person.getAlias() != null)         return false;
		if (lifeStatus == null    && person.getLifeStatus() != null)    return false;
		if (sex == null           && person.getSex() != null)           return false;
		if (birthDate == null     && person.getBirthDate() != null)     return false;
		if (deathDate == null     && person.getDeathDate() != null)     return false;
		if (birthPlace == null    && person.getBirthPlace() != null)    return false;
		if (deathPlace == null    && person.getDeathPlace() != null)    return false;
		if (contact == null       && person.getContact() != null)       return false;
		if (comments == null      && person.getComments() != null)      return false;
		if (baptismParish == null && person.getBaptismParish() != null) return false;
		if (burialPlace == null   && person.getBurialPlace() != null)   return false;

		if (firstName != null     && !firstName.equals(person.getFirstName()))         return false;
		if (lastName != null      && !lastName.equals(person.getLastName()))           return false;
		if (alias != null         && !alias.equals(person.getAlias()))                 return false;
		if (lifeStatus != null    && !lifeStatus.equals(person.getLifeStatus()))       return false;
		if (sex != null           && !sex.equals(person.getSex()))                     return false;
		if (birthDate != null     && !birthDate.equals(person.getBirthDate()))         return false;
		if (deathDate != null     && !deathDate.equals(person.getDeathDate()))         return false;
		if (birthPlace != null    && !birthPlace.equals(person.getBirthPlace()))       return false;
		if (deathPlace != null    && !deathPlace.equals(person.getDeathPlace()))       return false;
		if (contact != null       && !contact.equals(person.getContact()))             return false;
		if (comments != null      && !comments.equals(person.getComments()))           return false;
		if (baptismParish != null && !baptismParish.equals(person.getBaptismParish())) return false;
		if (burialPlace != null   && !burialPlace.equals(person.getBurialPlace()))     return false;

		return true;
	}
}
