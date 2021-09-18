package model.pgl.saver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import model.LifeStatus;
import model.MyDate;
import model.Sex;
import model.pgl.Section;
import model.pgl.saver.SectionWriter;
import model.pgl.PGLFields;

public class SectionWriterTest {

	private Section section;
	private SectionWriter writer;
	
	private void prepare() {
		section = new Section("A");
		writer = new SectionWriter(section);
	}
	
	@Test
	public void emptyString() {
		prepare();
		writer.saveProperty("a", "");
		assertNull(section.getValue("a"));
	}
	
	@Test
	public void string() {
		prepare();
		writer.saveProperty("a", "value");
		assertEquals("value", section.getValue("a"));
	}
	
	
	
	@Test
	public void integer() {
		prepare();
		writer.saveProperty("a", 12);
		assertEquals("12", section.getValue("a"));
	}
	
	@Test
	public void integer_negative() {
		prepare();
		writer.saveProperty("a", -120);
		assertEquals("-120", section.getValue("a"));
	}

	
	
	@Test
	public void myDate() {
		prepare();
		MyDate data = new MyDate(17, 6, 2020);
		
		writer.saveProperty("a", data);
		assertEquals(data, new MyDate(section.getValue("a")));
	}

	@Test
	public void date() {
		prepare();
		Date date = new GregorianCalendar(2020, Calendar.JUNE, 17).getTime();

		writer.saveProperty("a", date);
		assertEquals(PGLFields.dataFormat.format(date), section.getValue("a"));
	}



	@Test
	public void lifeStatus_yes() {
		prepare();
		writer.saveProperty(LifeStatus.YES);
		assertEquals(LifeStatus.YES.toString(), section.getValue(PGLFields.lifeStatus));
	}
	
	@Test
	public void lifeStatus_no() {
		prepare();
		writer.saveProperty(LifeStatus.NO);
		assertEquals(LifeStatus.NO.toString(), section.getValue(PGLFields.lifeStatus));
	}
	
	@Test
	public void lifeStatus_unknow() {
		prepare();
		writer.saveProperty(LifeStatus.UNKNOWN);
		assertNull(section.getValue(PGLFields.lifeStatus));
	}



	@Test
	public void propertySex_woman() {
		prepare();
		writer.saveProperty(Sex.WOMAN);
		assertEquals(Sex.WOMAN.toString(), section.getValue(PGLFields.sex));
	}

	@Test
	public void propertySex_man() {
		prepare();
		writer.saveProperty(Sex.MAN);
		assertEquals(Sex.MAN.toString(), section.getValue(PGLFields.sex));
	}

	@Test
	public void propertySex_unknown() {
		prepare();
		writer.saveProperty(Sex.UNKNOWN);
		assertNull(section.getValue(PGLFields.sex));
	}

}
