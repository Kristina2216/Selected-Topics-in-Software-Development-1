package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonRaphsonCalculate {
	
	/**
	 * 
	 * @param reMin lijevi kut prozora na realnoj osi
	 * @param reMax desni kut prozora na realnoj osi
	 * @param imMin donji kut prozora na imaginarnoj osi
	 * @param imMax gornji kut prozora na imaginarnoj osi
	 * @param width širina slike
	 * @param height visina slike
	 * @param maxIter maksimalan broj iteracija pri provjeravanju točke
	 * @param startPosition red za koji počinje ispitivanje
	 * @param endPosition red za koji završava ispitivanje
	 * @param data polje u koje upisuje rezultate izračuna pojedine točke
	 * @param rooted kompleksni polinom čije nultočke promatramo
	 * @param cancel 
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax,
			int width, int height, int maxIter, int startPosition, int endPosition, short[] data, ComplexRootedPolynomial rooted, AtomicBoolean cancel) {
		int offset = width*(startPosition-1);
		double convergenceTreshold=0.001;
		double rootThreshold=0.002;
		for(int y = startPosition; y <= endPosition; y++) {
			if(cancel.get()) break;
			for(int x = 0; x < width; x++) {
				double cre = x / (width-1.0) * (reMax - reMin) + reMin;
				double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
				double module;
				int iters = 0;
				Complex zn=new Complex(cre,cim);
				do {
					Complex numerator=rooted.apply(zn);
					Complex denominator=rooted.toComplexPolynom().derive().apply(zn);
					Complex znold=zn;
					Complex fraction=numerator.divide(denominator);
					zn=zn.sub(fraction);
					module=znold.sub(zn).module();
					iters++;
				} while(module>convergenceTreshold && iters<maxIter);
				short index=rooted.findClosestRootIndex(zn, rootThreshold);
				data[offset]=index;
				offset++;
			}
		}
	}
}
