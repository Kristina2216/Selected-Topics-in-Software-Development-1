package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.calc.Calculator.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator.CalcModelImpl;

public class CalcLayoutTest {
	private CalcLayout layout;
	
	private static CalcLayout newCalcLayout() {
		return new CalcLayout();
	}

	@BeforeEach
	public void setup() {
		layout = newCalcLayout();
	}
	
	@Test
	public void indexRowOutOfBound() {
		try {
			layout.addLayoutComponent(new JButton(),"6,1"); 
		}catch(CalcLayoutException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void firstRowThrows() {
		try {
			layout.addLayoutComponent(new JButton(),"1,3"); 
		}catch(CalcLayoutException e) {
			return;
		}
		fail();
	}
	@Test
	public void sameIndexThrows() {
		try {
			layout.addLayoutComponent(new JButton(),"1,7"); 
			layout.addLayoutComponent(new JButton(),"1,7"); 
		}catch(CalcLayoutException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void checkDimensions() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); 
		l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(dim.width, 152);
		assertEquals(dim.height, 158);
	}
	@Test
	public void checkDimensions2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.width, 152);
		assertEquals(dim.height, 158);
	}

}
