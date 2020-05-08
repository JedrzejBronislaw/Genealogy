package model;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TreeTools {
	
	public final static String LINESEPARATOR = System.lineSeparator();
	
	@NonNull
	private final Tree tree;

	public String personToString(Person person) {
		return tree.getID(person);
	}
	public Person stringToPerson(String string) {
		return tree.getPerson(string);
	}

	
	public String personsToString(List<Person> value) {
		if (value == null) return null;
		
		StringBuilder childrenIDs = new StringBuilder();
		String string;
		
		for (Person child : value) {
			string = personToString(child);
			if (string == null) return null;
			
			childrenIDs.append(string);
			childrenIDs.append(LINESEPARATOR);
		}
		
		return childrenIDs.toString().trim();
	}

	public List<Person> stringToPersons(String value) {
		if (value == null) return null;

		List<Person> childList = new ArrayList<Person>();
		String[] childrenIDs = value.split(LINESEPARATOR);
		Person person;
		
		for(String id : childrenIDs) {
			if (id.isEmpty()) continue;
			
			person = stringToPerson(id);
			if (person == null) return null;
			childList.add(person);
		}
		
		return childList;
	}
	
	
	public Person copyPerson(Person original) {
		if (original == null) return null;
		
		Person copy = new Person();
		copy.setFirstName(original.getFirstName());
		copy.setLastName(original.getLastName());
		if (original.getBirthDate() != null)
			copy.setBirthDate(original.getBirthDate().copy());
		copy.setBirthPlace(original.getBirthPlace());
		if (original.getDeathDate() != null)
			copy.setDeathDate(original.getDeathDate().copy());
		copy.setDeathPlace(original.getDeathPlace());
		copy.setLifeStatus(original.getLifeStatus());
		copy.setSex(original.getSex());
		copy.setAlias(original.getAlias());
		copy.setBaptismParish(original.getBaptismParish());
		copy.setBurialPlace(original.getBurialPlace());
		copy.setContact(original.getContact());
		copy.setComments(original.getComments());
		
		return copy;
	}
}
