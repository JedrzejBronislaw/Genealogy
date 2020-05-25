package treeGraphs;

import lombok.Builder;
import lombok.Getter;
import model.Person;
import treeGraphs.painter.nameDisplayers.NameDisplayerType;

@Builder
@Getter
public class TreeGraphParameters {

	private Person person;
	private TreeGraphType graphType;
	private NameDisplayerType nameDisplayerType;
	
	private String painterType;

	public boolean isReady() {
		return person != null
			&& graphType != null
			&& nameDisplayerType != null;
	}
}
