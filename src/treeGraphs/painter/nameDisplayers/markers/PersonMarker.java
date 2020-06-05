package treeGraphs.painter.nameDisplayers.markers;

import lombok.AllArgsConstructor;
import lombok.Setter;
import model.Person;
import treeGraphs.painter.MyColor;

@AllArgsConstructor
public class PersonMarker extends ColorMarker {

	@Setter
	private Person person;
	
	@Override
	public boolean check(Person person) {
		return person == this.person;
	}

	@Override
	protected MyColor getColor(Person person) {
		return new MyColor(255, 0, 0);
	}
}
