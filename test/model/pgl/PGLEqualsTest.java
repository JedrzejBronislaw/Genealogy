package model.pgl;

import static org.junit.Assert.*;

import org.junit.Test;

import model.pgl.Section;
import model.pgl.PGL;

public class PGLEqualsTest {

	private PGL prepareExample() {
		PGL example = new PGL();

		Section sectionA = example.newSection("A");
		sectionA.addKey("a", "1");
		sectionA.addKey("b", "2");
		sectionA.addKey("c", "3");

		Section sectionB = example.newSection("B");
		sectionB.addKey("d", "4");
		sectionB.addKey("e", "5");
		sectionB.addKey("f", "6");

		Section sectionC = example.newSection("C");
		sectionC.addKey("g", "7");
		sectionC.addKey("h", "8");
		sectionC.addKey("i", "9");
		
		return example;
	}
	
	@Test
	public void notEqualsNull() {
		PGL pgl = prepareExample();
		
		assertFalse(pgl.equals(null));
	}
	
	@Test
	public void theSame() {
		PGL pgl1 = prepareExample();
		
		assertEquals(pgl1, pgl1);
	}
	
	@Test
	public void twoEqual() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		assertEquals(pgl1, pgl2);
	}
	
	@Test
	public void oneAdditionalSection() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		pgl2.newSection("");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void oneDiffValue() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		pgl2.get("A").get().addKey("a", "0");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void oneAdditionalKey() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		pgl2.get("B").get().addKey("a", "0");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void twoValueChanges_noDiff() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		pgl2.get("A").get().addKey("a", "0");
		pgl2.get("A").get().addKey("a", "1");
		
		assertEquals(pgl1, pgl2);
		assertEquals(pgl2, pgl1);
	}
	
	@Test
	public void oneAdditionalSectionWithExistingName() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		pgl2.newSection("C");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void additionalSectionCopy() {
		PGL pgl1 = prepareExample();
		PGL pgl2 = prepareExample();
		
		Section sectionC = pgl2.newSection("C");
		sectionC.addKey("g", "7");
		sectionC.addKey("h", "8");
		sectionC.addKey("i", "9");
		
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
}
