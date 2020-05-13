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

	
	public String marriageToString(Marriage marriage) {
		if (marriage == null) return null;
		
		Person husband = marriage.getHusband();
		Person wife = marriage.getWife();
		String date = marriage.getDate();
		String place = marriage.getPlace();
		
		String husbandStr = (husband == null) ? "" : tree.getID(husband);
		String wifeStr    = (wife == null)    ? "" : tree.getID(wife);
		String dateStr    = (date == null)    ? "" : date;
		String placeStr   = (place == null)   ? "" : place;
		
		if (husbandStr == null || wifeStr == null) return null;
		
		String outcome = String.join(";",
				husbandStr,
				wifeStr,
				dateStr,
				placeStr
				);
		
		return outcome;
	}
	
	public Marriage stringToMarriage(String string) {
		if (string == null) return null;
		String[] parts = string.split(";");
		
		if (parts.length > 4) return null;
		
		Person husband = null;
		Person wife = null;
		String date = null;
		String place = null;

		Person person;
		
		if (parts.length > 0 && !parts[0].isEmpty()) {
				person = tree.getPerson(parts[0]);
				if (person == null) return null;
				husband = person;
			};
		if (parts.length > 1 && !parts[1].isEmpty()) {
				person = tree.getPerson(parts[1]);
				if (person == null) return null;
				wife = person;
			};
		
		if (parts.length > 2 && !parts[2].isEmpty()) date  = parts[2];
		if (parts.length > 3 && !parts[3].isEmpty()) place = parts[3];
		
		Marriage marriage = new Marriage();
		marriage.setHusband(husband);
		marriage.setWife(wife);
		marriage.setDate(date);
		marriage.setPlace(place);
		
		return marriage;
	}

	public String marriagesToString(List<Marriage> marriages) {
		if (marriages == null) return null;
		
		StringBuffer sb = new StringBuffer();
		
		for(Marriage marriage : marriages) {
			String marriageString = marriageToString(marriage);
			if (marriageString == null) return null;
			
			sb.append(marriageString);
			sb.append(LINESEPARATOR);
		}
		
		return sb.toString().trim();
	}
	
	public List<Marriage> stringToMarriages(String string) {
		if (string == null) return null;

		List<Marriage> marriageList = new ArrayList<>();
		String[] marriagesString = string.split(LINESEPARATOR);
		Marriage marriage;
		
		for(String marriageString : marriagesString) {
			if (marriageString.isEmpty()) continue;
			
			marriage = stringToMarriage(marriageString);
			if (marriage == null) return null;
			marriageList.add(marriage);
		}
		
		return marriageList;
	}
}
