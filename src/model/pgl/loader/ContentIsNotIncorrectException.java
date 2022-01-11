package model.pgl.loader;

public class ContentIsNotIncorrectException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public ContentIsNotIncorrectException(String line) {
		super("Line \"" + line + "\" is correct PGL line.");
	}
}
