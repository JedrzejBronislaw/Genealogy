package nameDisplaying;

import java.lang.reflect.InvocationTargetException;

public enum NameDisplayingType {
	onlyName(SimpleNameDisplaying.class),
	dateAndName(DateAndNameDisplaying.class),
	dateUnderName(DateUnderNameDisplaying.class);
	
	private Class<? extends Name> nameClass;
	
	private NameDisplayingType(Class<? extends Name> nameClass) {
		this.nameClass = nameClass;
	}
	
	public Name createDisplaying() {
		try {
			return (Name) nameClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException | NoSuchMethodException e) {

			e.printStackTrace();
			return null;
		}
	}

	boolean ifMatch(Class<? extends Name> nameClass) {
		return this.nameClass == nameClass;
	}

	public static NameDisplayingType get(Class<? extends Name> nameClass) {
		NameDisplayingType[] values = values();
		
		for (NameDisplayingType type : values)
			if (type.ifMatch(nameClass)) return type;
		
		return null;
	}
}
