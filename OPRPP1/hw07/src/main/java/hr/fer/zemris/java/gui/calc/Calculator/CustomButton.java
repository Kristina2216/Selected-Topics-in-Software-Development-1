package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/**
 * Razred nasljeđuje <code>JButton</code>
 * te mu postavlja boju i font
 * @author KristinaPetkovic
 *
 */
public class CustomButton extends JButton{
	private static final long serialVersionUID = 10000L;
	
	public CustomButton(String title, ActionListener l) {
		super(title);
		this.setBackground(new Color(0xC6CCE9));
		this.addActionListener(l);
		this.setOpaque(true);
		this.setFont(this.getFont().deriveFont(30f));
		getButton();
	}
	private JButton getButton() {
		return this;
	}
}
