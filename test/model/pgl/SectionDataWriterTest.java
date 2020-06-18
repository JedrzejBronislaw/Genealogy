package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import model.MyDate;
import model.Person.LifeStatus;
import model.Person.Sex;
import model.pgl.virtual.INISection;

public class SectionDataWriterTest {

	private INISection section;
	private SectionDataWriter writer;
	
	private void prepare() {
		section = new INISection("A");
		writer = new SectionDataWriter(section);
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
		assertEquals("1", section.getValue("zyje"));
	}
	
	@Test
	public void lifeStatus_no() {
		prepare();
		writer.saveProperty(LifeStatus.YES);
		assertEquals("1", section.getValue("zyje"));
	}
	
	@Test
	public void lifeStatus_unknow() {
		prepare();
		writer.saveProperty(LifeStatus.UNKNOWN);
		assertNull(section.getValue("zyje"));
	}



	@Test
	public void propertySex_woman() {
		prepare();
		writer.saveProperty(Sex.WOMAN);
		assertEquals("0", section.getValue("plec"));
	}

	@Test
	public void propertySex_man() {
		prepare();
		writer.saveProperty(Sex.MAN);
		assertEquals("1", section.getValue("plec"));
	}

	@Test
	public void propertySex_unknown() {
		prepare();
		writer.saveProperty(Sex.UNKNOWN);
		assertNull(section.getValue("plec"));
	}

}
