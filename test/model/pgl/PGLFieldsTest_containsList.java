package model.pgl;

import static org.junit.Assert.*;

import org.junit.Test;

public class PGLFieldsTest_containsList {

	private boolean check(String keyName, String sufix) {
		return PGLFields.isListElement(PGLFields.child, PGLFields.child + sufix);
	}
	
	@Test
	public void positive() {
		assertTrue(check(PGLFields.child, "1"));
		assertTrue(check(PGLFields.child, "2"));
		assertTrue(check(PGLFields.child, "3"));
		
		assertTrue(check(PGLFields.spouse, "3"));
		assertTrue(check(PGLFields.weddingDate, "3"));
		assertTrue(check(PGLFields.weddingPlace, "3"));
	}
	
	@Test
	public void nonPositive() {
		assertFalse(check(PGLFields.child, "0"));
		assertFalse(check(PGLFields.child, "-1"));
		assertFalse(check(PGLFields.child, "-2"));
		
		assertFalse(check(PGLFields.spouse, "0"));
		assertFalse(check(PGLFields.weddingDate, "-3"));
		assertFalse(check(PGLFields.weddingPlace, "-1"));
	}
	
	@Test
	public void noNumber() {
		assertFalse(check(PGLFields.child, ""));
	}
	
	@Test
	public void nonNumeral() {
		assertFalse(check(PGLFields.child, "a"));
		assertFalse(check(PGLFields.spouse, "zero"));
		assertFalse(check(PGLFields.weddingPlace, " "));
	}
	
	@Test
	public void wrongKeyName() {
		int len = PGLFields.child.length();
		String wrongName = PGLFields.child.substring(0, len-1) + "x";
		
		assertFalse(PGLFields.isListElement(PGLFields.child, wrongName + "1"));
		assertFalse(PGLFields.contains(wrongName + "1"));
	}
}
