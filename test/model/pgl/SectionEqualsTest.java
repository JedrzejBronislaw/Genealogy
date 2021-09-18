package model.pgl;

import static org.junit.Assert.*;

import org.junit.Test;

import model.pgl.Section;

public class SectionEqualsTest {

	private Section prepareExampleSection() {
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
	public void nullSections() {
		Section section = new Section("A");
		
		assertFalse(section.equals(null));
	}

	@Test
	public void theSameSections() {
		Section section = new Section("A");
		
		assertEquals(section, section);
	}
	
	@Test
	public void twoEmptySections() {
		Section section1 = new Section("A");
		Section section2 = new Section("A");
		
		assertEquals(section1, section2);
	}
	
	@Test
	public void twoEmptySections_diffName() {
		Section section1 = new Section("A");
		Section section2 = new Section("B");
		
		assertNotEquals(section1, section2);
	}
	
	@Test
	public void twoEqualSections() {
		Section section1 = prepareExampleSection();
		Section section2 = prepareExampleSection();
		
		assertEquals(section1, section2);
	}
	
	@Test
	public void oneDiffValue() {
		Section section1 = prepareExampleSection();
		Section section2 = prepareExampleSection();
		
		section2.addKey("a", "0");
		
		assertNotEquals(section1, section2);
	}
	
	@Test
	public void oneAdditionalKey() {
		Section section1 = prepareExampleSection();
		Section section2 = prepareExampleSection();
		
		section2.addKey("h", "8");
		
		assertNotEquals(section1, section2);
		assertNotEquals(section2, section1);
	}
	
	@Test
	public void twoValueChanges_noDiff() {
		Section section1 = prepareExampleSection();
		Section section2 = prepareExampleSection();
		
		section2.addKey("a", "0");
		section2.addKey("a", "1");
		
		assertEquals(section1, section2);
	}
}
