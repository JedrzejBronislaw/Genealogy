package model.pgl.virtual;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;

public class INISection {
	
	@Getter private String name;
	
	private Map<String, String> keys = new HashMap<String, String>();
	
	Map<String, String> getKeys() {
		return Collections.unmodifiableMap(keys);
	}
	
	public INISection(String name) {
		this.name = name;
	}
	
	public void addKey(String name, String value) {
		keys.put(name.trim(), value.trim());
	}
	
	public String getValue(String keyName) {
		return keys.get(keyName);
	}

	public Optional<String> value(String keyName) {
		return Optional.ofNullable(getValue(keyName));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof INISection)) return false;
		
		INISection section = (INISection) obj;
		
		return name.equals(section.name) &&
		       keys.equals(section.keys);
	}
}
