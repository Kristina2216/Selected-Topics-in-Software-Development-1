package hr.fer.zemris.math;


import java.util.ArrayList;
import java.util.List;

/**
 * Klasa za modeliranje kompleksnih brojevima
 */

public class Complex {
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private final double real;
	private final double imaginary;
	/**
	 * konstruktor bez parametra delegira stvaranje 
	 * kompleksnog broja(0,0) drugom konstruktoru
	 */
	public Complex() {
		this(0,0);
	}
	/**
	 * Konstruktor
	 * @param re realni dio
	 * @param im imaginarni dio
	 */
	public Complex(double re, double im) {
		this.real=re;
		this.imaginary=im;
	}
	/**
	 * Računa kut kompleksnog broja
	 * u radijanima
	 * @return kut u radijanima
	 * @return
	 */
	private double angle() {
		if(real>0) {
			if(imaginary>0)
				return Math.atan2(imaginary, real);
			else
				return Math.atan2(imaginary, real)+2*Math.PI;
		}else {
			if(imaginary>0) 
				return Math.atan2(imaginary, real);
			else
				return Math.atan2(imaginary, real)+2*Math.PI;
		}
		
	}
	/**
	 * Računa modul
	 * @return modul kompleksnog broja
	 */
	
	public double module() {
		return Math.sqrt(Math.pow(real,2)+Math.pow(imaginary,2));
	}
	/**
	 * 
	 * @param c drugi kompleksni broj 
	 * @return novi kompleksni broj
	 * koji je umnožak broja c i pozivajućeg broja
	 */
	public Complex multiply(Complex c) {
		double real1=this.real*c.real-this.imaginary*c.imaginary;
		double imaginary1=this.imaginary*c.real+this.real*c.imaginary;
		return new Complex(real1,imaginary1);
	}
	/**
	 * 
	 * @param c drugi kompleksni broj 
	 * @return novi kompleksni broj
	 * koji je količnik pozivajućeg broja i broja c
	 */
	public Complex divide(Complex c) {
		double magnitude1=this.module()/c.module();
		double angle1=this.angle()-c.angle();
		return new Complex(magnitude1*Math.cos(angle1), magnitude1*Math.sin(angle1));
	}
	/**
	 * Zbraja dva kompleksna broja
	 * @param c pribrojnik
	 * @return novi kompleksni broj
	 * koji je zbroj pozivajućeg broja i broja c
	 */
	
	public Complex add(Complex c) {
		return new Complex(this.real+c.real,this.imaginary+c.imaginary);
	}
	/**
	 * Oduzima dva kompleksna broja
	 * @param c umanjitelj
	 * @return novi kompleksni broj
	 * koji je razlika pozivajućeg broja i broja c
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real-c.real,this.imaginary-c.imaginary);
	}
	/**
	 * opercija množenja s 1
	 * @return novi kompleksni broj
	 * čiji su predznaci realnog i imaginarnog dijela
	 * promijenjeni
	 */
	public Complex negate() {
		return new Complex(-real,-imaginary);
	}
	/**
	 * potenciranje
	 * @param n potencija
	 * @return novi kompleksni broj koji
	 * predstavlja n-tu potenciju pozivajućeg
	 */
	public Complex power(int n) {
		if(n<0) {
			throw new IllegalArgumentException();
		}
		double magnitude1=Math.pow(this.module(),n);
		double angle1=n*this.angle();
		return new Complex(magnitude1*Math.cos(angle1), magnitude1*Math.sin(angle1));
	}
	/**
	 * korijenovanje
	 * @param n korijen
	 * @return novi kompleksni broj koji
	 * predstavlja n-ti korijen pozivajućeg
	 */
	public List<Complex> root(int n) {
		if(n<1) {
			throw new IllegalArgumentException();
		}
		double magnitude1=Math.pow(this.module(),1./n);
		double angle1=this.angle()/ n;
		double fraction=(Math.PI*2)/n;
		List<Complex> returnList=new ArrayList<Complex>();
		for (int i=0;i<n;i++) {
			returnList.add(new Complex(magnitude1*Math.cos(angle1+i*fraction),magnitude1*Math.sin(angle1+i*fraction)));
		}
		return returnList;
	}
	/**
	 * @return tekstualni zapis kompleksnog broja
	 */
	@Override
	public String toString() {
		if (imaginary>0) {
			return String.valueOf(real)+"+"+"i"+String.valueOf(imaginary);
		}else if(imaginary==0){
			return String.valueOf(real)+"+i0.0";
		}
		else 
			return String.valueOf(real)+"-i"+String.valueOf(Math.abs(imaginary));
	}
	
}
