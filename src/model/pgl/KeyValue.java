package model.pgl;

import java.util.Objects;

import lombok.Getter;

public class KeyValue {
	
	@Getter private String key;
	@Getter private String value;

	public KeyValue(String key, String value) {
		if (key == null) throw new IllegalArgumentException("Key cannot be null.");
		if (value == null) throw new IllegalArgumentException("Value cannot be null.");
		
		this.key = key;
		this.value = value;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof KeyValue)) return false;
		KeyValue otherPair = (KeyValue) obj;

		if (!Objects.equals(this.key, otherPair.key)) return false;
		if (!Objects.equals(this.value, otherPair.value)) return false;
		
		return true;
	}
}
