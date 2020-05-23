package treeGraphs;

import lombok.Builder;
import lombok.Getter;
import model.Person;
import nameDisplaying.NameDisplayingType;

@Builder
@Getter
public class TreeGraphParameters {

	private Person person;
	private TreeGraphType graphType;
	private NameDisplayingType nameDisplaying;
	
	private String painterType;

	public boolean isReady() {
		return person != null
			&& graphType != null
			&& nameDisplaying != null;
	}
}
