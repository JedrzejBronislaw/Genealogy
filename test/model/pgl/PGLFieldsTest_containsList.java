package model.pgl;

import static org.junit.Assert.*;

import org.junit.Test;

public class PGLFieldsTest_containsList {

	@Test
	public void test_positive() {
		assertTrue(PGLFields.contains(PGLFields.child + "1"));
		assertTrue(PGLFields.contains(PGLFields.child + "2"));
		assertTrue(PGLFields.contains(PGLFields.child + "3"));
		
		assertTrue(PGLFields.contains(PGLFields.spouse + "3"));
		assertTrue(PGLFields.contains(PGLFields.weddingDate + "3"));
		assertTrue(PGLFields.contains(PGLFields.weddingPlace + "3"));
	}
	
	@Test
	public void test_nonPositive() {
		assertFalse(PGLFields.contains(PGLFields.child + "0"));
		assertFalse(PGLFields.contains(PGLFields.child + "-1"));
		assertFalse(PGLFields.contains(PGLFields.child + "-2"));
		
		assertFalse(PGLFields.contains(PGLFields.spouse + "0"));
		assertFalse(PGLFields.contains(PGLFields.weddingDate + "-3"));
		assertFalse(PGLFields.contains(PGLFields.weddingPlace + "-1"));
	}
	
	@Test
	public void test_noNumber() {
		assertFalse(PGLFields.contains(PGLFields.child + ""));
	}
	
	@Test
	public void test_nonNumeral() {
		assertFalse(PGLFields.contains(PGLFields.child + "a"));
		assertFalse(PGLFields.contains(PGLFields.spouse + "zero"));
		assertFalse(PGLFields.contains(PGLFields.weddingPlace + " "));
	}

}
