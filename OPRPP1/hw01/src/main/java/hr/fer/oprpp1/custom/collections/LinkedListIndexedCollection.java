package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
/**
 * Razred predstavlja implementaciju razreda kolekcije za pohranu
 * objekata. Za unutarnju pohranu služi se pokazivačima na prethodni
 * i sljedeći element.
 * 
 * 
 */
public class LinkedListIndexedCollection extends Collection{
	private ListNode first;
	private ListNode last;
	private int size;
	/**
	 *  Konstruktor stvara novu praznu kolekciju.
	 *
	 */
	public LinkedListIndexedCollection() {
		size=0;
	}
	/**
	 *  Konstruktor stvara novu kolekciju čije elemente
	 *  kopira od predane.
	 *
	 *	@param Collection kolekcija čiji se elementi kopiraju.
	 *	@throws NullPointerException ako je predana kolekcija prazna.
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other==null) {
			throw new NullPointerException();
		}
		this.addAll(other);
		size=other.size();
	}
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;
		private ListNode(Object value) {
			this.value=value;
		}
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
		ListNode n=first;
		for(int i=0;i<size;i++) {
			if (n.value.equals(value)) {
				return true;
			}
			n=n.next;
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
		Object[] array=new Object[size];
		ListNode n=first;
		for(int i=0;i<size;i++) {
			array[i]=n.value;
			n=n.next;
		}
		return array;
	}
	/**
	 *  Dodaje element na kraj kolekcije.
	 *  
	 *  @param value vrijednost koju dodaje u kolekciju.
	 *  @throws NullPointerException  ako se pokuša dodati
	 *  null object.
	 */
	@Override
	public void add(Object value) {
		if(value==null) {
			throw new NullPointerException();
		}
		ListNode novi=new ListNode(value);
		novi.next=null;
		if(size==0) {
			first=novi;
			novi.previous=null;
		}else {
			novi.previous=last;
			last.next=novi;
		}
		last=novi;
		size++;
	}
	/**
	 *  Iterira kroz listu pohranjenih elemenata te za svaki
	 *  poziva funkciju <code>processor.process()<code>
	 *  
	 *  @param processor lokalna implementacija klase procesor koji zove metodu
	 *  add za svaku vrijednost.
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode n=first;
		for(int i=0;i<size;i++) {
			processor.process(n.value);
			n=n.next;
		}
	}
	/**
	 *  Vraća element na zadanoj poziciji.
	 *  
	 *  @param index pozicija vraćenog elementa
	 *  @return element na poziciji index.
	 *  @throws IndexOutOfBoundsException ako se pokuša
	 *  dohvatiti element na poziciji izvan [0,size-1].
	 */
	public Object get(int index) {
		if(index>=size | index<0) {
			throw new IndexOutOfBoundsException();
		}
		int i;
		ListNode n;
		if(index<=size/2) {
			n=first;
			for(i=0;i<index;i++) {
				n=n.next;
			}
		}else{
			n=last;
			for(i=size;i>index+1;i--) {
				n=n.previous;
			}
		}
		return n.value;
	}
	/**
	 *  Briše sve elemente kolekcije.
	 *  
	 */
	public void clear() {
		ListNode n=first;
		ListNode del;
		for(int i=0;i<size-1;i++) {
			del=n;
			n=n.next;
			n.previous=null;
			del=null;
		}
		first=last=null;
		size=0;
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
		ListNode tmp=first;
		for(int i=0;i<position-1;i++) {
			tmp=tmp.next;
		}
		last=tmp;
		int s=size;
		size=position;
		if(position>0) {
			tmp=tmp.next;
		}
		this.add(value);
		for(int i=position,b=s;i<b;i++) {
			last.next=tmp;
			tmp.previous=last;
			last=tmp;
			tmp=tmp.next;
		}
		size=s+1;
	}
	/**
	 *  Vraća poziciju elementa.
	 *  
	 *  @param value traženi element.
	 *  @return Pozicija traženog elementa, inače <code>-1<code>.
	 */
	public int indexOf(Object value) {
		ListNode tmp=first;
		for(int i=0;i<size;i++) {
			if(tmp.value.equals(value))
				return i;
			tmp=tmp.next;
		}
		return -1;
	}
	/**
	 *  Briše element na zadanoj poziciji.
	 *  
	 *  @param index indeks elementa koji se briše.
	 *  @throws IndexOutOfBoundsException ako se pokuša 
	 *  obrisati element na poziciji izvan <code>[0,size-1]<code>.
	 */
	public void remove(int index) {
		if(index <0 | index>size-1) {
			throw new IndexOutOfBoundsException();
		}
		int i;
		ListNode tmp=first;
		for(i=0;i<index;i++) {
			tmp=tmp.next;
		}
		ListNode n=tmp;
		n=tmp;
		if(size()==1) {
			tmp=null;
			first=last=tmp;
		}else if(index==0) {
			tmp=tmp.next;
			tmp.previous=null;
			first=tmp;
		}else if(index==size-1) {
			n.previous.next=null;
			last=n;
		}else {
			n.previous.next=n.next;
			tmp=tmp.next;
			tmp.previous=n.previous;
			n=null;
		}
		size--;
	}
}

