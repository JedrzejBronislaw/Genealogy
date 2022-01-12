package model.pgl.loader;

import java.util.Optional;

import lombok.Getter;

@Getter
public class PGLIncorrectLine {

	private String content;
	private Optional<String> sectionName;
	
	
	public PGLIncorrectLine(String content) {
		this(content, null);
	}
	
	public PGLIncorrectLine(String content, String sectionName) {
		validate(content, sectionName);
		
		this.content = content;
		this.sectionName = Optional.ofNullable(sectionName);
	}

	
	private void validate(String content, String sectionName) {
		if (content == null)
			throw new IllegalArgumentException("Incorrect PGL line cannot be null.");
		
		if (content.contains("=") && sectionName != null)
			throw new ContentIsNotIncorrectException(content);
	}
}
