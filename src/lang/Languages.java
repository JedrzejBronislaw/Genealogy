package lang;

import java.util.Locale;

import lombok.Getter;

public enum Languages {
	POLISH(new Locale("pl","PL"), "polish", "PL"),
	ENGLISH(Locale.ENGLISH, "english", "EN");
	
	private static Languages defaultLanguage = ENGLISH;
	
	@Getter
	private Locale locale;
	@Getter
	private String label;
	@Getter
	private String abbr;
	
	private Languages(Locale locale, String label, String abbr) {
		this.locale = locale;
		this.label = label;
		this.abbr = abbr;
	}
	
	Languages get(Locale language) {
		if (language == null) return defaultLanguage;
		
		Languages[] langs = Languages.values();
		
		for(Languages l : langs)
			if (l.locale.equals(language))
				return l;
		
		return defaultLanguage;
	}

}
