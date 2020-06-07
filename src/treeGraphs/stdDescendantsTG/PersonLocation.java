package treeGraphs.stdDescendantsTG;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import model.Person;

@AllArgsConstructor
@RequiredArgsConstructor
public class PersonLocation extends Area {

	@Getter private final Person person;
	@Getter private final int generation;
	@Getter private boolean spouse = false;

	
	@Override
	public String toString() {
		return person.nameSurname() + " " +
				getHeight() + "x" + getWidth() + " " +
				getLocation().toString();
	}
}
