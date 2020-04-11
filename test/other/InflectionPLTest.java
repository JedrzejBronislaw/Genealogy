package other;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Person;
import model.Person.Sex;

public class InflectionPLTest {

	private Person createPerson(String name, Sex sex) {
		Person person = new Person();
		person.setFirstName(name);
		person.setSex(sex);
		return person;
	}

	private String genitiveMan(String name) {
		return InflectionPL.nameGenitive(createPerson(name, Sex.MAN));
	}

	private String genitiveWoman(String name) {
		return InflectionPL.nameGenitive(createPerson(name, Sex.WOMEN));
	}

	@Test
	public void testNameGenitive_Jan() {
		assertEquals("Jana", genitiveMan("Jan"));
	}

	@Test
	public void testNameGenitive_Marek() {
		assertEquals("Marka", genitiveMan("Marek"));
	}

	@Test
	public void testNameGenitive_Piotr() {
		assertEquals("Piotra", genitiveMan("Piotr"));
	}

	@Test
	public void testNameGenitive_Piotrek() {
		assertEquals("Piotrka", genitiveMan("Piotrek"));
	}

	@Test
	public void testNameGenitive_Piotruœ() {
		assertEquals("Piotrusia", genitiveMan("Piotruœ"));
	}

	@Test
	public void testNameGenitive_Pawel() {
		assertEquals("Paw³a", genitiveMan("Pawe³"));
	}

	@Test
	public void testNameGenitive_Antoni() {
		assertEquals("Antoniego", genitiveMan("Antoni"));
	}

	@Test
	public void testNameGenitive_John() {
		assertEquals("Johna", genitiveMan("John"));
	}

	@Test
	public void testNameGenitive_Jacek() {
		assertEquals("Jacka", genitiveMan("Jacek"));
	}

	@Test
	public void testNameGenitive_Jack() {
		assertEquals("Jacka", genitiveMan("Jack"));
	}

	@Test
	public void testNameGenitive_Filip() {
		assertEquals("Filipa", genitiveMan("Filip"));
	}

	@Test
	public void testNameGenitive_Konrad() {
		assertEquals("Konrada", genitiveMan("Konrad"));
	}

	@Test
	public void testNameGenitive_Diego() {
		assertEquals("Diega", genitiveMan("Diego"));
	}

	@Test
	public void testNameGenitive_Hans() {
		assertEquals("Hansa", genitiveMan("Hans"));
	}

	@Test
	public void testNameGenitive_Wawrzyniec() {
		assertEquals("Wawrzyñca", genitiveMan("Wawrzyniec"));
	}

	@Test
	public void testNameGenitive_Bonifacy() {
		assertEquals("Bonifacego", genitiveMan("Bonifacy"));
	}

	@Test
	public void testNameGenitive_Mieszko() {
		assertEquals("Mieszka", genitiveMan("Mieszko"));
	}

	@Test
	public void testNameGenitive_Maciej() {
		assertEquals("Macieja", genitiveMan("Maciej"));
	}

	@Test
	public void testNameGenitive_Maciek() {
		assertEquals("Maæka", genitiveMan("Maciek"));
	}

	@Test
	public void testNameGenitive_Macius() {
		assertEquals("Maciusia", genitiveMan("Maciuœ"));
	}

	//----------
	
	@Test
	public void testNameGenitive_Martyna() {
		assertEquals("Martyny", genitiveWoman("Martyna"));
	}
	
	@Test
	public void testNameGenitive_Maria() {
		assertEquals("Marii", genitiveWoman("Maria"));
	}
	
	@Test
	public void testNameGenitive_Laura() {
		assertEquals("Laury", genitiveWoman("Laura"));
	}
	
	@Test
	public void testNameGenitive_Pelagia() {
		assertEquals("Pelagii", genitiveWoman("Pelagia"));
	}
	
	@Test
	public void testNameGenitive_Olga() {
		assertEquals("Olgi", genitiveWoman("Olga"));
	}
	
	@Test
	public void testNameGenitive_Kaja() {
		assertEquals("Kai", genitiveWoman("Kaja"));
	}
	
	@Test
	public void testNameGenitive_Pola() {
		assertEquals("Poli", genitiveWoman("Pola"));
	}
	
	@Test
	public void testNameGenitive_Ola() {
		assertEquals("Oli", genitiveWoman("Ola"));
	}
	
	@Test
	public void testNameGenitive_Monika() {
		assertEquals("Moniki", genitiveWoman("Monika"));
	}
	
	@Test
	public void testNameGenitive_Gertruda() {
		assertEquals("Gertrudy", genitiveWoman("Gertruda"));
	}
	
	@Test
	public void testNameGenitive_Anna() {
		assertEquals("Anny", genitiveWoman("Anna"));
	}
	
	@Test
	public void testNameGenitive_Ania() {
		assertEquals("Ani", genitiveWoman("Ania"));
	}
	
	@Test
	public void testNameGenitive_Waleria() {
		assertEquals("Walerii", genitiveWoman("Waleria"));
	}
	
	@Test
	public void testNameGenitive_Katarzyna() {
		assertEquals("Katarzyny", genitiveWoman("Katarzyna"));
	}
	
	@Test
	public void testNameGenitive_Kasia() {
		assertEquals("Kasi", genitiveWoman("Kasia"));
	}
	
	@Test
	public void testNameGenitive_Magdalena() {
		assertEquals("Magdaleny", genitiveWoman("Magdalena"));
	}
	
	@Test
	public void testNameGenitive_Magda() {
		assertEquals("Magdy", genitiveWoman("Magda"));
	}
	
	@Test
	public void testNameGenitive_Nicole() {
		assertEquals("Nicole", genitiveWoman("Nicole"));
	}
	
	@Test
	public void testNameGenitive_Nadzieja() {
		assertEquals("Nadziei", genitiveWoman("Nadzieja"));
	}

}
