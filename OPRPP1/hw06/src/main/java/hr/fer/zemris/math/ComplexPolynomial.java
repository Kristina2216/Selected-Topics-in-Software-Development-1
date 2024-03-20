package hr.fer.zemris.math;
/**
 * Klasa za modeliranje
 * kompleksnih polinoma
 *
 */
public class ComplexPolynomial {
	Complex[] factors;
	/**
	 * konstruktor prima članove koji stoje uz potencije z
	 * @param factors koeficijenti
	 */
	public ComplexPolynomial(Complex ...factors) {
		int i=0;
		this.factors=new Complex[factors.length];
		for(Complex c: factors) {
			this.factors[i]=c;
			i++;
		}
	}
	/**
	 * @return stupanj pozivajućeg polinoma
	 */
	public short order() {
		return (short)(factors.length-1);
	}
	/**
	 * @param p polinom kojim se množi pozivajući
	 * @return novi kompleksni polinom koji je umnožak
	 * pozivajućeg i p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int m=this.factors.length;
		int n=p.factors.length;
		Complex[] newFactors=new Complex[m+n-1];
		int level=0;
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				level=i+j;
				Complex toAdd=this.factors[i].multiply(p.factors[j]);
				if(newFactors[level]!=null)
					newFactors[level]=newFactors[level].add(toAdd);
				else
					newFactors[level]=toAdd;
			}
		}
		return new ComplexPolynomial(newFactors);
	}
	/**
	 * operacija deriviranja
	 * @return prva derivacija pozivajućeg kompleksnog
	 * polinoma
	 */
	public ComplexPolynomial derive() {
		Complex[] derivations=new Complex[factors.length-1];
		for(int i=1;i<factors.length;i++) {
			derivations[i-1]=factors[i].multiply(new Complex(i,0));
		}
		return new ComplexPolynomial(derivations);
	}
	/**
	 * Računa vrijednost polinoma u zadanoj točki
	 * @param z točka
	 * @return vrijednost polinoma u točki z
	 */
	public Complex apply(Complex z) {
		Complex value=factors[0];
		for(int i=0;i<factors.length;i++) {
			value=value.add(factors[i].multiply(z.power(i)));
		}
		return value;
	}
	/**
	 * @return tekstualni zapis kompleksnog polinoma
	 */
	public String toString() {
		String r="";
		for(int i=factors.length-1;i>=0;i--) {
			r=r+"("+factors[i].toString()+")";
			if(i!=0)
				r=r+"*z^"+i+"+";
		}
		return r;
	}
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
				ComplexPolynomial cp = crp.toComplexPolynom();
				System.out.println(crp);
				System.out.println(cp);
				System.out.println(cp.derive());
	}
}
