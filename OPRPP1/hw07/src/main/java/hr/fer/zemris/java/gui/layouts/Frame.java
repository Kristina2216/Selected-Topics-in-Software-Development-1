package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Frame extends JFrame{
	public Frame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();

		}
		private void initGUI() {
			Container cp = getContentPane();
			cp.setLayout(new CalcLayout(3));
			cp.add(l("tekst 1"), "1,1");
			cp.add(l("tekst 2"), new RCPosition(2,3));
			cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
			cp.add(l("tekst kraći"), new RCPosition(4,2));
			cp.add(l("tekst srednji"), new RCPosition(4,5));
			cp.add(l("tekst"), new RCPosition(4,7));
		}
		
		private JLabel l(String text) {
			JLabel l = new JLabel(text);
			l.setBackground(Color.YELLOW);
			l.setOpaque(true);
			return l;
		}
		public static void main(String[] args) {
			SwingUtilities.invokeLater(()->{
				new Frame().setVisible(true);
		});
		}

}
