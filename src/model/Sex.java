package model;

public enum Sex {
	WOMAN("0"),
	MAN("1"),
	UNKNOWN("");
	
	private String stringValue;
	
	private Sex(String value) {
		this.stringValue = value;
	}
	
	@Override
	public String toString() {
		return stringValue;
	}
	
	public static Sex get(String value) {
		Sex[] values = Sex.values();
		
		for (int i=0; i<values.length; i++)
			if (values[i].stringValue.equals(value)) return values[i];
		
		return Sex.UNKNOWN;
	}
}
