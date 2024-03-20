package hr.fer.zemris.java.gui.layouts;
/**
 * Iznimka koja nastaje
 * prilikom stvaranja layouta
 * @author User
 *
 */
public class CalcLayoutException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public CalcLayoutException () {
		super();
	}
	public CalcLayoutException (String message) {
		super(message);
	}
}
