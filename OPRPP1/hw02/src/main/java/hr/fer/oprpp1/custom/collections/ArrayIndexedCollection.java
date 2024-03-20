package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * Razred predstavlja implementaciju razreda kolekcije za pohranu
 * objekata. Za unutarnju pohranu služi se strukturama polja te
 * elemente dohvaća indeksiranjem.
 * 
 * @author Kristina Petković
 * 
 */

public class ArrayIndexedCollection implements List{
	private int size;
	private Object[] elements;
	private long modificationCount;
	
	/**
	 *  Konstruktor stvara novo prazno polje velicine
	 *	16.
	 *
	 *
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 *  Konstruktor stvara novo polje velicine
	 *	initalCapacity argumenta.
	 *
	 *	@param initialCapacity početni kapacitet
	 *	@throws IllegalArgumentException ako je dan kapacitet<1
	 *
	 */
	public ArrayIndexedCollection(int  initialCapacity) {
		if(initialCapacity<1) {
			throw new IllegalArgumentException("Kapacitet mora biti veći od nula!");
		}
		this.elements=new Object[initialCapacity];
		this.size=0;
		this.modificationCount=0;
	}
	/**
	 *  Konstruktor stvara novo polje u koje kopira sve elemente
	 *  iz druge kolekcije. Kapacitet postavlja na 16 ako je veličina druge kolekcije
	 *  manja od 16, inače na njenu veličinu. 
	 *
	 *	@throws IllegalArgumentException ako je kapacitet dane kolekcije>16.
	 *
	 */
	
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}
	/**
	 *  Konstruktor stvara novo polje u koje kopira sve elemente
	 *  iz druge kolekcije. Kapacitet je postavljen na initalCapacity 
	 *  argument ako je on veće od veliline druge kolekcije, inače je
	 *  jednak veličini druge..
	 *
	 *	@param other kolekcija čiji se elementi kopiraju.
	 *	@param initialCapacity početni kapacitet
	 *	@throws IllegalArgumentException ako je kapacitet dane kolekcije>initialCapacity.
	 *
	 */
	public ArrayIndexedCollection(Collection other,int initialCapacity) {
		if(other==null) {
			throw new NullPointerException();
		}
		if(initialCapacity<other.size()) {
			initialCapacity=other.size();
		}
		this.elements=new Object[initialCapacity];
		this.addAll(other);
		this.size=other.size();
		this.modificationCount=0;
	}
	/**
	 *  Vraća veličinu kolekcije.
	 *	
	 *	@return broj elemenata pohranjenih u listi elements[].
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 *  Provjerava sadrži li kolekcija dani objekt.
	 *  
	 *	@param value tražena vrijednost
	 *	@return Vraća <code>true<code> ako je vrijednost pronađena,
	 *	odnosno <code>false<code> u suprotnom.
	 */
	@Override
	public boolean contains(Object value) {
		for(int i=0;i<size();i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 *  Briše prvo ponavljanje elementa  value iz kolekcije.
	 *  
	 *  @param value vrijednost koju je potrebno izbrisati iz kolekcije
	 *	@return Vraća <code>true<code> ako je element pronađen i obrisan.
	 */
	@Override
	public boolean remove(Object value) {
		int position=this.indexOf(value);
		if(position==-1) 
			return false;
		this.remove(position);
		return true;
	}
	/**
	 *  Vraća polje svih elemenata pohranjenih u
	 * 	kolekciji.
	 *  
	 *	@return Vraća novo polje koje sadrži sve elemente kolekcije.
	 */
	@Override 
	public Object[] toArray() {
		return (Arrays.copyOf(elements, size));
	}
	/**
	 *  Briše sve elemente kolekcije.
	 *  
	 */
	@Override
	public void clear() {
		int i=0;
		for(Object element:elements) {
			elements[i]=null;
			i++;
		}
		size=0;
		modificationCount++;
	}
	/**
	 *  Dodaje element u kolekciju na zadanu 
	 *  poziciju.
	 *  
	 *  @param value vrijednost elementa koji se dodaje.
	 *  @param position pozicija na koju umećemo element.
	 *  @throws IndexOutOfBoundsException ako je pozicija
	 *  izvan intervala [0,size].
	 */
	public void insert(Object value, int position) {
		if(position <0 | position>size) {
			throw new IndexOutOfBoundsException();
		}
		for(int i=position, b=size;i<=b;i++) {
			try {
				Object tmp=elements[i];
				size=i;
				this.add(value);
				value=tmp;
				if(value==null) break;
			}catch(IndexOutOfBoundsException ex) {
				size=i;
				this.add(value);
			}
		}
	}
	/**
	 *  Dodaje element na kraj kolekcije.
	 *  
	 *  @param value vrijednost koju dodaje u kolekciju.
	 *  @throws NullPointerException  ako se pokuša dodati
	 *  null object.
	 */
	public void add(Object value) {
		if(value==null) {
			throw new NullPointerException();
		}
		try {
			elements[size]=value;
			size++;
		}catch(IndexOutOfBoundsException ex){
			elements=Arrays.copyOf(elements, size*2);
			elements[size]=value;
			size++;
			modificationCount++;
		}
	}
	/**
	 *  Vraća element na danoj poziciji.
	 *  
	 *  @param index pozicija vraćanog elementa
	 *  @return element na poziciji index.
	 *  @throws IndexOutOfBoundsException ako se pokuša
	 *  dohvatiti element na poziciji izvan [0,size-1].
	 */
	public Object get(int index) {
		if(index>=size | index<0) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	/**
	 *  Vraća poziciju elementa.
	 *  
	 *  @param value traženi element.
	 *  @return Pozicija traženog elementa, inače -1.
	 */
	public int indexOf(Object value) {
		for(int i=0;i<size;i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 *  Briše element na danoj poziciji.
	 *  
	 *  @param index indeks elementa koji se briše.
	 *  @throws IndexOutOfBoundsException ako se pokuša
	 *  obrisati element na poziciji izvan [0,size-1]
	 */
	public void remove(int index) {
		if(index <0 | index>size-1) {
			throw new IndexOutOfBoundsException();
		}
		int i;
		for(i=index;i<size-1;i++) {
			elements[i]=elements[i+1];
		}
		elements[i]=null;
		size--;
		modificationCount++;
	}
	private static class ArrayElementsGetter implements ElementsGetter{
		private int current;
		private ArrayIndexedCollection c;
		private long savedModificationCount;
		
		private ArrayElementsGetter(ArrayIndexedCollection col) {
			current=0;
			c=col;
			savedModificationCount=col.modificationCount;
		}
		public boolean hasNextElement() {
			if(savedModificationCount!=c.modificationCount) {
				throw new  ConcurrentModificationException();
			}
			try {
				Object o=c.elements[current];
			}catch(IndexOutOfBoundsException e) {
				return false;
			}
			return true;
		}
		public Object getNextElement() {
			if(savedModificationCount!=c.modificationCount) {
				throw new  ConcurrentModificationException();
			}
			Object o;
			try {
				o=c.elements[current];
			}catch(IndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
			if(o==null) {
				throw new NoSuchElementException();
			}
			current++;
			return o;
		}
	}
	public ElementsGetter createElementsGetter() {
		return new ArrayElementsGetter(this);
	}
	public static void main(String[] args) {
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");
		Collection col3 = col1;
		Collection col4 = col2;
		col1.get(0);
		col2.get(0);
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); 
		}

}

