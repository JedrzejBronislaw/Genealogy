package model.pgl.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Sex;
import model.pgl.PGL;
import model.pgl.PGLFields;
import model.pgl.Section;

public class PGLLoaderTest_SmallFamily {
	
	private static String content = String.join("\n",
			"[1]",
			PGLFields.firstName + "=Adam",
			PGLFields.lastName +  "=Kowalski",
			PGLFields.sex +       "=" + Sex.MAN,
			PGLFields.marriages + "=1",
			PGLFields.spouse(1) + "=2",
			PGLFields.children +  "=3",
			PGLFields.child(1) +  "=3",
			PGLFields.child(2) +  "=4",
			PGLFields.child(3) +  "=5",
			"[2]",
			PGLFields.firstName + "=Ewa",
			PGLFields.lastName +  "=Nowak",
			PGLFields.sex +       "=" + Sex.WOMAN,
			PGLFields.marriages + "=1",
			PGLFields.spouse(1) + "=1",
			PGLFields.children +  "=3",
			PGLFields.child(1) +  "=3",
			PGLFields.child(2) +  "=4",
			PGLFields.child(3) +  "=5",
			"[3]",
			PGLFields.firstName + "=Jan",
			PGLFields.lastName +  "=Kowalski",
			PGLFields.father +    "=1",
			PGLFields.mother +    "=2",
			"[4]",
			PGLFields.firstName + "=Marta",
			PGLFields.lastName +  "=Kowalska",
			PGLFields.father +    "=1",
			PGLFields.mother +    "=2",
			"[5]",
			PGLFields.firstName + "=Pawel",
			PGLFields.lastName +  "=Kowalski",
			PGLFields.father +    "=1",
			PGLFields.mother +    "=2"
			);
	
	private static PGL pgl;
	private static List<Section> sections;
	
	
	@BeforeClass
	public static void prepare() {
		pgl = new TempFile(content).loadPGL();
		sections = pgl.getSections();
	}

	
	@Test
	public void shouldReturnNumberOfSections() {
		assertEquals(5, pgl.getSections().size());
	}
	
	@Test
	public void shouldReturnSizeOfPGL() {
		assertEquals(5, pgl.size());
	}

	@Test
	public void shouldReturnFirstNames() {
		List<String> expectedFirstNames = List.of("Adam", "Ewa", "Jan", "Marta", "Pawel");
		List<String> actualFirstNames;

		actualFirstNames = sections.stream()
				.map(s -> s.getValue(PGLFields.firstName))
				.collect(Collectors.toList());

		assertEquals(expectedFirstNames, actualFirstNames);
	}

	@Test
	public void shouldReturnLastNames() {
		List<String> expectedLastNames = List.of("Kowalski", "Nowak", "Kowalski", "Kowalska", "Kowalski");
		List<String> actualLastNames;

		actualLastNames = sections.stream()
				.map(s -> s.getValue(PGLFields.lastName))
				.collect(Collectors.toList());

		assertEquals(expectedLastNames, actualLastNames);
	}

	@Test
	public void shouldReturnFathers() {
		List<String> expectedFathers = new ArrayList<>(pgl.size());
		List<String> actualFathers;

		expectedFathers.add(null);
		expectedFathers.add(null);
		expectedFathers.add("1");
		expectedFathers.add("1");
		expectedFathers.add("1");

		actualFathers = sections.stream()
				.map(s -> s.getValue(PGLFields.father))
				.collect(Collectors.toList());

		assertEquals(expectedFathers, actualFathers);
	}

	@Test
	public void shouldReturnMothers() {
		List<String> expectedMothers = new ArrayList<>(pgl.size());
		List<String> actualMothers;

		expectedMothers.add(null);
		expectedMothers.add(null);
		expectedMothers.add("2");
		expectedMothers.add("2");
		expectedMothers.add("2");

		actualMothers = sections.stream()
				.map(s -> s.getValue(PGLFields.mother))
				.collect(Collectors.toList());

		assertEquals(expectedMothers, actualMothers);
	}

	@Test
	public void shouldReturnNumberOfChildren() {
		List<String> expectedChildren = new ArrayList<>(pgl.size());
		List<String> actualChildren;

		expectedChildren.add("3");
		expectedChildren.add("3");
		expectedChildren.add(null);
		expectedChildren.add(null);
		expectedChildren.add(null);

		actualChildren = sections.stream()
				.map(s -> s.getValue(PGLFields.children))
				.collect(Collectors.toList());

		assertEquals(expectedChildren, actualChildren);
	}

	@Test
	public void shouldReturnChildren() {
		assertEquals("3", pgl.getValue("1", PGLFields.child(1)));
		assertEquals("4", pgl.getValue("1", PGLFields.child(2)));
		assertEquals("5", pgl.getValue("1", PGLFields.child(3)));
		
		assertEquals("3", pgl.getValue("2", PGLFields.child(1)));
		assertEquals("4", pgl.getValue("2", PGLFields.child(2)));
		assertEquals("5", pgl.getValue("2", PGLFields.child(3)));
	}

	@Test
	public void shouldReturnNumOfMarriages() {
		assertEquals("1", pgl.getValue("1", PGLFields.marriages));
		assertEquals("1", pgl.getValue("2", PGLFields.marriages));
		assertNull(pgl.getValue("3", PGLFields.marriages));
		assertNull(pgl.getValue("4", PGLFields.marriages));
		assertNull(pgl.getValue("5", PGLFields.marriages));
	}

	@Test
	public void shouldReturnFirstSpouses() {
		assertEquals("2", pgl.getValue("1", PGLFields.spouse(1)));
		assertEquals("1", pgl.getValue("2", PGLFields.spouse(1)));
		assertNull(pgl.getValue("3", PGLFields.spouse(1)));
		assertNull(pgl.getValue("4", PGLFields.spouse(1)));
		assertNull(pgl.getValue("5", PGLFields.spouse(1)));
	}

	@Test
	public void shouldReturnSex() {
		assertEquals(Sex.MAN.toString(), pgl.getValue("1", PGLFields.sex));
		assertEquals(Sex.WOMAN.toString(), pgl.getValue("2", PGLFields.sex));
		assertNull(pgl.getValue("3", PGLFields.sex));
		assertNull(pgl.getValue("4", PGLFields.sex));
		assertNull(pgl.getValue("5", PGLFields.sex));
	}
}
