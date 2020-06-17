package model.pgl;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import model.MyDate;
import model.Person.LifeStatus;
import model.Person.Sex;
import model.pgl.virtual.INISection;

@RequiredArgsConstructor
public class SectionDataWriter {

	private final INISection section;
	public static final SimpleDateFormat dataFormat = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
	public static final String lineSeparator = "$";

	public void saveProperty(String name, String value) {
		if (value == null || value.isEmpty()) return;
		section.addKey(name, value.replace(System.lineSeparator(), lineSeparator));
	}

	public void saveProperty(String name, int value) {
		section.addKey(name, Integer.toString(value));
	}

	public void saveProperty(String name, MyDate value) {
		if (value == null) return;
		section.addKey(name, value.toString());
	}

	public void saveProperty(String name, Date value) {
		if (value == null) return;
		section.addKey(name, dataFormat.format(value));
	}

	public void saveProperty(LifeStatus value) {
		if (value == null || value == LifeStatus.UNKNOWN) return;

		if (value == LifeStatus.NO)  saveProperty("zyje", 0);
		if (value == LifeStatus.YES) saveProperty("zyje", 1);
	}

	public void saveProperty(Sex value) {
		if (value == null || value == Sex.UNKNOWN) return;

		if (value == Sex.WOMAN) saveProperty("plec", 0);
		if (value == Sex.MAN)   saveProperty("plec", 1);
	}
}
