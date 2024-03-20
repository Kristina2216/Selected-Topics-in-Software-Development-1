package hr.fer.oprpp1.custom.collections;
/**
 * Razred nasljeđuje RunTimeException i
 * predstavlja iznimke u radu Stoga
 *
 */
public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmptyStackException () {
		super();
	}
	public EmptyStackException (String message) {
		super(message);
	}
}
