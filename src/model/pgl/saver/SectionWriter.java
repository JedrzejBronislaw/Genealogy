package model.pgl.saver;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import model.LifeStatus;
import model.MyDate;
import model.Sex;
import model.pgl.Section;
import model.pgl.PGLFields;

@RequiredArgsConstructor
public class SectionWriter {

	private final Section section;

	public void saveProperty(String name, String value) {
		if (value == null || value.isEmpty()) return;
		section.addKey(name, value.replace(System.lineSeparator(), PGLFields.lineSeparator));
	}

	public void saveProperty(String name, int value) {
		section.addKey(name, Integer.toString(value));
	}

	public void saveProperty(String name, MyDate value) {
		if (value == null || value.isEmpty()) return;
		section.addKey(name, value.toString());
	}

	public void saveProperty(String name, Date value) {
		if (value == null) return;
		section.addKey(name, PGLFields.dataFormat.format(value));
	}

	public void saveProperty(LifeStatus value) {
		if (value == null || value == LifeStatus.UNKNOWN) return;
		saveProperty(PGLFields.lifeStatus, value.toString());
	}

	public void saveProperty(Sex value) {
		if (value == null || value == Sex.UNKNOWN) return;
		saveProperty(PGLFields.sex, value.toString());
	}
}
