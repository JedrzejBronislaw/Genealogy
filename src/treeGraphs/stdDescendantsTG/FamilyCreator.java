package treeGraphs.stdDescendantsTG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Person;
import treeGraphs.painter.nameDisplayers.NameDisplayer;

public class FamilyCreator {

	public Family create(Person person, NameDisplayer displayer) {
		return create(person, displayer, GraphParameters.DEFAULT);
	}
	
	public Family create(Person person, NameDisplayer displayer, GraphParameters params) {
		
		Family family = new Family(person, 0, params);
		setNameSizes(family, displayer);
		setGenerationsWidth(family);
		
		return family;
	}


	private void setGenerationsWidth(Family family) {
		createGenerations(family).forEach(generation -> {
			int width = generation.computeWidth();
			generation.setPersonsWidth(width);
		});
	}

	private List<Generation> createGenerations(Family family) {
		List<Generation> generations = new ArrayList<>();
		List<PersonLocation> allPersons = family.getPersonLocationsStream().collect(Collectors.toList());
		int genNumber = 0;
		
		while (true) {
			int genNum = genNumber;
			List<PersonLocation> generation = allPersons.stream()
					.filter(person -> person.getGeneration() == genNum)
					.collect(Collectors.toList());
			if (generation.isEmpty()) break;
			
			generations.add(new Generation(generation));
			
			genNumber++;
		}
		
		return generations;
	}

	private void setNameSizes(Family family, NameDisplayer displayer) {
		family.getPersonLocations().forEach(person -> {
			person.setHeight(displayer.getHeight(person.getPerson()));
			person.setWidth(displayer.getWidth(person.getPerson()));
		});
	}
}
