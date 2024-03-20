package hr.fer.oprpp1.custom.collections;

public class Collection {
	
	protected Collection() {
		
	}
	/**
	 * Provjerava ima li elemenata u kolekciji
	 * 
	 * @return <code>true<code> ako je kolekcija prazna
	 */
	public boolean isEmpty() {
		return (this.size()==0);
	}
	/**
	 * Vraća veličinu kolekcije
	 * @return <code>int<code> broj elemenata u kolekciji
	 */
	public int size() {
		return 0;
	}
	/**
	 *  Dodaje element na kraj kolekcije.
	 *  
	 *  @param value vrijednost koju dodaje u kolekciju.
	 *  @throws NullPointerException  ako se pokuša dodati
	 *  null object.
	 */
	public void add(Object value) {
	}
	/**
	 *  Provjerava sadrži li kolekcija dani objekt.
	 *  
	 *	@param value tražena vrijednost
	 *	@return Vraća <code>true<code> ako je vrijednost pronađena,
	 *	odnosno <code>false<code> u suprotnom.
	 */
	public boolean contains(Object value) {
		return false;
	}
	/**
	 *  Briše prvo ponavljanje elementa  value iz kolekcije.
	 *  
	 *  @param value vrijednost koju je potrebno izbrisati iz kolekcije
	 *	@return Vraća <code>true<code> ako je element pronađen i obrisan.
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 *  Vraća polje svih elemenata pohranjenih u
	 * 	kolekciji.
	 *  
	 *	@return Vraća novo polje koje sadrži sve elemente kolekcije.
	 */
	public Object[] toArray() {
		 throw new UnsupportedOperationException() ;
	}
	/**
	 *  Iterira kroz listu pohranjenih elemenata te za svaki
	 *  poziva funkciju <code>processor.process()<code>
	 *  
	 *  @param processor lokalna implementacija klase procesor koji zove metodu
	 *  add za svaku vrijednost.
	 */
	public void forEach(Processor processor) {
	}
	/**
	 * Metoda dodaje sve elemente jedne kolekcije u drugu.
	 *
	 * @param other kolekcija čiji se elementi kopiraju
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor{
			/**
			 * Za svaku vrijednost zove metode <code>add<code>
			 * @param value element kolekcije
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		LocalProcessor p=new LocalProcessor();
		other.forEach(p);
		
	}
	/**
	 *  Briše sve elemente kolekcije.
	 *  
	 */
	public void clear() {
	}

}
