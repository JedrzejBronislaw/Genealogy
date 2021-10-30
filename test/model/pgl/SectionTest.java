package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import model.pgl.Section;

public class SectionTest {
	

	@Test
	public void shouldReturnValue() {
		// given
		Section section = new Section("");
		String key = "key";
		String value = "value";
		section.addKey(key, value);
		
		// when
		String returnedValue = section.getValue(key);
		
		// then
		assertEquals("value", returnedValue);
	}

	@Test
	public void shouldReturnNullWhenGetNotExistingKey() {
		// given
		Section section = new Section("");
		section.addKey("key", "value");
		
		// when
		String returnedValue = section.getValue("otherKey");
		
		// then
		assertNull(returnedValue);
	}

	@Test
	public void shouldReturnNullValueFromEmptySection() {
		// given
		Section section = new Section("");
		
		// when
		String returnedValue = section.getValue("key");
		
		// then
		assertNull(returnedValue);
	}

	@Test
	public void shouldTrimKeyAndValueWhenAddNewKey() {
		//TODO wrong behavior
		// given
		Section section = new Section("");
		
		// when
		section.addKey("key ", "value ");
		
		// then
		assertNull(section.getValue("key "));
		assertEquals("value", section.getValue("key"));
	}
	
	
	@Test
	public void shoulReturnOptionalWhenKeyExists() {
		// given
		Section section = new Section("");
		String key = "key";
		String value = "value";
		section.addKey(key, value);
		
		// when
		String returnedValue = section.value(key).get();

		// then
		assertEquals(value, returnedValue);
	}
	
	@Test
	public void shoulReturnOptionalWhenKeyDoesntExist() {
		// given
		Section section = new Section("");
		
		// when
		boolean valueExists = section.value("notExistingKey").isPresent();
		
		// then
		assertFalse(valueExists);
	}
}
