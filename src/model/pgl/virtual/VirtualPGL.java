package model.pgl.virtual;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class VirtualPGL {

	private List<INISection> sections = new ArrayList<>();
	
	public INISection newSection(String name) {
		INISection section = new INISection(name);
		sections.add(section);
		return section;
	}
	
	public Optional<INISection> get(String name) {
		return sections.stream().
				filter(section -> section.getName().toUpperCase().equals(name.toUpperCase())).
				findFirst();
	}
	
	public String getValue(String sectionName, String keyName) {
		Optional<INISection> section = get(sectionName);
		if (section.isEmpty()) return null;
		
		return section.get().getValue(keyName);
	}
	
	public void forEachSession(Consumer<INISection> action) {
		sections.forEach(action);
	}
	
	public int size() {
		return sections.size();
	}
}
