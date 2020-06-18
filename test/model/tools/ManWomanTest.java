package model.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Person;
import model.Sex;
import model.random.RandomPerson;

public class ManWomanTest {

	private RandomPerson rPerson = new RandomPerson();;
	private Person man   = rPerson.generate(Sex.MAN);
	private Person woman = rPerson.generate(Sex.WOMAN);
	private Person unknown;
	
	@Before
	public void prepare() {
		unknown = rPerson.generate();
		unknown.setSex(Sex.UNKNOWN);
	}
	
	private void assertUnsuccess(ManWoman manWoman) {
		assertFalse(manWoman.success());
		assertNull(manWoman.getMan());
		assertNull(manWoman.getWoman());
	}
	
	@Test
	public void testManWoman_mw() {
		ManWoman manWoman = new ManWoman(man, woman);
		
		assertTrue(manWoman.success());
		assertEquals(man,   manWoman.getMan());
		assertEquals(woman, manWoman.getWoman());
	}
	
	@Test
	public void testManWoman_wm() {
		ManWoman manWoman = new ManWoman(woman, man);
		
		assertTrue(manWoman.success());
		assertEquals(man,   manWoman.getMan());
		assertEquals(woman, manWoman.getWoman());
	}
	
	@Test
	public void testManWoman_mm() {
		assertUnsuccess(new ManWoman(man, man));
	}
	
	@Test
	public void testManWoman_ww() {
		assertUnsuccess(new ManWoman(woman, woman));
	}

	//-----with null-----
	
	@Test
	public void testManWoman_mn() {
		assertUnsuccess(new ManWoman(man, null));
	}
	
	@Test
	public void testManWoman_nm() {
		assertUnsuccess(new ManWoman(null, man));
	}
	
	@Test
	public void testManWoman_wn() {
		assertUnsuccess(new ManWoman(woman, null));
	}
	
	@Test
	public void testManWoman_nw() {
		assertUnsuccess(new ManWoman(null, woman));
	}
	
	@Test
	public void testManWoman_nn() {
		assertUnsuccess(new ManWoman(null, null));
	}

	//-----with unknown-----
	
	@Test
	public void testManWoman_mu() {
		assertUnsuccess(new ManWoman(man, unknown));
	}
	
	@Test
	public void testManWoman_um() {
		assertUnsuccess(new ManWoman(unknown, man));
	}
	
	@Test
	public void testManWoman_wu() {
		assertUnsuccess(new ManWoman(woman, unknown));
	}
	
	@Test
	public void testManWoman_uw() {
		assertUnsuccess(new ManWoman(unknown, woman));
	}
	
	@Test
	public void testManWoman_uu() {
		assertUnsuccess(new ManWoman(unknown, unknown));
	}

	//-----with null and unknown-----
	
	@Test
	public void testManWoman_nu() {
		assertUnsuccess(new ManWoman(null, unknown));
	}
	
	@Test
	public void testManWoman_un() {
		assertUnsuccess(new ManWoman(unknown, null));
	}
}
