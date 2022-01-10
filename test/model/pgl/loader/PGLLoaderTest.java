package model.pgl.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import model.pgl.PGL;

public class PGLLoaderTest {
	
	
	private PGL saveAndLoadFile(String... content) {
		return new TempFile(String.join("\n", content)).loadPGL();
	}

	
	@Test
	public void shouldReturnOneSectionWithTwoValues() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "Jan";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldReturnOneSectionWithoutValues() {
		// given
		PGL pgl = saveAndLoadFile(
			"[1]"
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		
		// then
		assertEquals(1, pglSize);
		assertEquals(0, sectionSize);
	}
	
	@Test
	public void shouldReturnThreeSectionsWithoutValues() {
		// given
		PGL pgl = saveAndLoadFile(
			"[1]",
			"[2]",
			"[3]"
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int section1Size = pgl.getSections().get(0).size();
		int section2Size = pgl.getSections().get(1).size();
		int section3Size = pgl.getSections().get(2).size();
		
		// then
		assertEquals(3, pglSize);
		assertEquals(0, section1Size);
		assertEquals(0, section2Size);
		assertEquals(0, section3Size);
	}
	
	@Test
	public void shouldReturnSectionWithTextName() {
		// given
		String sectionName = "one";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]"
			);
		
		// when
		int pglSize = pgl.getSections().size();
		String returnedSectionName = pgl.getSections().get(0).getName();
		
		// then
		assertEquals(1, pglSize);
		assertEquals(sectionName, returnedSectionName);
	}
	
	@Test
	public void shouldNotLoadSectionWhenSectionHeaderIsNotClosed() {
		// given
		String sectionName = "1";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName
			);
		
		// when
		int pglSize = pgl.getSections().size();
		boolean sectionExists = pgl.getSection(sectionName).isPresent();
		
		// then
		assertEquals(0, pglSize);
		assertFalse(sectionExists);
	}
	
	@Test
	public void shouldNotLoadSectionWhenSectionHeaderIsNotOpened() {
		// given
		String sectionName = "1";
		
		PGL pgl = saveAndLoadFile(
			sectionName + "]"
			);
		
		// when
		int pglSize = pgl.getSections().size();
		boolean sectionExists = pgl.getSection(sectionName).isPresent();
		
		// then
		assertEquals(0, pglSize);
		assertFalse(sectionExists);
	}
	
	@Test
	public void shouldLoadNothingWhenSectionHeaderHaveNoBrackets() {
		// given
		String sectionName = "1";
		
		PGL pgl = saveAndLoadFile(
			sectionName
			);
		
		// when
		int pglSize = pgl.getSections().size();
		boolean sectionExists = pgl.getSection(sectionName).isPresent();
		
		// then
		assertEquals(0, pglSize);
		assertFalse(sectionExists);
	}

	
	@Test
	public void shouldReturnTwoPartOfSectionWhenSectionNameUsedTwice() {
		// given
		PGL pgl = saveAndLoadFile(
			"[1]",
			"[1]"
			);
		
		// when
		int pglSize = pgl.getSections().size();
		
		// then
		assertEquals(2, pglSize);
	}
	
	@Test
	public void shouldGetValuesFromAllPartsOfSection() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "Jan";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			"[" + sectionName + "]",
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(2, pglSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldReturnMultiValueKeyWhenSectionNameUsedTwice() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "Jan";
		String cityKey = "city";
		String cityValue1 = "Poznan1";
		String cityValue2 = "Poznan2";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			cityKey + "=" + cityValue1,
			"[" + sectionName + "]",
			cityKey + "=" + cityValue2
			);
		
		// when
		int pglSize = pgl.getSections().size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		List<String> returnedCities = pgl.getValues(sectionName, cityKey);
		
		// then
		assertEquals(2, pglSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue2, returnedCity);
		assertEquals(2, returnedCities.size());
		assertEquals(cityValue1, returnedCities.get(0));
		assertEquals(cityValue2, returnedCities.get(1));
	}
	
	@Test
	public void shouldReturnTwoSectionsWithTheSameName() {
		// given
		String section1Name = "1";
		String section2Name = "2";
		
		PGL pgl = saveAndLoadFile(
			"[" + section1Name + "]",
			"name=Jan",
			"city=Poznan1",
			"[" + section2Name + "]",
			"city=Poznan2",
			"[" + section1Name + "]",
			"city=Poznan2"
			);
		
		// when
		int numOfSections1 = pgl.getSections(section1Name).size();
		int numOfSections2 = pgl.getSections(section2Name).size();
		
		// then
		assertEquals(2, numOfSections1);
		assertEquals(1, numOfSections2);
	}
	
	@Test
	public void shouldReturnMultiValueKeyWhenSectionNameUsedMultiple() {
		// given
		String sectionName = "1";
		String cityKey = "city";
		String cityValue1 = "Poznan1";
		String cityValue2 = "Poznan2";
		String cityValue3 = "Poznan3";
		String cityValue4 = "Poznan4";
		String cityValue5 = "Poznan5";
		String cityValue6 = "Poznan6";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			"name=Jan",
			cityKey + "=" + cityValue1,
			cityKey + "=" + cityValue2,
			"[" + sectionName + "]",
			cityKey + "=" + cityValue3,
			"[" + sectionName + "]",
			cityKey + "=" + cityValue4,
			cityKey + "=" + cityValue1,
			cityKey + "=" + cityValue5,
			"[" + sectionName + "]",
			cityKey + "=" + cityValue6
			);
		
		// when
		String returnedCity = pgl.getValue(sectionName, cityKey);
		List<String> returnedCities = pgl.getValues(sectionName, cityKey);
		
		// then
		assertEquals(cityValue6, returnedCity);
		assertEquals(7, returnedCities.size());
		assertEquals(cityValue1, returnedCities.get(0));
		assertEquals(cityValue2, returnedCities.get(1));
		assertEquals(cityValue3, returnedCities.get(2));
		assertEquals(cityValue4, returnedCities.get(3));
		assertEquals(cityValue1, returnedCities.get(4));
		assertEquals(cityValue5, returnedCities.get(5));
		assertEquals(cityValue6, returnedCities.get(6));
	}
	
	
	@Test
	public void shouldReturnEmptyValue() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(2, sectionSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldReturnValueWithEqualSign() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "1=1";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(2, sectionSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldReturnValueWhenValueIsEqualSign() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "=";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(2, sectionSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldReturnValueWhenKeyIsEmpty() {
		// given
		String sectionName = "1";
		String nameKey = "";
		String nameValue = "Jan";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(2, sectionSize);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldIgnoreValueWhenThereIsNoEqualSignInTheLine() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "Jan";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + nameValue,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(1, sectionSize);
		assertNull(returnedName);
		assertEquals(cityValue, returnedCity);
	}
	
	@Test
	public void shouldReturnAllValuesWhenKeyRepeats() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue1 = "Jan1";
		String nameValue2 = "Jan2";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue1,
			nameKey + "=" + nameValue2,
			cityKey + "=" + cityValue
			);
		
		// when
		int pglSize = pgl.getSections().size();
		int sectionSize = pgl.getSections().get(0).size();
		String returnedName = pgl.getValue(sectionName, nameKey);
		List<String> returnedNames = pgl.getSection(sectionName).get().getValues(nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, pglSize);
		assertEquals(3, sectionSize);
		assertEquals(nameValue2, returnedName);
		assertEquals(2, returnedNames.size());
		assertEquals(nameValue1, returnedNames.get(0));
		assertEquals(nameValue2, returnedNames.get(1));
		assertEquals(cityValue, returnedCity);
	}
	
	

	
	@Test
	public void shouldReturn0UniqueSectionsWhenPGLisEmpty() {
		// given
		PGL pgl = saveAndLoadFile("");
		
		// when
		long numOfUniqueSections = pgl.numOfUniqueSections();
		
		// then
		assertEquals(0, numOfUniqueSections);
	}
	
	@Test
	public void shouldReturn1UniqueSectionWhenSectionNameUsedTwice() {
		// given
		PGL pgl = saveAndLoadFile(
			"[1]",
			"[1]"
			);
		
		// when
		long numOfUniqueSections = pgl.numOfUniqueSections();
		
		// then
		assertEquals(1, numOfUniqueSections);
	}
	
	@Test
	public void shouldReturn3UniqueSectionWhen9PartOfSectionsUse3Names() {
		// given
		PGL pgl = saveAndLoadFile(
			"[1]",
			"[2]",
			"[3]",
			"[2]",
			"[1]",
			"[2]",
			"[2]",
			"[3]",
			"[1]"
			);
		
		// when
		long numOfUniqueSections = pgl.numOfUniqueSections();
		
		// then
		assertEquals(3, numOfUniqueSections);
	}
	
	@Test
	public void shouldReturn1UniqueSectionWhenPartsOfSectionsHaveOtherValues() {
		// given
		String sectionName = "1";
		String nameKey = "name";
		String nameValue = "Jan";
		String cityKey = "city";
		String cityValue = "Poznan";
		
		PGL pgl = saveAndLoadFile(
			"[" + sectionName + "]",
			nameKey + "=" + nameValue,
			"[" + sectionName + "]",
			cityKey + "=" + cityValue
			);
		
		// when
		long numOfUniqueSections = pgl.numOfUniqueSections();
		String returnedName = pgl.getValue(sectionName, nameKey);
		String returnedCity = pgl.getValue(sectionName, cityKey);
		
		// then
		assertEquals(1, numOfUniqueSections);
		assertEquals(nameValue, returnedName);
		assertEquals(cityValue, returnedCity);
	}
}
