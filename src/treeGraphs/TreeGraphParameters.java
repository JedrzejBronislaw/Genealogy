package treeGraphs;

import lombok.Builder;
import lombok.Getter;
import model.Person;

@Builder
@Getter
public class TreeGraphParameters {

	private Person person;
	private TreeGraphType graphType;
	
	private String painterType;

	public boolean isReady() {
		return person != null && graphType != null;
	}
}
