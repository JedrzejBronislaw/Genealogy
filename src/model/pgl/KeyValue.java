package model.pgl;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class KeyValue {
	
	@Getter private String key;
	@Getter private String value;
	
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
