package model.pgl.virtual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class INISectionTest {
	
	class ValueBox {
		@Setter @Getter
		private String value;
	}
	

	@Test
	public void testGetValue() {
		INISection section = new INISection("");
		section.addKey("abc", "def");
		
		assertEquals("def", section.getValue("abc"));
	}

	@Test
	public void testGetNullValue() {
		INISection section = new INISection("");
		section.addKey("abc", "def");
		
		assertNull("def", section.getValue("abcd"));
	}

	@Test
	public void testGetValueFromEmptySection() {
		INISection section = new INISection("");
		
		assertNull(section.getValue("abc"));
	}

	@Test
	public void testGetValue_trim() {
		INISection section = new INISection("");
		section.addKey("abc ", "def ");
		
		assertNull(section.getValue("abc "));
		assertEquals("def", section.getValue("abc"));
	}
	
	
	@Test
	public void testNotNullValue() {
		INISection section = new INISection("");
		section.addKey("abc", "def");
		final ValueBox value = new ValueBox();
		
		section.value("abc").ifPresent(value::setValue);
		assertEquals("def", value.getValue());
	}
	
	@Test
	public void testNullValue() {
		INISection section = new INISection("");
		final ValueBox value = new ValueBox();
		
		section.value("abc").ifPresent(value::setValue);
		assertNull(value.getValue());
	}
}
