package tools;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Maxer {

	@Getter
	private int max;
	
	public Maxer add(int... value) {
		for (int v : value)
			max = Math.max(max, v);
		
		return this;
	}
	
	public int get() {
		return max;
	}
}
