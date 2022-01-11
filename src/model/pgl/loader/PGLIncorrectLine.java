package model.pgl.loader;

import java.util.Optional;

import lombok.Getter;

@Getter
public class PGLIncorrectLine {

	private String content;
	private Optional<String> sectionName;
	
	public PGLIncorrectLine(String content, String sectionName) {
		validateContent(content);
		
		this.content = content;
		this.sectionName = Optional.ofNullable(sectionName);
	}
	
	public PGLIncorrectLine(String content) {
		validateContent(content);
		
		this.content = content;
		this.sectionName = Optional.empty();
	}

	
	private void validateContent(String content) {
		if (content == null) throw new IllegalArgumentException("Incorrect PGL line cannot be null.");
	}
}
