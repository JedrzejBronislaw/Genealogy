package model;

public enum LifeStatus {
	YES("1"),
	NO("0"),
	UNKNOWN("");
	
	private String stringValue;
	
	private LifeStatus(String value) {
		this.stringValue = value;
	}
	
	@Override
	public String toString() {
		return stringValue;
	}
	
	public static LifeStatus get(String value) {
		LifeStatus[] values = LifeStatus.values();
		
		for (int i=0; i<values.length; i++)
			if (values[i].stringValue.equals(value)) return values[i];
		
		return LifeStatus.UNKNOWN;
	}
}
