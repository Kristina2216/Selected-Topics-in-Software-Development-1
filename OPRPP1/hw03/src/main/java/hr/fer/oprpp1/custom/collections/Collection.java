package hr.fer.oprpp1.custom.collections;


import java.util.NoSuchElementException;

public interface Collection<T> {
	/**
	 * Provjerava ima li elemenata u kolekciji
	 * 
	 * @return <code>true<code> ako je kolekcija prazna
	 */
	public default boolean isEmpty() {
		return (this.size()==0);
	}
	/**
	 * Vraća veličinu kolekcije
	 * @return <code>int<code> broj elemenata u kolekciji
	 */
	public abstract int size();
	/**
	 *  Dodaje element na kraj kolekcije.
	 *  
	 *  @param value vrijednost koju dodaje u kolekciju.
	 *  @throws NullPointerException  ako se pokuša dodati
	 *  null object.
	 */
	public abstract void add(T value);
	/**
	 *  Provjerava sadrži li kolekcija dani objekt.
	 *  
	 *	@param value tražena vrijednost
	 *	@return Vraća <code>true<code> ako je vrijednost pronađena,
	 *	odnosno <code>false<code> u suprotnom.
	 */
	public abstract boolean contains(Object value);
	/**
	 *  Briše prvo ponavljanje elementa  value iz kolekcije.
	 *  
	 *  @param value vrijednost koju je potrebno izbrisati iz kolekcije
	 *	@return Vraća <code>true<code> ako je element pronađen i obrisan.
	 */
	public abstract boolean remove(Object value);
	/**
	 *  Vraća polje svih elemenata pohranjenih u
	 * 	kolekciji.
	 *  
	 *	@return Vraća novo polje koje sadrži sve elemente kolekcije.
	 */
	public abstract Object[] toArray();
	/**
	 *  Iterira kroz listu pohranjenih elemenata te za svaki
	 *  poziva funkciju <code>processor.process()<code>
	 *  
	 *  @param processor lokalna implementacija klase procesor koji zove metodu
	 *  add za svaku vrijednost.
	 */
	
	public default <U> void forEach(Processor<U> processor) {
		ElementsGetter<U> g=this.createElementsGetter();
		while(true) {
			try {
				processor.process(g.getNextElement());
			}catch(NoSuchElementException e) {
				return;
			}
		}
	}
	/**
	 * Metoda dodaje sve elemente jedne kolekcije u drugu.
	 *
	 * @param other kolekcija čiji se elementi kopiraju
	 */
	
	public default  void addAll(Collection<? extends T> other) {
		class LocalProcessor<U> implements Processor<T>{
			/**
			 * Za svaku vrijednost zove metode <code>add<code>
			 * @param value element kolekcije
			 */
			@Override
			@SuppressWarnings("unchecked")
			public void process(T value) {
				add(value);
			}
		}
		LocalProcessor<T> p=new LocalProcessor<T>();
		other.forEach(p);
		
	}
	/**
	 *  Briše sve elemente kolekcije.
	 *  
	 */
	public abstract void clear();
	/**
	 * Stvara objekt <code>ElementsGetter<code>
	 */
	public abstract ElementsGetter createElementsGetter();
	/**
	 * Metoda redom testira elemente predane kolekcije te
	 * pozivajuću puni samo onima koji prolaze test
	 * 
	 * @param col kolekcija čije elemente testiramo
	 * @param tester koji ispituje elemente
	 */
	public default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<T> g=col.createElementsGetter();
		T o;
		while(true) {
			try {
			o=g.getNextElement();
			}catch(NoSuchElementException e) {
				return;
			}
			if(tester.test(o)) {
				this.add(o);
			}
		}
	}

}
