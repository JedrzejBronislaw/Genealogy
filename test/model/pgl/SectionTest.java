package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import model.pgl.Section;

public class SectionTest {
	
	class ValueBox {
		@Setter @Getter
		private String value;
	}
	

	@Test
	public void testGetValue() {
		Section section = new Section("");
		section.addKey("abc", "def");
		
		assertEquals("def", section.getValue("abc"));
	}

	@Test
	public void testGetNullValue() {
		Section section = new Section("");
		section.addKey("abc", "def");
		
		assertNull("def", section.getValue("abcd"));
	}

	@Test
	public void testGetValueFromEmptySection() {
		Section section = new Section("");
		
		assertNull(section.getValue("abc"));
	}

	@Test
	public void testGetValue_trim() {
		Section section = new Section("");
		section.addKey("abc ", "def ");
		
		assertNull(section.getValue("abc "));
		assertEquals("def", section.getValue("abc"));
	}
	
	
	@Test
	public void testNotNullValue() {
		Section section = new Section("");
		section.addKey("abc", "def");
		final ValueBox value = new ValueBox();
		
		section.value("abc").ifPresent(value::setValue);
		assertEquals("def", value.getValue());
	}
	
	@Test
	public void testNullValue() {
		Section section = new Section("");
		final ValueBox value = new ValueBox();
		
		section.value("abc").ifPresent(value::setValue);
		assertNull(value.getValue());
	}
}
