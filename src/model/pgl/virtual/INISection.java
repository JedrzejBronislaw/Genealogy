package model.pgl.virtual;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;

public class INISection {
	
	@Getter private String name;
	
	private Map<String, String> keys = new HashMap<String, String>();
	
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
}
