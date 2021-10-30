package model.pgl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import lombok.Getter;

public class Section {
	
	@Getter private String name;
	
	private Map<String, String> keys = new HashMap<String, String>();
	
	public Map<String, String> getKeys() {
		return Collections.unmodifiableMap(keys);
	}
	
	public Section(String name) {
		if (name == null) throw new IllegalArgumentException("Section name cannot be null.");
		
		this.name = name;
	}
	
	public void addKey(String name, String value) {
		if (name == null || value == null)
			throw new IllegalArgumentException("Key and value must be not-null.");
		
		keys.put(name.trim(), value.trim());
	}
	
	public String getValue(String keyName) {
		if (keyName == null) throw new IllegalArgumentException("Key name cannot be null.");
		
		return keys.get(keyName);
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
		keys.forEach(action);
	}
}
