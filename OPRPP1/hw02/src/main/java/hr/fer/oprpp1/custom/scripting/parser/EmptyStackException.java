package hr.fer.oprpp1.custom.scripting.parser;

public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmptyStackException () {
		super();
	}
	public EmptyStackException (String message) {
		super(message);
	}
}
