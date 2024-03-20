package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
/**
 * Klasa za modeliranje
 * kompleksnih polinoma u faktoriziranom
 * obliku
 *
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;
	/**
	 * konstruktor prima konstantu i  nultočke
	 * @param constant slobodni član
	 * @param roots polje nultočaka
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant=constant;
		int i=0;
		this.roots=new Complex[roots.length];
		for(Complex c: roots) {
			this.roots[i]=c;
			i++;
		}
	}
	/**
	 * Računa vrijednost polinoma u zadanoj točki
	 * @param z točka
	 * @return vrijednost polinoma u točki z
	 */
	public Complex apply(Complex z) {
		Complex value=constant;
		for(int i=0;i<roots.length;i++) {
			value=value.multiply((z.sub(roots[i])));
		}
		return value;
	}
	/**
	 * množi nultočke te vraća nefaktorizirani oblik
	 * kompleksnog polinoma
	 * @return <code> ComplexPolynomial<code>
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result=new ComplexPolynomial(this.constant);
		for(int i=0;i<roots.length;i++) {
			ComplexPolynomial c=new ComplexPolynomial(roots[i], Complex.ONE);
			result=result.multiply(c);
		}
		return result;
	}
	/**
	 * 
	 * @param zn kompleksni broj čiju udaljenost provjeravamo
	 * @param rootThreshold najveća prihvatljiva udaljenost od neke nultočke
	 * @return indeks najbliže nultočke ukliko je udaljenost od nje manja od
	 * <code> rootThreshold<code> inače 0
	 */
	public short findClosestRootIndex(Complex zn, double rootThreshold) {
		double min=0;
		short index=0;
		int start=1;
		for(Complex root:roots) {
			double distance=zn.sub(root).module();
			if(start==1 || distance<min) {
				min=distance;
				index=(short)start;
			}
			start++;
		}
		if(min>0.002) {
			return 0;
		}
		else
			return index;
	}
	/**
	 * @return teksutalni zapis kompleksnog polinoma u
	 * obliku umnoška nultočaka
	 */
	@Override
	public String toString() {
		String r="("+constant.toString()+")";
		for (int i=0;i<roots.length;i++) {
			r=r+"(z-("+roots[i].toString()+"))";
			if(i!=roots.length-1)
				r=r+"*";
		}
		return r;
	}
	

}
