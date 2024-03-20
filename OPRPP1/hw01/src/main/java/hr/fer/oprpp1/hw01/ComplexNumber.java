package hr.fer.oprpp1.hw01;
/**
 * Razred predstavlja implementaciju kompleksnih brojeva.
 * Sadrži dvije članske varijable; realni dio i kompleksni dio
 * u kartezijevim koordinatama.
 * 
 *
 */

public class ComplexNumber {
	private final double real;
	private final double imaginary;
	/**
	 * Konstruktor 
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real=real;
		this.imaginary=imaginary;
	}
	/**
	 * Tvornička metoda
	 * @param real realni dio kompleksnog broja, 
	 * imaginarni je 0.
	 * @return Novi kompleksni broj (<code>real<code>, 0)
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,  0);
	}
	/**
	 * Tvornička metoda
	 * @param imaginary imaginarni dio kompleksnog broja, 
	 * realni je 0.
	 * @return Novi kompleksni broj (0, <code>imaginary<code>)
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0,imaginary);
	}
	/**
	 * Tvornička metoda
	 * @param magnitude radijus
	 * @param angle kut
	 * @return Novi kompleksni broj <code>(r*sin(φ), r*cos(φ))<code>
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	/**
	 * Tvornička metoda, pretvara string u kompleksni broj
	 * dopušteni oblici su: <code>"+real", "imaginary*i","-real,"real+imaginary*i" <code>
	 * 
	 * @param s predstavlja string iz kojeg se čitaju realni i apsolutni dio 
	 * @return Novi kompleksni broj <code>(r*sin(φ), r*cos(φ))<code>
	 */
	public static ComplexNumber parse(String s) {
		String[] all=s.split("((?<=\\+|\\-)|(?=\\+|\\-))");
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
					if(n.substring(n.length() - 1).equals("i")) {
						if(n.length()==1) {
							imaginary=1;
						}else
							imaginary=Double.parseDouble(n.substring(0,n.length()-1));
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
			return new ComplexNumber(real,imaginary);
	}
	/**
	 * Getter
	 * 
	 * @return <code>double<code> realni dio
	 */
	public double getReal() {
		return real;
	}
	/**
	 * Getter
	 * 
	 * @return <code>double<code> imaginarni dio
	 */
	public double getImaginary() {
		return imaginary;
	}
	/**
	 * Getter
	 * 
	 * @return <code>double<code> radijus
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real,2)+Math.pow(imaginary,2));
	}
	/**
	 * Getter
	 * 
	 * @return <code>double<code> kut u radijanima u intervalu [0,2*PI]
	 */
	public double getAngle() {
		double angle=Math.atan2(imaginary,real);
		if(angle<0) {
			//4 kvadrant
			if(Math.abs(angle)<Math.PI/2) {
				//treba biti 4 kvadrant
				if(real>0)
					angle=angle+2*Math.PI;
				//treba biti 2 kvadrant
				else
					angle=angle+Math.PI;
			//3 kvadrant
			}else{
				//treba biti 3 kvadrant
				if(real<0)
					angle=angle+2*Math.PI;
				//treba biti 1 kvadrant
				else
					angle=angle+Math.PI;
			}
		}else {
			//1 kvadrant
			if(Math.abs(angle)<Math.PI/2) {
				//treba biti 3
				if(real<0) {
					angle=angle+Math.PI;
				}
			//2 kvadrant
			}else {
				//4 kvadrant
				if(real>0) {
					angle=angle+Math.PI;
				}
			}
		}
		return angle;
	}
	/**
	 * Zbraja 2 kompleksna broja.
	 * 
	 * @param c drugi kompleksni broj
	 * @return novi kompleksni broj koji predstavlja zbroj.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real+c.real,this.imaginary+c.imaginary);
	}
	/**
	 * Od broja na kojem je pozvana metoda oduzima drugi.
	 * 
	 * @param c drugi kompleksni broj
	 * @return novi kompleksni broj koji predstavlja razliku.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real-c.real,this.imaginary-c.imaginary);
	}
	/**
	 * Množi 2 kompleksna broja.
	 * 
	 * @param c drugi kompleksni broj
	 * @return novi kompleksni broj koji predstavlja umnožak.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real1=this.real*c.real-this.imaginary*c.imaginary;
		double imaginary1=this.imaginary*c.real+this.real*c.imaginary;
		return new ComplexNumber(real1,imaginary1);
	}
	/**
	 * Dijeli kompleksni broj nad kojim je pozvana funkcija drugim.
	 * 
	 * @param c drugi kompleksni broj
	 * @return novi kompleksni broj koji predstavlja količnik.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double magnitude1=this.getMagnitude()/c.getMagnitude();
		double angle1=this.getAngle()-c.getAngle();
		return fromMagnitudeAndAngle(magnitude1,angle1);
	}
	/**
	 * Potencira kompleksni broj
	 * @param n potencija
	 * @return novi kompleksni broj koji predstavlja <code>pow(this, n)<code>
	 * @throws IllegalArgumentException ako je potencija negativna
	 */
	public ComplexNumber power(int n) {
		if(n<0) {
			throw new IllegalArgumentException();
		}
		double magnitude1=Math.pow(this.getMagnitude(),n);
		double angle1=n*this.getAngle();
		return fromMagnitudeAndAngle(magnitude1,angle1);
	}
	/**
	 * Korjenuje kompleksni broj
	 * @param n korijen
	 * @return novi kompleksni broj koji predstavlja <code>pow(this, 1/n)<code>
	 * @throws IllegalArgumentException ako je potencija manja od 1
	 */
	public ComplexNumber[] root(int n) {
		if(n<1) {
			throw new IllegalArgumentException();
		}
		double magnitude1=Math.pow(this.getMagnitude(),1./n);
		double angle1=this.getAngle()/ n;
		double fraction=(Math.PI*2)/n;
		ComplexNumber[] cArray=new ComplexNumber[n];
		for (int i=0;i<n;i++) {
			cArray[i]=fromMagnitudeAndAngle(magnitude1,angle1+n*fraction);
		}
		return cArray;
	}
	/**
	 * Implementacija metode <code>toString()<code>
	 * @return vraća tekstualni zapis kompleksnog broja
	 */
	@Override
	public String toString() {
		if (imaginary>0) {
			if(real==0)
				return String.valueOf(imaginary)+"i";
			else
				return String.valueOf(real)+"+"+String.valueOf(imaginary)+"i";
		}else if (imaginary==0) {
			if(real>0)
				return String.valueOf(real);
			else 
				return String.valueOf(0);
		}else 
			if(real==0)
				return String.valueOf(imaginary)+"i";
			return String.valueOf(real)+String.valueOf(imaginary)+"i";
	}
}
