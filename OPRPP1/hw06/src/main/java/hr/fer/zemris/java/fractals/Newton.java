package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * klasa koja upravlja crtanjem Newton-Raphsonove iteracije
 * pomoću više dretvi
 */
public class Newton{
	/**
	 * Čita korijene kompleksnog broja dok korisnik ne upiše "done",
	 * potom poziva crtanje
	 */
	public static void main(String[] args) {
	
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
			+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		try(Scanner s=new Scanner(System.in)){
			String input="";
			int rootNumber=1;
			List<Complex> roots=new ArrayList<Complex>();
			while(!input.contains("done")) {
				System.out.println("Root "+rootNumber+">");
				input=s.nextLine();
				if(!input.equals("done")) {
					String[] all=input.split("((?<=\\+|\\-)|(?=\\+|\\-))");
					double real=0.f;
					double imaginary=0.f;
					boolean pos=true;
						for(String n:all) {
							switch(n) {
							case "+":
								pos=true;
								break;
							case"-":
								pos=false;
								break;
							default:
								if(n.substring(0,1).equals("i")) {
									if(n.length()==1) {
										imaginary=1;
									}else
										imaginary=Double.parseDouble(n.substring(1,n.length()));
										if(!pos) imaginary=-1*imaginary;
								}else {
									try {
										real=Double.parseDouble(n);
										if(!pos) real=-1*real;
									}catch (NumberFormatException e) {
										System.err.println("Invalid argument!");
									}
								}
							}
						}
						roots.add(new Complex(real,imaginary));
				}
				rootNumber++;
			}
			Complex[] create=new Complex[roots.size()];
			int i=0;
			for(Complex c:roots) {
				create[i]=c;
				i++;
			}
			FractalViewer.show(new MojProducer(new ComplexRootedPolynomial(Complex.ONE,create)));
		}	
	
	}
	/**
	 * klasa koja implementira sučelje koje upravlja računanjem vrijednosti
	 * za sve točke
	 * @author User
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		ComplexRootedPolynomial p;
		/**
		 * Konstruktor
		 * @param p kompleksni polinom koji promatramo
		 */
		public MojProducer(ComplexRootedPolynomial p) {
			this.p=p;
		}
		/**
		 * metoda stvara prazno polje <code>data[]<code> 
		 * poziva funkciju za izračun te kad završi prenosi rezultate klasi 
		 * <code>IFractalResultObserver<code>
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = 16*16*16;
			short[] data = new short[width * height];
			NewtonRaphsonCalculate.calculate(reMin, reMax, imMin, imMax,
				 width, height, m, 1, height-1, data, p, cancel);
			
			observer.acceptResult(data, (short)(p.toComplexPolynom().order()+1), requestNo);
		}
	}
}
