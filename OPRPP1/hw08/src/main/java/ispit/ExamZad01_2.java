package ispit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ExamZad01_2 extends JDialog{
	public ExamLayoutManager exlm;
	
	public ExamZad01_2() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		pack();
	}
	private void initGUI() {
		Container cp = getContentPane();
		exlm = new ExamLayoutManager(33);
		JSlider percentage = new JSlider(JSlider.HORIZONTAL,
                10, 90, 20);
		percentage.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				 JSlider source = (JSlider)e.getSource();
				 int value=ExamZad01_2.this.exlm.percentage;
				 if (!source.getValueIsAdjusting()) 
					 value= (int)source.getValue();
				ExamZad01_2.this.exlm.setPercentage(value,ExamZad01_2.this );
				
			}
			
		});
		cp.setLayout(exlm);
		cp.add(percentage);
		cp.add(
				makeLabel("Ovo je tekst za područje 1.", Color.RED),
				ExamLayoutManager.AREA1);
		cp.add(
				makeLabel("Područje 2.", Color.GREEN),
				ExamLayoutManager.AREA2);
		cp.add(
				makeLabel("Područje 3.", Color.YELLOW),
				ExamLayoutManager.AREA3);
	}
	private Component makeLabel(String txt, Color col) {
		JLabel lab = new JLabel(txt);
		lab.setOpaque(true);
		lab.setBackground(col);
		return lab;
	}

}
