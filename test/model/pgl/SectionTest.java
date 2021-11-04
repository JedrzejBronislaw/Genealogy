package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.junit.Test;

import model.pgl.Section;

public class SectionTest {

	
	@Test
	public void shouldNamedSection() {
		// given
		// when
		Section section = new Section("sectionName");
		
		// then
		assertEquals("sectionName", section.getName());
	}

	@Test
	public void shouldCreateSectionWithoutName() {
		// given
		// when
		Section section = new Section("");
		
		// then
		assertEquals("", section.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateSectionWithNullName() {
		// given
		// when
		// then
		new Section(null);
	}

	@Test
	public void shouldReturnValue() {
		// given
		Section section = new Section("sectionName");
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
		Section section = new Section("sectionName");
		section.addKey("key", "value");
		
		// when
		String returnedValue = section.getValue("otherKey");
		
		// then
		assertNull(returnedValue);
	}

	@Test
	public void shouldReturnNullValueFromEmptySection() {
		// given
		Section section = new Section("sectionName");
		
		// when
		String returnedValue = section.getValue("key");
		
		// then
		assertNull(returnedValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenGetNullKey() {
		// given
		Section section = new Section("sectionName");
		
		// when
		// then
		section.getValue(null);
	}

	@Test
	public void shouldReturnTheLastValueWithTheSameKey() {
		// given
		Section section = new Section("sectionName");
		String key = "key";
		String firstValue = "value1";
		String lastValue = "value2";
		section.addKey(key, firstValue);
		section.addKey(key, lastValue);
		
		// when
		String returnedValue = section.getValue(key);
		
		// then
		assertEquals(lastValue, returnedValue);
	}

	@Test
	public void shouldReturnAllValuesWithTheSameKey() {
		// given
		Section section = new Section("sectionName");
		String key = "key";
		String firstValue = "value1";
		String lastValue = "value2";
		section.addKey(key, firstValue);
		section.addKey(key, lastValue);
		
		// when
		List<String> returnedValues = section.getValues(key);
		
		// then
		assertEquals(2, returnedValues.size());
		assertTrue(returnedValues.contains(firstValue));
		assertTrue(returnedValues.contains(lastValue));
	}

	@Test
	public void shouldReturnOneElementListWhenExistOneValueWithSpecificKey() {
		// given
		Section section = new Section("sectionName");
		String key = "key";
		String value = "value";
		section.addKey(key, value);
		
		// when
		List<String> returnedValues = section.getValues(key);
		
		// then
		assertEquals(1, returnedValues.size());
		assertTrue(returnedValues.contains(value));
	}

	@Test
	public void shouldReturnEmptyListWhenNotExistValueWithSpecificKey() {
		// given
		Section section = new Section("sectionName");
		section.addKey("key", "value");
		
		// when
		List<String> returnedValues = section.getValues("otherKey");
		
		// then
		assertTrue(returnedValues.isEmpty());
	}
	
	//

	@Test
	public void shouldNotTrimKeyWhenAddNewKey() {
		// given
		Section section = new Section("sectionName");
		
		// when
		section.addKey("key ", "value");
		
		// then
		assertTrue(section.value("key").isEmpty());
		assertTrue(section.value("key ").isPresent());
		assertEquals("value", section.getValue("key "));
	}

	@Test
	public void shouldNotTrimValueWhenAddNewKey() {
		// given
		Section section = new Section("sectionName");
		
		// when
		section.addKey("key", "value ");
		
		// then
		assertEquals("value ", section.getValue("key"));
	}
	
	
	@Test
	public void shoulReturnOptionalWhenKeyExists() {
		// given
		Section section = new Section("sectionName");
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
		Section section = new Section("sectionName");
		
		// when
		boolean valueExists = section.value("notExistingKey").isPresent();
		
		// then
		assertFalse(valueExists);
	}
	
	//

	@Test
	public void shouldAddNewKey() {
		// given
		Section section = new Section("sectionName");
		String key = "key";
		String value = "value";
		
		// when
		section.addKey(key, value);
		
		// then
		assertEquals(value, section.getValue(key));
	}

	@Test
	public void shouldAddValueWithTheSameKey() {
		// given
		Section section = new Section("sectionName");
		String key = "key";
		
		// when
		section.addKey(key, "value1");
		section.addKey(key, "value2");
		
		// then
		assertEquals(2, section.size());
	}

	@Test
	public void shouldAddTwoTheSameValue() {
		// given
		Section section = new Section("sectionName");
		String value = "value";
		
		// when
		section.addKey("key1", value);
		section.addKey("key2", value);
		
		// then
		assertEquals(2, section.size());
	}

	@Test
	public void shouldAddValueWithEmptyKey() {
		// given
		Section section = new Section("sectionName");
		String key = "";
		String value = "value";
		
		// when
		section.addKey(key, value);
		
		// then
		assertEquals(value, section.getValue(key));
	}

	@Test
	public void shouldAddEmptyValue() {
		// given
		Section section = new Section("sectionName");
		String key = "key";
		String value = "";
		
		// when
		section.addKey(key, value);
		
		// then
		assertEquals(value, section.getValue(key));
	}

	@Test
	public void shouldAddEmptyValueWithEmptyKey() {
		// given
		Section section = new Section("sectionName");
		String key = "";
		String value = "";
		
		// when
		section.addKey(key, value);
		
		// then
		assertEquals(value, section.getValue(key));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenAddValueWithNullKey() {
		// given
		Section section = new Section("sectionName");
		
		// when
		// then
		section.addKey(null, "value");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenAddNullValue() {
		// given
		Section section = new Section("sectionName");
		
		// when
		// then
		section.addKey("key", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenAddNullValueWithNullKey() {
		// given
		Section section = new Section("sectionName");
		
		// when
		// then
		section.addKey(null, null);
	}
	
	//

	@Test
	public void shouldExecuteTaskForEachValues() {
		// given
		Section section = new Section("sectionName");
		List<String> list = new ArrayList<>();
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		section.addKey(key1, value1);
		section.addKey(key2, value2);
		
		BiConsumer<String, String> task = (key, value) -> {
			list.add(key);
			list.add(value);
		};
		
		// when
		section.forEachKey(task);
		
		// then
		assertTrue(list.contains(key1));
		assertTrue(list.contains(value1));
		assertTrue(list.contains(key2));
		assertTrue(list.contains(value2));
	}

	@Test
	public void shouldExecuteTaskForEachValuesWithTheSameKey() {
		// given
		Section section = new Section("sectionName");
		List<String> list = new ArrayList<>();
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String value3 = "value3";
		section.addKey(key1, value1);
		section.addKey(key2, value2);
		section.addKey(key2, value3);
		
		BiConsumer<String, String> task = (key, value) -> {
			list.add(key);
			list.add(value);
		};
		
		// when
		section.forEachKey(task);
		
		// then
		assertTrue(list.contains(key1));
		assertTrue(list.contains(value1));
		assertTrue(list.contains(key2));
		assertTrue(list.contains(value2));
		assertTrue(list.contains(value3));
		assertEquals(6, list.size());
		assertEquals(2, list.stream().filter(el -> el.equals(key2)).count());
	}

	@Test
	public void shouldBeKeyFirstAndValueSecondArgumentInTaskForEachValues() {
		// given
		Section section = new Section("sectionName");
		List<String> list = new ArrayList<>();
		String key = "key";
		String value = "value";
		section.addKey(key, value);
		
		BiConsumer<String, String> task = (k, v) -> {
			list.add(k);
			list.add(v);
		};
		
		// when
		section.forEachKey(task);
		
		// then
		assertEquals(key, list.get(0));
		assertEquals(value, list.get(1));
	}
	
	//

	@Test
	public void shouldNewSectionBeEmpty() {
		// given
		Section section = new Section("sectionName");
		
		// when
		int size = section.size();
		
		// then
		assertEquals(0, size);
	}
	
	@Test
	public void shouldReturn1WhenAddOneValue() {
		// given
		Section section = new Section("sectionName");
		section.addKey("key", "value");
		
		// when
		int size = section.size();
		
		// then
		assertEquals(1, size);
	}
	
	@Test
	public void shouldReturn3WhenAddThreeValues() {
		// given
		Section section = new Section("sectionName");
		section.addKey("key1", "value1");
		section.addKey("key2", "value2");
		section.addKey("key3", "value3");
		
		// when
		int size = section.size();
		
		// then
		assertEquals(3, size);
	}
}
