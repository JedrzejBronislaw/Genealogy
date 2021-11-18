package model.pgl.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import model.LifeStatus;
import model.Sex;
import model.pgl.PGL;
import model.pgl.PGLFields;
import model.pgl.Section;

public class PGLLoaderTest_SinglePerson {
	
	private static String content = String.join("\n",
			"[1]",
			PGLFields.firstName +     "=Jan",
			PGLFields.lastName +      "=Kowalski",
			PGLFields.birthPlace +    "=Poznan",
			PGLFields.deathPlace +    "=Krakow",
			PGLFields.birthDate +     "=2.4.1930",
			PGLFields.deathDate +     "=5.7.2000",
			PGLFields.lifeStatus +    "=" + LifeStatus.NO,
			PGLFields.sex +           "=" + Sex.MAN,
			PGLFields.alias +         "=Adam",
			PGLFields.baptismParish + "=sw. Jana z Murami",
			PGLFields.burialPlace +   "=Poznan Junikowo",
			PGLFields.contact +       "=Poznan ul. Poznanska 1 ",
			PGLFields.comments +      "=professor of physics" + PGLFields.lineSeparator + "doctor of chemistry"
			);
	
	private static PGL pgl;
	private static Section person;
	
	
	@BeforeClass
	public static void prepare() {
		pgl = new TempFile(content).loadPGL();
		person = pgl.getSection("1").get();
	}

	
	@Test
	public void shouldReturnNumberOfSections() {
		assertEquals(1, pgl.getSections().size());
	}
	
	@Test
	public void shouldReturnSizeOfPGL() {
		assertEquals(1, pgl.size());
	}

	@Test
	public void shouldReturnFirstName() {
		assertEquals("Jan", person.getValue(PGLFields.firstName));
	}

	@Test
	public void shouldReturnLastName() {
		assertEquals("Kowalski", person.getValue(PGLFields.lastName));
	}

	@Test
	public void shouldReturnBirthPlace() {
		assertEquals("Poznan", person.getValue(PGLFields.birthPlace));
	}

	@Test
	public void shouldReturnDeathPlace() {
		assertEquals("Krakow", person.getValue(PGLFields.deathPlace));
	}

	@Test
	public void shouldReturnDeathDate() {
		assertEquals("5.7.2000", person.getValue(PGLFields.deathDate));
	}

	@Test
	public void shouldReturnBirthDate() {
		assertEquals("2.4.1930", person.getValue(PGLFields.birthDate));
	}

	@Test
	public void shouldReturnAliveStatus() {
		assertEquals(LifeStatus.NO.toString(), person.getValue(PGLFields.lifeStatus));
	}

	@Test
	public void shouldReturnSex() {
		assertEquals(Sex.MAN.toString(), person.getValue(PGLFields.sex));
	}

	@Test
	public void shouldReturnAlias() {
		assertEquals("Adam", person.getValue(PGLFields.alias));
	}

	@Test
	public void shouldReturnBaptismParish() {
		assertEquals("sw. Jana z Murami", person.getValue(PGLFields.baptismParish));
	}

	@Test
	public void shouldReturnBurialPlace() {
		assertEquals("Poznan Junikowo", person.getValue(PGLFields.burialPlace));
	}

	@Test
	public void shouldReturnContact() {
		assertEquals("Poznan ul. Poznanska 1", person.getValue(PGLFields.contact));
	}

	@Test
	public void shouldReturnComments() {
		assertEquals(
			"professor of physics" + PGLFields.lineSeparator + "doctor of chemistry",
			person.getValue(PGLFields.comments));
	}

	@Test
	public void shouldReturnNullWhenAskForFather() {
		assertNull(person.getValue(PGLFields.father));
	}

	@Test
	public void shouldReturnNullWhenAskForMother() {
		assertNull(person.getValue(PGLFields.mother));
	}

	@Test
	public void shouldReturnNullWhenAskForNumberOfMarriages() {
		assertNull(person.getValue(PGLFields.marriages));
	}

	@Test
	public void shouldReturnNullWhenAskForNumberOfChildren() {
		assertNull(person.getValue(PGLFields.children));
	}

	@Test
	public void shouldReturnNullWhenAskForChild1() {
		assertNull(person.getValue(PGLFields.child(1)));
	}
}
