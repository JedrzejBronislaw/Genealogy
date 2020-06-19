package model.pgl.virtual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import model.pgl.virtual.Differences.AdditionalKey;
import model.pgl.virtual.Differences.AdditionalSection;
import model.pgl.virtual.Differences.OtherValue;

public class VirtualPGLDiffTest {

	private VirtualPGL preparePGLExample() {
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
	
	private Differences prepareDiffExample() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		PGLComparator diff = new PGLComparator(pgl1, pgl2);
		return diff.compare();
	}

	private void chceckSizes(Differences diff, int allDifferences, int otherValues, int additionalKeys, int additionalSections) {
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
		prepareDiffExample().getAdditionalKeys().add(new AdditionalKey(new VirtualPGL(), "", ""));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableList_AdditionalSections() {
		prepareDiffExample().getAdditionalSections().add(new AdditionalSection(new VirtualPGL(), ""));
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
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		PGLComparator diff = new PGLComparator(pgl1, pgl2);

		chceckSizes(diff.compare(), 0, 0, 0, 0);
	}

//-----OtherValue

	private Differences changeValueOfKeyAinSectionAinSecondPGL(VirtualPGL pgl1, VirtualPGL pgl2) {
		pgl2.get("A").get().addKey("a", "0");
		
		return new PGLComparator(pgl1, pgl2).compare();
	}
	
	@Test
	public void otherValue() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = changeValueOfKeyAinSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 1, 0, 0);
		assertEquals(new OtherValue("A", "a"), diff.getOtherValues().get(0));
	}
	
	@Test
	public void otherValue_otherPGL() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = changeValueOfKeyAinSectionAinSecondPGL(pgl2, pgl1);

		chceckSizes(diff, 1, 1, 0, 0);
		assertEquals(new OtherValue("A", "a"), diff.getOtherValues().get(0));
	}
	
	@Test
	public void otherValue_wrongSection() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = changeValueOfKeyAinSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 1, 0, 0);
		assertNotEquals(new OtherValue("Z", "a"), diff.getOtherValues().get(0));
	}
	
	@Test
	public void otherValue_wrongKey() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = changeValueOfKeyAinSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 1, 0, 0);
		assertNotEquals(new OtherValue("A", "z"), diff.getOtherValues().get(0));
	}

//-----AdditionalKey

	private Differences addKeyZtoSectionAinSecondPGL(VirtualPGL pgl1, VirtualPGL pgl2) {
		pgl2.get("A").get().addKey("z", "0");
		
		return new PGLComparator(pgl1, pgl2).compare();
	}
	
	@Test
	public void additionalKey() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertEquals(new AdditionalKey(pgl2, "A", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_otherPGL() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addKeyZtoSectionAinSecondPGL(pgl2, pgl1);

		chceckSizes(diff, 1, 0, 1, 0);
		assertEquals(new AdditionalKey(pgl1, "A", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_wrongPGL() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertNotEquals(new AdditionalKey(pgl1, "A", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_wrongSection() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertNotEquals(new AdditionalKey(pgl2, "Z", "z"), diff.getAdditionalKeys().get(0));
	}
	
	@Test
	public void additionalKey_wrongKey() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addKeyZtoSectionAinSecondPGL(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 1, 0);
		assertNotEquals(new AdditionalKey(pgl2, "A", "x"), diff.getAdditionalKeys().get(0));
	}

//-----AdditionalSection

	private Differences addSectionDtoPGL2(VirtualPGL pgl1, VirtualPGL pgl2) {
		pgl2.newSection("D");

		return new PGLComparator(pgl1, pgl2).compare();
	}
	
	@Test
	public void additionalSection() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addSectionDtoPGL2(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 0, 1);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void additionalSection_otherPGL() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addSectionDtoPGL2(pgl2, pgl1);

		chceckSizes(diff, 1, 0, 0, 1);
		assertEquals(new AdditionalSection(pgl1, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void additionalSection_wrongPGL() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addSectionDtoPGL2(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 0, 1);
		assertNotEquals(new AdditionalSection(pgl1, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void additionalSection_wrongSection() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		Differences diff = addSectionDtoPGL2(pgl1, pgl2);

		chceckSizes(diff, 1, 0, 0, 1);
		assertNotEquals(new AdditionalSection(pgl2, "E"), diff.getAdditionalSections().get(0));
	}

//-----All 3 kinds of diff
	
	@Test
	public void all3Diff() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		pgl2.newSection("D");
		pgl2.get("A").get().addKey("z", "0");
		pgl2.get("A").get().addKey("a", "0");

		Differences diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 1, 1, 1);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
		assertEquals(new AdditionalKey(pgl2, "A", "z"), diff.getAdditionalKeys().get(0));
		assertEquals(new OtherValue("A", "a"), diff.getOtherValues().get(0));
	}

//-----other
	
	@Test
	public void additionalKeyInAdditionalSection() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		pgl2.newSection("D");
		pgl2.get("D").get().addKey("d", "0");

		Differences diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 1, 0, 0, 1);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
	}
	
	@Test
	public void threeAdditionalSection() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();

		pgl2.newSection("D");
		pgl2.newSection("E");
		pgl2.newSection("F");

		Differences diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 0, 0, 3);
		assertEquals(new AdditionalSection(pgl2, "D"), diff.getAdditionalSections().get(0));
		assertEquals(new AdditionalSection(pgl2, "E"), diff.getAdditionalSections().get(1));
		assertEquals(new AdditionalSection(pgl2, "F"), diff.getAdditionalSections().get(2));
	}
	
	@Test
	public void threeAdditionalKeys() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		pgl2.get("A").get().addKey("d", "0");
		pgl2.get("A").get().addKey("e", "0");
		pgl2.get("A").get().addKey("f", "0");

		Differences diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 0, 3, 0);
		assertEquals(new AdditionalKey(pgl2, "A", "d"), diff.getAdditionalKeys().get(0));
		assertEquals(new AdditionalKey(pgl2, "A", "e"), diff.getAdditionalKeys().get(1));
		assertEquals(new AdditionalKey(pgl2, "A", "f"), diff.getAdditionalKeys().get(2));
	}
	
	@Test
	public void threeOtherValues() {
		VirtualPGL pgl1 = preparePGLExample();
		VirtualPGL pgl2 = preparePGLExample();
		
		pgl2.get("B").get().addKey("d", "0");
		pgl2.get("B").get().addKey("e", "0");
		pgl2.get("B").get().addKey("f", "0");

		Differences diff = new PGLComparator(pgl1, pgl2).compare();

		chceckSizes(diff, 3, 3, 0, 0);
		assertEquals(new OtherValue("B", "d"), diff.getOtherValues().get(0));
		assertEquals(new OtherValue("B", "e"), diff.getOtherValues().get(1));
		assertEquals(new OtherValue("B", "f"), diff.getOtherValues().get(2));
	}
}
