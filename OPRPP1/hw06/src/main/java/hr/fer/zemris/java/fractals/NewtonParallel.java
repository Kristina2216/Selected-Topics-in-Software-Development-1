package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * klasa koja upravlja crtanjem Newton-Raphsonove iteracije
 * pomoću više dretvi
 */
public class NewtonParallel {
	/**
	 * Čita korijene kompleksnog broja dok korisnik ne upiše "done",
	 * potom poziva crtanje
	 * @param args broj radnika i traka
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
			+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		Runtime run = Runtime.getRuntime();
		String worker;
		String track;
		if(args.length==0 || (args.length==1 && args[0].contains("t"))) {
			worker=String.valueOf(run.availableProcessors());
		}else {
			worker=args[0].replaceAll("[^0-9]", "");
			if(Integer.parseInt(worker)>run.availableProcessors()) {
				worker=String.valueOf(run.availableProcessors());
			}
		}
		if(args.length==0 || (args.length==1 && args[0].contains("w"))) {
			track=String.valueOf(run.availableProcessors()*4);
		}else 
			track=args[1].replaceAll("[^0-9]", "");
		int workerNumber=Integer.parseInt(worker);
		int trackNumber=Integer.parseInt(track);
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
			FractalViewer.show(new MojProducer(new ComplexRootedPolynomial(Complex.ONE,create),workerNumber,trackNumber));
		}	
	
	}
	/**
	 * predstavlja posao koji odrađuje jedna dretva
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial rooted;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		/**
		 * @param reMin lijevi kut prozora na realnoj osi
		 * @param reMax desni kut prozora na realnoj osi
		 * @param imMin donji kut prozora na imaginarnoj osi
		 * @param imMax gornji kut prozora na imaginarnoj osi
		 * @param width širina slike
		 * @param height visina slike
		 * @param yMin red za koji počinje ispitivanje
		 * @param yMax red za koji završava ispitivanje
		 * @param m maksimalan broj iteracija
		 * @param data polje koje popunjava vrijednostima iteracije
		 * @param cancel
		 * @param rooted kompleksni polinom čije nultočke promatramo
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rooted) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.rooted=rooted;
		}
		/**
		 * fukncija koja se pokreće kad se pokrene dretva
		 */
		@Override
		public void run() {
			
			NewtonRaphsonCalculate.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, rooted, cancel);
			
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
		int workerNumber;
		int trackNumber;
		/**
		 * 
		 * @param p kompleksni polinom čije nultočke promatramo
		 * @param workerNumber broj dretvi
		 * @param trackNumber broj poslova
		 */
		public MojProducer(ComplexRootedPolynomial p, int workerNumber, int trackNumber) {
			this.p=p;
			this.workerNumber=workerNumber;
			this.trackNumber=trackNumber;
		}
		/**
		 * metoda stvara zadan broj dretvi te im računa indekse koje ispituju pa
		 * ih čeka te kad sve završe prenosi rezultate klasi 
		 * <code>IFractalResultObserver<code>
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka;
			if(trackNumber<height)
				brojTraka= trackNumber;
			else
				brojTraka=height-1;
			int brojYPoTraci = height / brojTraka;
			System.out.println("Number of effective threads: "+workerNumber+"\n"+"Number of tracks: "+brojTraka);
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workerNumber];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci+1;
				int yMax = (i+1)*brojYPoTraci;
				if(i==brojTraka-1) {
					yMax = height;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, p);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(p.toComplexPolynom().order()+1), requestNo);
		}
	}
	
}
