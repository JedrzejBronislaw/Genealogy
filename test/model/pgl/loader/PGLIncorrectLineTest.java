package model.pgl.loader;

import static org.junit.Assert.*;

import org.junit.Test;

public class PGLIncorrectLineTest {

	@Test
	public void shouldCreatePGLIncorrectLineWhenArgsAreCorrect() {
		new PGLIncorrectLine("line", "sectionName");
	}

	@Test
	public void shouldGetContetOfIncorrectLineWhenArgsAreCorrect() {
		// given
		String content = "line";
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine(content, "sectionName");
		
		// when
		String returnedContent = incorrectLine.getContent();
		
		// then
		assertEquals(content, returnedContent);
	}

	@Test
	public void shouldGetSectionNameOfIncorrectLineWhenArgsAreCorrect() {
		// given
		String sectionName = "sectionName";
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine("line", sectionName);
		
		// when
		String returnedSectionName = incorrectLine.getSectionName().get();
		
		// then
		assertEquals(sectionName, returnedSectionName);
	}

	@Test
	public void shouldGetEmptyContetOfIncorrectLineWhenContentIsEmpty() {
		// given
		String content = "";
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine(content, "sectionName");
		
		// when
		String returnedContent = incorrectLine.getContent();
		
		// then
		assertEquals(content, returnedContent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWhenContentIsNull() {
		new PGLIncorrectLine(null, "sectionName");
	}

	@Test
	public void shouldGetEmptySectionNameOfIncorrectLineWhenSectionNameIsEmpty() {
		// given
		String sectionName = "";
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine("line", sectionName);
		
		// when
		String returnedSectionName = incorrectLine.getSectionName().get();
		
		// then
		assertEquals(sectionName, returnedSectionName);
	}

	@Test
	public void shouldGetEmptyOptionalWhenSectionNameIsNull() {
		// given
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine("line", null);
		
		// when
		boolean sectionNameExists = incorrectLine.getSectionName().isPresent();
		
		// then
		assertFalse(sectionNameExists);
	}

	


	@Test
	public void shouldCreatePGLIncorrectLineWhenUseOneArgConstructor() {
		new PGLIncorrectLine("line");
	}

	@Test
	public void shouldGetContetOfIncorrectLineWhenUseOneArgConstructor() {
		// given
		String content = "line";
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine(content);
		
		// when
		String returnedContent = incorrectLine.getContent();
		
		// then
		assertEquals(content, returnedContent);
	}

	@Test
	public void shouldGetEmptyOptionalWhenUseOneArgConstructor() {
		// given
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine("line");
		
		// when
		boolean sectionNameExists = incorrectLine.getSectionName().isPresent();
		
		// then
		assertFalse(sectionNameExists);
	}
	
	@Test
	public void shouldGetEmptyContetOfIncorrectLineWhenUseOneArgConstructor() {
		// given
		String content = "";
		PGLIncorrectLine incorrectLine = new PGLIncorrectLine(content);
		
		// when
		String returnedContent = incorrectLine.getContent();
		
		// then
		assertEquals(content, returnedContent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWhenContentIsNullAndUseOneArgConstructor() {
		new PGLIncorrectLine(null);
	}
}
