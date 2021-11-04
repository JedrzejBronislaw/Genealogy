package model.pgl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import model.pgl.Section;

public class SectionEqualsTest {

	private Section createExampleSection() {
		Section section = new Section("section");
		
		section.addKey("a", "1");
		section.addKey("b", "2");
		section.addKey("c", "3");
		section.addKey("d", "4");
		section.addKey("e", "5");
		section.addKey("f", "6");
		section.addKey("g", "7");
		
		return section;
	}

	@Test
	public void shouldReturnFalseWhenCompareWithnullSections() {
		// given
		Section section = new Section("A");
		
		// when
		boolean isEqual = section.equals(null);
		
		// then
		assertFalse(isEqual);
	}

	@Test
	public void shouldReturnTrueWhenCompareWithItself() {
		// given
		Section section = new Section("A");
		
		// when
		boolean isEqual = section.equals(section);
		
		// then
		assertTrue(isEqual);
	}
	
	@Test
	public void shouldReturnTrueWhenCompareTwoEmptySectionsWithTheSameName() {
		// given
		Section section1 = new Section("A");
		Section section2 = new Section("A");
		
		// when
		boolean isEqual = section1.equals(section2);

		// then
		assertTrue(isEqual);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareTwoEmptySectionsWithDifferentNames() {
		// given
		Section section1 = new Section("A");
		Section section2 = new Section("B");

		// when
		boolean isEqual = section1.equals(section2);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldReturnTrueWhenCompareTwoSectionsWithTheSameValues() {
		// given
		Section section1 = createExampleSection();
		Section section2 = createExampleSection();

		// when
		boolean isEqual = section1.equals(section2);
		
		// then
		assertTrue(isEqual);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareTwoSectionsWithDifferentValues() {
		// given
		Section section1 = createExampleSection();
		Section section2 = createExampleSection();
		section2.addKey("a", "0");

		// when
		boolean isEqual = section1.equals(section2);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithSectionWithOneAdditionalKey() {
		// given
		Section section1 = createExampleSection();
		Section section2 = createExampleSection();
		section2.addKey("h", "8");
		
		// when
		boolean isEqual = section1.equals(section2);

		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithSectionWithOneKeyLess() {
		// given
		Section section1 = createExampleSection();
		Section section2 = createExampleSection();
		section2.addKey("h", "8");
		
		// when
		boolean isEqual = section2.equals(section1);

		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldReturnFalseAfterAttemptOfChangAndRechange() {
		// given
		Section section1 = createExampleSection();
		Section section2 = createExampleSection();
		section2.addKey("a", "0");
		section2.addKey("a", "1");
		
		// when
		boolean isEqual = section1.equals(section2);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldReturn3ValuesAfterAttemptOfChangAndRechange() {
		// given
		Section section2 = createExampleSection();
		String key = "a";
		section2.addKey(key, "0");
		section2.addKey(key, "1");
		
		// when
		List<String> valuesWithOneKey = section2.getValues(key);
		
		// then
		assertEquals(3, valuesWithOneKey.size());
	}
}
