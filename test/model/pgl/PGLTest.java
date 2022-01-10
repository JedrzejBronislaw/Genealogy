package model.pgl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import org.junit.Test;

public class PGLTest {

	@Test
	public void shouldSizeBe0WhenPGLisNew() {
		// given
		PGL pgl;
		
		// when
		pgl = new PGL();
		
		// then
		assertEquals(0, pgl.size());
	}

	@Test
	public void shouldCreateNewSection() {
		// given
		PGL pgl = new PGL();
		
		// when
		pgl.newSection("sectionName");
		
		// then
		assertEquals(1, pgl.size());
	}

	@Test
	public void shouldCreateThreeNewSections() {
		// given
		PGL pgl = new PGL();
		
		// when
		pgl.newSection("section1Name");
		pgl.newSection("section2Name");
		pgl.newSection("section3Name");
		
		// then
		assertEquals(3, pgl.size());
	}

	@Test
	public void shouldCreateTwoSectionWithTheSameName() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		pgl.newSection(sectionName);
		
		// when
		pgl.newSection(sectionName);
		
		// then
		assertEquals(2, pgl.size());
	}
	
	@Test
	public void shouldNamedNewSection() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		
		// when
		pgl.newSection(sectionName);
		
		// then
		assertEquals(sectionName, pgl.getSections().get(0).getName());
	}
	
	@Test
	public void shoudlReturnSection() {
		// given
		PGL pgl = new PGL();
		String section1Name = "section1Name";
		String section2Name = "section2Name";
		String section3Name = "section3Name";
		pgl.newSection(section1Name);
		pgl.newSection(section2Name);
		Section section3 = pgl.newSection(section3Name);
		
		// when
		Section returnedSection = pgl.getSection(section3Name).get();
		
		// then
		assertSame(section3, returnedSection);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void shoudlRetunNullWhenGettingSectionWithWrongName() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("sectionName");
		
		// when
		// then
		 pgl.getSection("otherName").get();
	}
	
	@Test
	public void shoudlNotRetunSectionWhenGettingSectionWithWrongName() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("sectionName");
		
		// when
		boolean sectionExists = pgl.getSection("otherName").isPresent();
		
		// then
		assertFalse(sectionExists);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shoudlThrowExceptionWhenGettingNullSection() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("sectionName");
		
		// when
		// then
		pgl.getSection(null);
	}
	
	@Test
	public void shouldAddValue() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		String key = "key";
		String value = "value";
		
		// when
		pgl.newSection(sectionName).addKey(key, value);
		
		// then
		assertEquals(1, pgl.getSection(sectionName).get().getKeys().size());
	}
	
	@Test
	public void shouldAddThreeValues() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		Section section = pgl.newSection(sectionName);
		
		// when
		section.addKey("key1", "value");
		section.addKey("key2", "value");
		section.addKey("key3", "value");
		
		// then
		assertEquals(3, pgl.getSection(sectionName).get().getKeys().size());
	}
	
//	@Test
//	public void shouldAddThreeKeysWithTheSameName() {
//		// given
//		PGL pgl = new PGL();
//		String sectionName = "sectionName";
//		String key = "key1";
//		Section section = pgl.newSection(sectionName);
//		
//		// when
//		section.addKey(key, "value1");
//		section.addKey(key, "value2");
//		section.addKey(key, "value3");
//		
//		// then
//		assertEquals(3, pgl.getSection(sectionName).get().getKeys().size());
//	}
	
	@Test
	public void shouldNewSectionBeWithoutValues() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		
		// when
		pgl.newSection(sectionName);
		
		// then
		assertEquals(0, pgl.getSection(sectionName).get().getKeys().size());
	}
	
	@Test
	public void shouldReturnValue() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		String key = "key";
		String value = "value";
		
		pgl.newSection(sectionName).addKey(key, value);
		
		// when
		String returnedValue = pgl.getValue(sectionName, key);
		
		// then
		assertEquals(value, returnedValue);
	}
	
	@Test
	public void shouldReturnNullValueWhenGettingNotExistingKey() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		String key = "key";
		String value = "value";
		
		pgl.newSection(sectionName).addKey(key, value);
		
		// when
		String returnedValue = pgl.getValue(sectionName, "otherKey");
		
		// then
		assertNull(returnedValue);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWhenGettingNullKey() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		String key = "key";
		String value = "value";
		
		pgl.newSection(sectionName).addKey(key, value);
		
		// when
		// then
		pgl.getValue(sectionName, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWhenGettingKeyFromNullSection() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		String key = "key";
		String value = "value";
		
		pgl.newSection(sectionName).addKey(key, value);
		
		// when
		// then
		pgl.getValue(null, key);
	}
	
	@Test
	public void shouldReturnNullWhenGettingNotExistingKey() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		
		pgl.newSection(sectionName).addKey("key", "value");
		
		// when
		String returnedValue = pgl.getValue(sectionName, "otherKey");
		
		// then
		assertNull(returnedValue);
	}
	
	@Test
	public void shouldExecuteTestForEachSection() {
		// given
		PGL pgl = new PGL();
		List<String> nameList = new ArrayList<String>();
		String section1Name = "section1Name";
		String section2Name = "section2Name";
		String section3Name = "section3Name";

		pgl.newSection(section1Name);
		pgl.newSection(section2Name);
		pgl.newSection(section3Name);
		
		Consumer<Section> task = section -> nameList.add(section.getName());
		
		// when
		pgl.forEachSection(task);
		
		// then
		assertEquals(3, nameList.size());
		assertTrue(nameList.contains(section1Name));
		assertTrue(nameList.contains(section2Name));
		assertTrue(nameList.contains(section3Name));
	}
	
	@Test(expected = Exception.class)
	public void shouldNotAllowToClearByGetSection() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("section1Name");
		pgl.newSection("section2Name");
		pgl.newSection("section3Name");
		
		// when
		// then
		pgl.getSections().clear();
	}
	
	@Test(expected = Exception.class)
	public void shouldNotAllowToAddByGetSection() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("section1Name");
		pgl.newSection("section2Name");
		pgl.newSection("section3Name");
		
		// when
		// then
		pgl.getSections().add(new Section("section3Name"));
	}
	
//	@Test(expected = Exception.class)
//	public void shouldNotAllowToModifyByGetSection() {
//		// given
//		PGL pgl = new PGL();
//		pgl.newSection("section1Name");
//		pgl.newSection("section2Name");
//		pgl.newSection("section3Name");
//		
//		// when
//		// then
//		pgl.getSections().get(0).addKey("key", "value");
//	}


	@Test
	public void shouldReturn0UniqueSectionWhenPGLisNew() {
		// given
		PGL pgl = new PGL();
		
		// when
		long numOfUniqueSection = pgl.numOfUniqueSections();
		
		// then
		assertEquals(0, numOfUniqueSection);
	}

	@Test
	public void shouldReturn1UniqueSectionWhenPGLhasOneSection() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("section1Name");
		
		// when
		long numOfUniqueSection = pgl.numOfUniqueSections();
		
		// then
		assertEquals(1, numOfUniqueSection);
	}

	@Test
	public void shouldReturn3UniqueSectionWhenPGLhas3SectionInOnePart() {
		// given
		PGL pgl = new PGL();
		pgl.newSection("section1Name");
		pgl.newSection("section2Name");
		pgl.newSection("section3Name");
		
		// when
		long numOfUniqueSection = pgl.numOfUniqueSections();
		
		// then
		assertEquals(3, numOfUniqueSection);
	}

	@Test
	public void shouldReturn1UniqueSectionWhenPGLhas1SectionIn3Part() {
		// given
		PGL pgl = new PGL();
		String sectionName = "sectionName";
		pgl.newSection(sectionName);
		pgl.newSection(sectionName);
		pgl.newSection(sectionName);
		
		// when
		long numOfUniqueSection = pgl.numOfUniqueSections();
		long numOfSectionParts = pgl.getSections().size();
		
		// then
		assertEquals(1, numOfUniqueSection);
		assertEquals(3, numOfSectionParts);
	}

	@Test
	public void shouldReturn3UniqueSectionWhenPGLhas3SectionInManyParts() {
		// given
		PGL pgl = new PGL();
		String section1Name = "section1Name";
		String section2Name = "section2Name";
		String section3Name = "section3Name";
		pgl.newSection(section1Name);
		pgl.newSection(section1Name);
		pgl.newSection(section2Name);
		pgl.newSection(section3Name);
		pgl.newSection(section2Name);
		pgl.newSection(section1Name);
		pgl.newSection(section3Name);
		pgl.newSection(section3Name);
		
		// when
		long numOfUniqueSection = pgl.numOfUniqueSections();
		
		// then
		assertEquals(3, numOfUniqueSection);
	}
}
