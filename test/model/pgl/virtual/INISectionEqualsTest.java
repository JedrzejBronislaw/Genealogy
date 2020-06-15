package model.pgl.virtual;

import static org.junit.Assert.*;

import org.junit.Test;

public class INISectionEqualsTest {

	private INISection prepareExampleSection() {
		INISection section = new INISection("section");
		
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
		INISection section = new INISection("A");
		
		assertFalse(section.equals(null));
	}

	@Test
	public void theSameSections() {
		INISection section = new INISection("A");
		
		assertEquals(section, section);
	}
	
	@Test
	public void twoEmptySections() {
		INISection section1 = new INISection("A");
		INISection section2 = new INISection("A");
		
		assertEquals(section1, section2);
	}
	
	@Test
	public void twoEmptySections_diffName() {
		INISection section1 = new INISection("A");
		INISection section2 = new INISection("B");
		
		assertNotEquals(section1, section2);
	}
	
	@Test
	public void twoEqualSections() {
		INISection section1 = prepareExampleSection();
		INISection section2 = prepareExampleSection();
		
		assertEquals(section1, section2);
	}
	
	@Test
	public void oneDiffValue() {
		INISection section1 = prepareExampleSection();
		INISection section2 = prepareExampleSection();
		
		section2.addKey("a", "0");
		
		assertNotEquals(section1, section2);
	}
	
	@Test
	public void oneAdditionalKey() {
		INISection section1 = prepareExampleSection();
		INISection section2 = prepareExampleSection();
		
		section2.addKey("h", "8");
		
		assertNotEquals(section1, section2);
		assertNotEquals(section2, section1);
	}
	
	@Test
	public void twoValueChanges_noDiff() {
		INISection section1 = prepareExampleSection();
		INISection section2 = prepareExampleSection();
		
		section2.addKey("a", "0");
		section2.addKey("a", "1");
		
		assertEquals(section1, section2);
	}
}
