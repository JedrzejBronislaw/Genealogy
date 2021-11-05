package model.pgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import lombok.Getter;

public class Section {
	
	@Getter private String name;
	private List<KeyValue> keys = new ArrayList<>();

	
	public List<KeyValue> getKeys() {
		return Collections.unmodifiableList(keys);
	}
	
	public Set<String> getKeySet() {
		return keys.stream().map(KeyValue::getKey).distinct().collect(Collectors.toUnmodifiableSet());
	}
	
	public Section(String name) {
		if (name == null) throw new IllegalArgumentException("Section name cannot be null.");
		
		this.name = name;
	}
	
	public void addKey(String name, String value) {
		if (name == null || value == null)
			throw new IllegalArgumentException("Key and value must be not-null.");
		
		keys.add(new KeyValue(name, value));
	}
	
	public String getValue(String keyName) {
		if (keyName == null) throw new IllegalArgumentException("Key name cannot be null.");
		
		List<String> allValues = getValues(keyName);
		
		if (allValues.isEmpty()) return null;
		return allValues.get(allValues.size()-1);
	}
	
	public List<String> getValues(String keyName) {
		return keys.stream()
				.filter(pair -> pair.getKey().equals(keyName))
				.map(pair -> pair.getValue())
				.collect(Collectors.toUnmodifiableList());
	}

	public Optional<String> value(String keyName) {
		return Optional.ofNullable(getValue(keyName));
	}
	
	public int size() {
		return keys.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Section)) return false;
		
		Section section = (Section) obj;
		
		return name.equals(section.name) &&
		       keys.equals(section.keys);
	}

	public void forEachKey(BiConsumer<String, String> action) {
		keys.forEach(pair -> action.accept(pair.getKey(), pair.getValue()));
	}
}
