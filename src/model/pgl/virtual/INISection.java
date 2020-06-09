package model.pgl.virtual;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class INISection {
	
	@AllArgsConstructor
	public class NullValidator {
		@Getter private String value;
		
		public void ifNotNull(Consumer<String> action) {
			if (value != null) action.accept(value);
		}
	}
	
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

	public NullValidator value(String keyName) {
		return new NullValidator(getValue(keyName));
	}
}
