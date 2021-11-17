package model.pgl.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.LifeStatus;
import model.Person;
import model.Sex;
import model.Tree;
import model.pgl.PGLFields;

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
	
	private static Tree tree;
	private static Person person;
	
	
	@BeforeClass
	public static void prepare() {
		tree = new TempFile(content).loadTree();
		person = tree.getAll()[0];
	}
	
	
	@Test
	public void numberOfPersons() {
		assertEquals(1, tree.getAll().length);
	}

	@Test
	public void firstName() {
		assertEquals("Jan", person.getFirstName());
	}

	@Test
	public void lastName() {
		assertEquals("Kowalski", person.getLastName());
	}

	@Test
	public void birthPlace() {
		assertEquals("Poznan", person.getBirthPlace());
	}

	@Test
	public void deathPlace() {
		assertEquals("Krakow", person.getDeathPlace());
	}

	@Test
	public void deathDate() {
		assertEquals("5.7.2000", person.getDeathDate().toString());
	}

	@Test
	public void birthDate() {
		assertEquals("2.4.1930", person.getBirthDate().toString());
	}

	@Test
	public void isAlive() {
		assertEquals(LifeStatus.NO, person.getLifeStatus());
	}

	@Test
	public void sex() {
		assertEquals(Sex.MAN, person.getSex());
	}

	@Test
	public void alias() {
		assertEquals("Adam", person.getAlias());
	}

	@Test
	public void baptismParish() {
		assertEquals("sw. Jana z Murami", person.getBaptismParish());
	}

	@Test
	public void burialPlace() {
		assertEquals("Poznan Junikowo", person.getBurialPlace());
	}

	@Test
	public void contact() {
		assertEquals("Poznan ul. Poznanska 1", person.getContact());
	}

	@Test
	public void comments() {
		assertEquals(
			"professor of physics" + System.lineSeparator() + "doctor of chemistry",
			person.getComments());
	}

	@Test
	public void father() {
		assertEquals(null, person.getFather());
	}

	@Test
	public void mother() {
		assertEquals(null, person.getMother());
	}

	@Test
	public void numberOfMarriages() {
		assertEquals(0, person.numberOfMarriages());
	}

	@Test
	public void numberOfChildren() {
		assertEquals(0, person.numberOfChildren());
	}

	@Test
	public void notNulChildren() {
		assertNotEquals(null, person.getChildren());
	}

	@Test
	public void childrenArray() {
		assertEquals(0, person.getChildren().length);
	}
}
