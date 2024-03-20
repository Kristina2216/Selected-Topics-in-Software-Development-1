package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
/**
 * Razred predstavlja jednostavni 
 * kalkulator
 * @author KristinaPetkovic
 *
 */
public class Calculator extends JFrame {
	private static final long serialVersionUID = 10000L;
	private CalcModelImpl model;
	private ArrayList<Double> queue;
	/**
	 * konstuktor postavlja operaciju zatvaranja
	 * i novu listu brojeva pohranjenih na stog
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		model=new CalcModelImpl();
		queue=new ArrayList<Double>();
		initGUI();
		pack();
		}
	/**
	 * metoda u kojoj se postavljaju elementi prozora
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		int number=1;
		for(int i=4;i>1;i--) {
			for(int j=3;j<6;j++) {
				String position=""+i+","+j;
				String num=String.valueOf(number);
				int localNum=number;
				cp.add(new CustomButton(num,l-> model.insertDigit(localNum)),position);
				number++;
				
			}
		}
		cp.add(new CustomButton("0",a->model.insertDigit(0)),"5,3");
		cp.add(new CustomButton("+/-",a->model.swapSign()),"5,4");
		cp.add(new CustomButton(".",a->model.insertDecimalPoint()),"5,5");
		cp.add(new CustomButton("clr",a->model.clear()),"1,7");
		cp.add(new CustomButton("res",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clearAll();
			}
		}),"2,7");
		cp.add(new CustomButton("push",a->this.queue.add(model.getValue())),"3,7");
		cp.add(new CustomButton("pop",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clear();
				if(queue.isEmpty())
					System.out.println("Queue is empty!");
				else
					model.setValue(queue.get(queue.size()-1));
			}
		}),"4,7");
		cp.add(new CustomButton("=",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),model.getValue()));
				model.setActiveOperand(0);
				model.setPendingBinaryOperation(null);
			}
		}),"1,6");
		cp.add(new CustomButton("/",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setPendingBinaryOperation((a,b)->a/b);
				model.setActiveOperand(model.getValue());
				model.clear();
			}
		}),"2,6");
		cp.add(new CustomButton("*",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setPendingBinaryOperation((a,b)->a*b);
				model.setActiveOperand(model.getValue());
				model.clear();
			}
		}),"3,6");
		cp.add(new CustomButton("-",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setPendingBinaryOperation((a,b)->a-b);
				model.setActiveOperand(model.getValue());
				model.clear();
			}
		}),"4,6");
		cp.add(new CustomButton("+",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setPendingBinaryOperation((a,b)->a+b);
				model.setActiveOperand(model.getValue());
				model.clear();
			}
		}),"5,6");
		JLabel l=new JLabel(model.toString());
		l.setHorizontalAlignment(SwingConstants.RIGHT);
		l.setBackground(Color.yellow);
		l.setOpaque(true);
		l.setFont(l.getFont().deriveFont(30f));
		cp.add(l, new RCPosition(1,1));
		model.addCalcValueListener(new CalcValueListener() {

			@Override
			public void valueChanged(CalcModel model) {
				l.setText(model.toString());
				
			}
		});
		cp.add(new DoubleActionButton("1/x",
				new Supplier<String>(){
					public String get() {
						model.setValue(1/model.getValue());
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					return model.toString();
				}
			}
			,model), "2,1");
		cp.add(new DoubleActionButton("sin",
				new Supplier<String>(){
					public String get() {
						model.setValue(Math.sin(model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(Math.asin(model.getValue()));
					return model.toString();
				}
			}
			,model), "2,2");
		cp.add(new DoubleActionButton("cos",
				new Supplier<String>(){
					public String get() {
						model.setValue(Math.cos(model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(Math.acos(model.getValue()));
					return model.toString();
				}
			}
			,model), "3,2");
		cp.add(new DoubleActionButton("log",
				new Supplier<String>(){
					public String get() {
						model.setValue(Math.log10(model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(Math.pow(10,(model.getValue())));
					return model.toString();
				}
			}
			,model), "3,1");
		cp.add(new DoubleActionButton("ln",
				new Supplier<String>(){
					public String get() {
						model.setValue(Math.log(model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(Math.pow(Math.E,(model.getValue())));
					return model.toString();
				}
			}
			,model), "4,1");
		cp.add(new DoubleActionButton("tan",
				new Supplier<String>(){
					public String get() {
						model.setValue(Math.tan(model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(Math.atan(model.getValue()));
					return model.toString();
				}
			}
			,model), "4,2");
		cp.add(new DoubleActionButton("x^n",
				new Supplier<String>(){
					public String get() {
						model.setValue(Math.pow(model.getActiveOperand(),model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(Math.pow(model.getActiveOperand(),1/model.getValue()));
					return model.toString();
				}
			}
			,model), "5,1");
		cp.add(new DoubleActionButton("ctg",
				new Supplier<String>(){
					public String get() {
						model.setValue(1/Math.tan(model.getValue()));
						return model.toString();
					}
				}
			, new Supplier<String>(){
				public String get() {
					model.setValue(1/Math.atan(model.getValue()));
					return model.toString();
				}
			}
			,model), "5,2");
	JCheckBox box=new JCheckBox("Inv", false);
	box.setBackground(new Color(0xC6CCE9));
	box.addActionListener(m->model.invert());
	box.setBorderPainted(true);
    box.setBorder(BorderFactory.createLineBorder(new Color(0x556478),1));
	cp.add(box, "5,7");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			Calculator c=new Calculator();
			c.setVisible(true);
		
	});
	}
	
	
}
