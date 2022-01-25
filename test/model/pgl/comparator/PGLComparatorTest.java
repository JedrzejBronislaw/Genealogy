package model.pgl.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.pgl.Section;
import model.pgl.PGL;
import model.pgl.comparator.PGLDiffContainer.AdditionalKey;
import model.pgl.comparator.PGLDiffContainer.AdditionalSection;
import model.pgl.comparator.PGLDiffContainer.OtherValue;

public class PGLComparatorTest {

	private PGL preparePGLExample(
			String aVal, String bVal, String cVal,
			String dVal, String eVal, String fVal,
			String gVal, String hVal, String iVal) {
		PGL example = new PGL();

		Section sectionA = example.newSection("A");
		sectionA.addKey("a", aVal);
		sectionA.addKey("b", bVal);
		sectionA.addKey("c", cVal);

		Section sectionB = example.newSection("B");
		sectionB.addKey("d", dVal);
		sectionB.addKey("e", eVal);
		sectionB.addKey("f", fVal);

		Section sectionC = example.newSection("C");
		sectionC.addKey("g", gVal);
		sectionC.addKey("h", hVal);
		sectionC.addKey("i", iVal);
		
		return example;
	}

	private PGL preparePGLExample() {
		return preparePGLExample(
				"1", "2", "3",
				"4", "5", "6",
				"7", "8", "9");
	}
	
	private PGLDiffContainer prepareDiffExample() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLComparator diff = new PGLComparator(pgl1, pgl2);
		return diff.compare();
	}

	private void chceckSizes(PGLDiffContainer diff, int allDifferences, int otherValues, int additionalKeys, int additionalSections) {
		assertEquals(allDifferences,     diff.size());
		assertEquals(otherValues,        diff.getOtherValues().size());
		assertEquals(additionalKeys,     diff.getAdditionalKeys().size());
		assertEquals(additionalSections, diff.getAdditionalSections().size());
	}
	
//-----Immutability

	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableList_OtherValues() {
		prepareDiffExample().getOtherValues().add(new OtherValue("", ""));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableList_AdditionalKeys() {
		prepareDiffExample().getAdditionalKeys().add(new AdditionalKey(new PGL(), "", ""));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableList_AdditionalSections() {
		prepareDiffExample().getAdditionalSections().add(new AdditionalSection(new PGL(), ""));
	}
	
//-----null PGL

	@Test(expected = NullPointerException.class)
	public void firstNull() {
		PGLComparator diff = new PGLComparator(null, preparePGLExample());
		diff.compare();
	}
	
	@Test(expected = NullPointerException.class)
	public void secondNull() {
		PGLComparator diff = new PGLComparator(preparePGLExample(), null);
		diff.compare();
	}
	
//-----the same PGL

	@Test
	public void theSame() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLComparator diff = new PGLComparator(pgl1, pgl2);

		chceckSizes(diff.compare(), 0, 0, 0, 0);
	}

//-----OtherValue

	private PGLDiffContainer changeValueOfKeyAinSectionAinSecondPGL(PGL pgl1, PGL pgl2) {
		pgl2.getSection("A").get().addKey("a", "0");
		
		return new PGLComparator(pgl1, pgl2).compare();
	}
	
	@Test
	public void otherValue() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = changeValueOfKeyAinSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 1, 0, 0);
		assertEquals(new OtherValue("A", "a"), diff.getOtherValues().get(0));
	}
	
	@Test
	public void otherValue_otherPGL() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = changeValueOfKeyAinSectionAinSecondPGL(pgl2, pgl1);

		chceckSizes(diff, 1, 1, 0, 0);
		assertEquals(new OtherValue("A", "a"), diff.getOtherValues().get(0));
	}
	
	@Test
	public void otherValue_wrongSection() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = changeValueOfKeyAinSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 1, 0, 0);
		assertNotEquals(new OtherValue("Z", "a"), diff.getOtherValues().get(0));
	}
	
	@Test
	public void otherValue_wrongKey() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = changeValueOfKeyAinSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 1, 0, 0);
		assertNotEquals(new OtherValue("A", "z"), diff.getOtherValues().get(0));
	}

//-----AdditionalKey

	private PGLDiffContainer addKeyZtoSectionAinSecondPGL(PGL pgl1, PGL pgl2) {
		pgl2.getSection("A").get().addKey("z", "0");
		
		return new PGLComparator(pgl1, pgl2).compare();
	}
	
	@Test
	public void additionalKey() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertEquals(new AdditionalKey(pgl2, "A", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_otherPGL() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addKeyZtoSectionAinSecondPGL(pgl2, pgl1);

		chceckSizes(diff, 1, 0, 1, 0);
		assertEquals(new AdditionalKey(pgl1, "A", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_wrongPGL() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertNotEquals(new AdditionalKey(pgl1, "A", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_wrongSection() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertNotEquals(new AdditionalKey(pgl2, "Z", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_wrongKey() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertNotEquals(new AdditionalKey(pgl2, "A", "x"), diff.getAdditionalKeys().get(0));
	}

//-----AdditionalSection

	private PGLDiffContainer addSectionDtoPGL2(PGL pgl1, PGL pgl2) {
		pgl2.newSection("D");

		return new PGLComparator(pgl1, pgl2).compare();
	}
	
	@Test
	public void additionalSection() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addSectionDtoPGL2(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 0, 1);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void additionalSection_otherPGL() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addSectionDtoPGL2(pgl2, pgl1);

		chceckSizes(diff, 1, 0, 0, 1);
		assertEquals(new AdditionalSection(pgl1, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void additionalSection_wrongPGL() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addSectionDtoPGL2(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 0, 1);
		assertNotEquals(new AdditionalSection(pgl1, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void additionalSection_wrongSection() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		PGLDiffContainer diff = addSectionDtoPGL2(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 0, 1);
		assertNotEquals(new AdditionalSection(pgl2, "E"), diff.getAdditionalSections().get(0));
	}

//-----All 3 kinds of diff
	
	@Test
	public void all3Diff() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		pgl2.newSection("D");
		pgl2.getSection("A").get().addKey("z", "0");
		pgl2.getSection("A").get().addKey("a", "0");

		PGLDiffContainer diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 1, 1, 1);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
		assertEquals(new AdditionalKey(pgl2, "A", "z"), diff.getAdditionalKeys().get(0));
		assertEquals(new OtherValue("A", "a"), diff.getOtherValues().get(0));
	}

//-----other
	
	@Test
	public void additionalKeyInAdditionalSection() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		pgl2.newSection("D");
		pgl2.getSection("D").get().addKey("d", "0");

		PGLDiffContainer diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 1, 0, 0, 1);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void threeAdditionalSection() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();

		pgl2.newSection("D");
		pgl2.newSection("E");
		pgl2.newSection("F");

		PGLDiffContainer diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 0, 0, 3);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
		assertEquals(new AdditionalSection(pgl2, "E"), diff.getAdditionalSections().get(1));
		assertEquals(new AdditionalSection(pgl2, "F"), diff.getAdditionalSections().get(2));
	}
	
	@Test
	public void threeAdditionalKeys() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 = preparePGLExample();
		
		pgl2.getSection("A").get().addKey("d", "0");
		pgl2.getSection("A").get().addKey("e", "0");
		pgl2.getSection("A").get().addKey("f", "0");

		PGLDiffContainer diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 0, 3, 0);
		assertTrue(diff.getAdditionalKeys().contains(new AdditionalKey(pgl2, "A", "d")));
		assertTrue(diff.getAdditionalKeys().contains(new AdditionalKey(pgl2, "A", "e")));
		assertTrue(diff.getAdditionalKeys().contains(new AdditionalKey(pgl2, "A", "f")));
	}
	
	@Test
	public void threeOtherValues() {
		PGL pgl1 = preparePGLExample();
		PGL pgl2 =  preparePGLExample(
				"1", "2", "3",
				"0", "0", "0",
				"7", "8", "9");

		PGLDiffContainer diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 3, 0, 0);
		assertTrue(diff.getOtherValues().contains(new OtherValue("B", "d")));
		assertTrue(diff.getOtherValues().contains(new OtherValue("B", "e")));
		assertTrue(diff.getOtherValues().contains(new OtherValue("B", "f")));
	}
}
