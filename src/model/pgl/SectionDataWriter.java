package model.pgl;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import model.LifeStatus;
import model.MyDate;
import model.Sex;
import model.pgl.virtual.INISection;

@RequiredArgsConstructor
public class SectionDataWriter {

	private final INISection section;

	public void saveProperty(String name, String value) {
		if (value == null || value.isEmpty()) return;
		section.addKey(name, value.replace(System.lineSeparator(), PGLFields.lineSeparator));
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
		section.addKey(name, PGLFields.dataFormat.format(value));
	}

	public void saveProperty(LifeStatus value) {
		if (value == null || value == LifeStatus.UNKNOWN) return;

		if (value == LifeStatus.NO)  saveProperty(PGLFields.lifeStatus, 0);
		if (value == LifeStatus.YES) saveProperty(PGLFields.lifeStatus, 1);
	}

	public void saveProperty(Sex value) {
		if (value == null || value == Sex.UNKNOWN) return;

		if (value == Sex.WOMAN) saveProperty(PGLFields.sex, 0);
		if (value == Sex.MAN)   saveProperty(PGLFields.sex, 1);
	}
}
