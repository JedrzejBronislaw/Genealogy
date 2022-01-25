package model.pgl;

import static org.junit.Assert.*;

import org.junit.Test;

public class PGLEqualsTest {

	private PGL createExamplePGL() {
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
	public void shouldReturnFalseWhenCompareWithNull() {
		// given
		PGL pgl = createExamplePGL();
		
		// when
		// then
		assertFalse(pgl.equals(null));
	}
	
	@Test
	public void shouldReturnTrueWhenCompareWithItself() {
		// given
		PGL pgl = createExamplePGL();
		
		// when
		// then
		assertEquals(pgl, pgl);
	}
	
	@Test
	public void shouldReturnTrueWhenCompareWithTheSame() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		
		// when
		// then
		assertEquals(pgl1, pgl2);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithPGLWithOneAdditionalSection() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		pgl2.newSection("");
		
		// when
		boolean isEqual = pgl1.equals(pgl2);
		
		// then
		assertFalse(isEqual);
	}

	
	@Test
	public void shouldReturnFalseWhenCompareWithPGLWithOneSectionLess() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		pgl2.newSection("");
		
		// when
		boolean isEqual = pgl2.equals(pgl1);
		
		// then
		assertFalse(isEqual);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithPLGWithOneDifferentValue() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		
		pgl2.getSection("A").get().addKey("a", "0");
		
		// when
		// then
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithPGLWithOneAdditionalKey() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		
		pgl2.getSection("B").get().addKey("a", "0");
		
		// when
		// then
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithPGLAfterAttemptOfChangeAndRechange() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		
		pgl2.getSection("A").get().addKey("a", "0");
		pgl2.getSection("A").get().addKey("a", "1");
		
		// when
		// then
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithPGLWithOneAdditionalSectionWithExistingName() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		
		pgl2.newSection("C");
		
		// when
		// then
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
	
	@Test
	public void shouldReturnFalseWhenCompareWithPGLWithAdditionalSectionCopy() {
		// given
		PGL pgl1 = createExamplePGL();
		PGL pgl2 = createExamplePGL();
		
		Section sectionC = pgl2.newSection("C");
		sectionC.addKey("g", "7");
		sectionC.addKey("h", "8");
		sectionC.addKey("i", "9");
		
		// when
		// then
		assertNotEquals(pgl1, pgl2);
		assertNotEquals(pgl2, pgl1);
	}
}
