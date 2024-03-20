package hr.fer.zemris.java.gui.layouts;
/**
 * Razred koji pohranjuje 
 * željenu poziciju elemenata kalkulatora
 * u obliku reda i stupca
 * @author User
 *
 */
public class RCPosition {
	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row=row;
		this.column=column;
	}
	/**
	 * 
	 * @return redak elementa
	 */
	public int getRow() {
		return row;
	}
	/**
	 * 
	 * @return stupac elementa
	 */
	public int getColumn() {
		return column;
	}
}
