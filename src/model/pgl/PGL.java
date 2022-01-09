package model.pgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
	
	public Optional<Section> getSection(String name) {
		if (name == null) throw new IllegalArgumentException("Section name cannot be null.");
		
		List<Section> sections = getSections(name);
		
		if (sections.isEmpty())
			return Optional.empty();
		
		return Optional.of(sections.get(sections.size()-1));
	}
	
	public List<Section> getSections(String name) {
		if (name == null) throw new IllegalArgumentException("Section name cannot be null.");
		
		return sections.stream().
				filter(section -> section.getName().toUpperCase().equals(name.toUpperCase())).
				collect(Collectors.toUnmodifiableList());
	}
	
	public String getValue(String sectionName, String keyName) {
		if (sectionName == null || keyName == null)
			throw new IllegalArgumentException("Section name and key name cannot be null.");
		
		
		List<String> values = getValues(sectionName, keyName);
		
		if (values.isEmpty())
			return null;
		
		return values.get(values.size()-1);
	}
	
	public List<String> getValues(String sectionName, String keyName) {
		if (sectionName == null || keyName == null)
			throw new IllegalArgumentException("Section name and key name cannot be null.");
		
		
		List<Section> sections = getSections(sectionName);
		List<String> values = sections.stream().
				flatMap(section -> section.getValues(keyName).stream()).
				collect(Collectors.toList());
		
		return values;
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
