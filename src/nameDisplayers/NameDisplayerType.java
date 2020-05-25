package nameDisplayers;

import java.lang.reflect.InvocationTargetException;

public enum NameDisplayerType {
	onlyName(SimpleNameDisplayer.class),
	dateAndName(DateAndNameDisplayer.class),
	dateUnderName(DateUnderNameDisplayer.class);
	
	private Class<? extends NameDisplayer> nameClass;
	
	private NameDisplayerType(Class<? extends NameDisplayer> nameClass) {
		this.nameClass = nameClass;
	}
	
	public NameDisplayer createDisplayer() {
		try {
			return (NameDisplayer) nameClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException | NoSuchMethodException e) {

			e.printStackTrace();
			return null;
		}
	}

	boolean ifMatch(Class<? extends NameDisplayer> nameClass) {
		return this.nameClass == nameClass;
	}

	public static NameDisplayerType get(Class<? extends NameDisplayer> nameClass) {
		NameDisplayerType[] values = values();
		
		for (NameDisplayerType type : values)
			if (type.ifMatch(nameClass)) return type;
		
		return null;
	}
}
