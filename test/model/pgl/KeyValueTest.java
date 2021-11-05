package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KeyValueTest {

	@Test
	public void shouldCreateObjectWithKeyAndValue() {
		// given
		String key = "key";
		String value = "value";
		
		// when
		KeyValue keyValue = new KeyValue(key, value);
		
		// then
		assertEquals(key, keyValue.getKey());
		assertEquals(value, keyValue.getValue());
	}
	
	@Test
	public void shouldNotCreateObjectWhenKeyIsEmpty() {
		// given
		String key = "";
		String value = "value";
		
		// when
		KeyValue keyValue = new KeyValue(key, value);
		
		// then
		assertEquals(key, keyValue.getKey());
		assertEquals(value, keyValue.getValue());
	}
	
	@Test
	public void shouldNotCreateObjectWhenValueIsEmpty() {
		// given
		String key = "key";
		String value = "";
		
		// when
		KeyValue keyValue = new KeyValue(key, value);
		
		// then
		assertEquals(key, keyValue.getKey());
		assertEquals(value, keyValue.getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateObjectWhenKeyIsNull() {
		// given
		// when
		// then
		new KeyValue(null, "value");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateObjectWhenValueIsNull() {
		// given
		// when
		// then
		new KeyValue("key", null);
	}
	
	//
	
	@Test
	public void shouldRetrunFalseWhenCompareToNull() {
		// given
		KeyValue keyValue = new KeyValue("key", "value");
		
		// when
		boolean isEqual = keyValue.equals(null);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldRetrunTrueWhenCompareToItself() {
		// given
		KeyValue keyValue = new KeyValue("key", "value");
		
		// when
		boolean isEqual = keyValue.equals(keyValue);
		
		// then
		assertTrue(isEqual);
	}
	
	@Test
	public void shouldRetrunTrueWhenCompareToEqualObject() {
		// given
		KeyValue keyValue1 = new KeyValue("key", "value");
		KeyValue keyValue2 = new KeyValue("key", "value");
		
		// when
		boolean isEqual = keyValue1.equals(keyValue2);
		
		// then
		assertTrue(isEqual);
	}
	
	@Test
	public void shouldRetrunFalseWhenCompareToKVWithAnotherKey() {
		// given
		KeyValue keyValue1 = new KeyValue("key1", "value");
		KeyValue keyValue2 = new KeyValue("key2", "value");
		
		// when
		boolean isEqual = keyValue1.equals(keyValue2);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldRetrunFalseWhenCompareToKVWithAnotherValue() {
		// given
		KeyValue keyValue1 = new KeyValue("key", "value1");
		KeyValue keyValue2 = new KeyValue("key", "value2");
		
		// when
		boolean isEqual = keyValue1.equals(keyValue2);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldRetrunFalseWhenCompareToKVWithUpperKey() {
		// given
		KeyValue keyValue1 = new KeyValue("key", "value");
		KeyValue keyValue2 = new KeyValue("KEY", "value");
		
		// when
		boolean isEqual = keyValue1.equals(keyValue2);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldRetrunFalseWhenCompareToKVWithUpperValue() {
		// given
		KeyValue keyValue1 = new KeyValue("key", "value");
		KeyValue keyValue2 = new KeyValue("key", "VALUE");
		
		// when
		boolean isEqual = keyValue1.equals(keyValue2);
		
		// then
		assertFalse(isEqual);
	}
}
