package hr.fer.zemris.java.gui.calc.Calculator;

import java.util.function.DoubleBinaryOperator;
/**
 * Razred predstavlja 
 * implementaciju sučelja CalcModel
 * @author KristinaPetkovic
 *
 */
public class CalcModelImpl implements CalcModel {
	private boolean editable;
	private boolean negative;
	private String variableString;
	private double variable;
	private String frozenValue;
	private double activeOperand;
	private DoubleBinaryOperator pendingOperator;
	private boolean activeOperandSet;
	private CalcValueListener[] listeners;
	private int listenerCount;
	private boolean inverted;
	/**
	 * Razred interno pohranjuje 4 zastavice
	 * koje govore o stanju broj(predznak, je li moguće
	 * uređivati, je li pritisnuta tipka za invertiranje i
	 * postoji li spremljen drugi operand u binarnim operacijama)
	 * te pamti trenutno upisan broj
	 */
	public CalcModelImpl() {
		editable=true;
		negative=false;
		variableString="";
		variable=0f;
		frozenValue=null;
		listeners=new CalcValueListener[35];
		listenerCount=0;
		inverted=false;
	}
	/**
	 * 
	 * @return trenutno stanje zastavice inverted
	 */
	public boolean isInverted() {
		return inverted;
	}
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l!=null) 
			listeners[listenerCount++]=l;
		else throw new NullPointerException();
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l!=null)
			for (int i=0;i<listenerCount;i++) {
				if(l==listeners[i]) {
					listeners[i]=null;
				}
			}
		
	}
	/**
	 * mijenja stanje zastavice inverted
	 */
	public void invert() {
		inverted=!inverted;
	}
	@Override
	public double getValue() {
		return variable;
	}

	@Override
	public void setValue(double value) {
		variable=value;
		variableString=String.valueOf(variable);
		negative=value<0;
		editable=false;
		freezeValue();
		buttonPressed();
		
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		editable=true;
		negative=false;
		variableString="";
		variable=0;
		frozenValue=null;
		buttonPressed();
		
	}

	@Override
	public void clearAll() {
		editable=true;
		negative=false;
		variableString="";
		variable=0f;
		frozenValue=null;
		activeOperand=0;
		pendingOperator=null;
		activeOperandSet=false;
		buttonPressed();
		inverted=false;
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable())
			throw new CalculatorInputException();
		variable=variable*(-1);
		negative=!negative;
		if(!negative && variableString.length()!=0)
			variableString=variableString.substring(1);
		else
			if(!variableString.equals(""))
				variableString="-"+variableString;
		if(frozenValue!=null)
			if(variableString.contains("."))
				freezeValue();
			else
				frozenValue=variableString;
		else
			if(!variableString.equals(""))
				frozenValue=variableString;
		buttonPressed();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException{
		if(!variableString.contains(".") && editable&& variableString.length()!=0)
			variableString+=".";
		else
			throw new CalculatorInputException();
		buttonPressed();
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable)
			throw new CalculatorInputException();
		try {
			if(!variableString.equals("0")) {
				variableString+=String.valueOf(digit);
				variable=Double.parseDouble(variableString);
			}
			else
				variableString=String.valueOf(digit);
				if(negative)
					variableString="-"+variableString;
				variable=Double.parseDouble(variableString);
		}catch (NumberFormatException|NullPointerException e) {
			throw new CalculatorInputException();
		}
		if((digit!=0 ||!variableString.equals("0"))) {
			if(variableString.contains("."))
				freezeValue();
			else
				frozenValue=variableString;
		}
		buttonPressed();
		
	}

	@Override
	public boolean isActiveOperandSet() {
		if(!activeOperandSet)
			return false;
		return true;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet())
			throw new IllegalStateException();
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand=activeOperand;
		activeOperandSet=true;
		buttonPressed();
		
	}

	@Override
	public void clearActiveOperand() {
		activeOperand=0;
		activeOperandSet=false;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperator=op;
		
	}
	/**
	 * zapisuje trenutni broj u
	 * varijablu koja se prikazuje
	 * na zaslonu kalkulatora
	 */
	public void freezeValue() {
		frozenValue=variableString;
		buttonPressed();
	}
	/**
	 * @return <code>true</code> ako
	 * je upisan broj
	 */
	boolean hasFrozenValue() {
		return frozenValue!=null;
	}
	@Override
	public String toString() {
		if(hasFrozenValue())
			return frozenValue;
		else if(negative)
			return "-0";
		else
			return String.valueOf(0);
	}
	public void buttonPressed() {
		for(int i=0;i<listenerCount;i++) {
			listeners[i].valueChanged(this);
		}
	}
	
	
	

}
