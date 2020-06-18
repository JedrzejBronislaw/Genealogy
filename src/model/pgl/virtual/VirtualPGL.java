package model.pgl.virtual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class VirtualPGL {

	private List<INISection> sections = new ArrayList<>();
	
	List<INISection> getSections() {
		return Collections.unmodifiableList(sections);
	}
	
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
	
	public void forEachSection(Consumer<INISection> action) {
		sections.forEach(action);
	}
	
	public int size() {
		return sections.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof VirtualPGL)) return false;
		
		VirtualPGL pgl = (VirtualPGL) obj;
		
		return sections.equals(pgl.sections);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		forEachSection(section -> {
			buffer.append("[" + section.getName() + "]" + System.lineSeparator());
			section.forEachKey((k, v) -> buffer.append(k + "=" + v + System.lineSeparator()));
		});
		
		return buffer.toString();
	}
}
