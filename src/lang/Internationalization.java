package lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.Getter;

public class Internationalization {
	static final String resourceLocation = "lang.labels";
	private static ResourceBundle rb = ResourceBundle.getBundle(resourceLocation);
	
	
	@Getter
	private static Languages currentLanguage;
	
	private Internationalization() {};
	
	
	public static String get(String key) {
		try {
			return rb.getString(key);
		} catch(MissingResourceException e) {
			e.printStackTrace();
			return key;
		}
	}
	
	private static void refresh() {
		rb = ResourceBundle.getBundle(resourceLocation);
	}
	
	public static void setLanguage(Languages language) {
		Locale.setDefault(language.getLocale());
		refresh();
		currentLanguage = language;
	}
}
