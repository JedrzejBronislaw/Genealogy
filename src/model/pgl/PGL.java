package model.pgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class PGL {

	private List<Section> sections = new ArrayList<>();
	
	public List<Section> getSections() {
		return Collections.unmodifiableList(sections);
	}
	
	public Section newSection(String name) {
		Section section = new Section(name);
		sections.add(section);
		return section;
	}
	
	public Optional<Section> get(String name) {
		return sections.stream().
				filter(section -> section.getName().toUpperCase().equals(name.toUpperCase())).
				findFirst();
	}
	
	public String getValue(String sectionName, String keyName) {
		Optional<Section> section = get(sectionName);
		if (section.isEmpty()) return null;
		
		return section.get().getValue(keyName);
	}
	
	public void forEachSection(Consumer<Section> action) {
		sections.forEach(action);
	}
	
	public int size() {
		return sections.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof PGL)) return false;
		
		PGL pgl = (PGL) obj;
		
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
