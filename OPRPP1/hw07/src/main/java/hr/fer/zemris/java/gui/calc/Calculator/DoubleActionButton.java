package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Color;
import java.util.function.Supplier;

import javax.swing.JButton;
/**
 * Razred nasljeđuje <code>JButton</code>
 * te mu postavlja boju i font
 * interno pohranjuje i 2 varijable tipa supplier kojima
 * ActionListener promovira posao gumba ovisno o stanju zastavice
 * inverted u modelu
 * @author KristinaPetkovic
 *
 */
public class DoubleActionButton extends JButton{
	private static final long serialVersionUID = 1000000L;
	Supplier<String> regular;
	Supplier<String> inverted;
	CalcModelImpl model;
	
	public DoubleActionButton(String title, Supplier<String> s1, Supplier<String> s2, CalcModelImpl model) {
		super(title);
		this.setBackground(new Color(0xC6CCE9));
		this.setOpaque(true);
		this.setFont(this.getFont().deriveFont(30f));
		this.model=model;
		this.regular=s1;
		this.inverted=s2;
		this.addActionListener(l->this.setOperation());
		getButton();
	}
	private JButton getButton() {
		return this;
	}
	/**
	 * ovisno o stanju zastavice inverted
	 * poziva odgovarajuću funkciju
	 */
	public void setOperation() {
		if(model.isInverted()) {
			inverted.get();
		}
		else
			regular.get();
	}

}
