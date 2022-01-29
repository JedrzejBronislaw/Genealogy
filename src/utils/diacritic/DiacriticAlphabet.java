package utils.diacritic;

import java.util.Arrays;
import java.util.List;

class DiacriticAlphabet {
	
	private List<DiacriticPair> diacriticPairs;
	
	
	public DiacriticAlphabet(DiacriticPair... pairs) {
		diacriticPairs = Arrays.asList(pairs);
	}
	
	
	public String replaceDiacriticChars(String text) {
		for (DiacriticPair dp : diacriticPairs)
			text = text.replace(dp.getDiacritic(), dp.getNonDiacritic());
		
		return text;
	}
}
