package model.pgl.virtual;

import static org.junit.Assert.*;

import org.junit.Test;

public class VirtualPGLEqualsTest {

	private VirtualPGL prepareExample() {
		VirtualPGL example = new VirtualPGL();

		INISection sectionA = example.newSection("A");
		sectionA.addKey("a", "1");
		sectionA.addKey("b", "2");
		sectionA.addKey("c", "3");

		INISection sectionB = example.newSection("B");
		sectionB.addKey("d", "4");
		sectionB.addKey("e", "5");
		sectionB.addKey("f", "6");

		INISection sectionC = example.newSection("C");
		sectionC.addKey("g", "7");
		sectionC.addKey("h", "8");
		sectionC.addKey("i", "9");
		
		return example;
	}
	
	@Test
	public void notEqualsNull() {
		VirtualPGL pgl = prepareExample();
		
		assertFalse(pgl.equals(null));
	}
	
	@Test
	public void theSame() {
		VirtualPGL pgl1 = prepareExample();
		
		assertEquals(pgl1, pgl1);
	}
	
	@Test
	public void twoEqual() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		assertEquals(pgl1, pgl2);
	}
	
	@Test
	public void oneAdditionalSection() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		pgl2.newSection("");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void oneDiffValue() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		pgl2.get("A").get().addKey("a", "0");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void oneAdditionalKey() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		pgl2.get("B").get().addKey("a", "0");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void twoValueChanges_noDiff() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		pgl2.get("A").get().addKey("a", "0");
		pgl2.get("A").get().addKey("a", "1");
		
		assertEquals(pgl1, pgl2);
		assertEquals(pgl2, pgl1);
	}
	
	@Test
	public void oneAdditionalSectionWithExistingName() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		pgl2.newSection("C");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void additionalSectionCopy() {
		VirtualPGL pgl1 = prepareExample();
		VirtualPGL pgl2 = prepareExample();
		
		INISection sectionC = pgl2.newSection("C");
		sectionC.addKey("g", "7");
		sectionC.addKey("h", "8");
		sectionC.addKey("i", "9");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
}
