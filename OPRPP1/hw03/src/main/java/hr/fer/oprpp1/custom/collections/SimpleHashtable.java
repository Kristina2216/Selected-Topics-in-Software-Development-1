package hr.fer.oprpp1.custom.collections;
/**
 * Razred je implementacija tablice
 * raspršenog adresiranja
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	TableEntry<K,V>[] heads;
	int size;
	private int modificationCount;
	/**
	 * Konstruktor bez argumenata
	 * stvara polje referenci veličine 16 na prve čvorove
	 * pojedinih slotova tablice
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		heads=(TableEntry<K,V>[])new Object[16];
		modificationCount=0;
		size=16;
	}
	/**
	 * Stvara polje referenci veličine capacity na prve čvorove
	 * pojedinih slotova tablice
	 * @param capacity broj slotova tablice
	 */
	public SimpleHashtable(int capacity) {
		int p=0;
		boolean add=true;
		while(capacity>1) {
			if (capacity==2)
				add=false;
			capacity=capacity/2;
			p++;
		}
		if(add) {
			p=p+1;
		}
		size=(int)Math.pow(2, p);
		heads=( TableEntry<K,V>[]) new TableEntry[size];
		modificationCount=0;
	}
	
	public static class TableEntry<K,V>{
		private K key;
		private V value;
		TableEntry next;
		/**
		 * Stvara novi čvor unutar slota
		 * @param key ključ zapisa
		 * @param value vrijednost zapisa
		 */
		private TableEntry(K key, V value) {
			this.key=key;
			this.value=value;
		}
		/**
		 * Vraća ključ zapisa
		 * @return ključ
		 */
		public K getKey() {
			return key;
		}
		/**
		 * Vraća vrijednost zapisa
		 * @return vrijednost
		 */
		public V getValue() {
			return value;
		}
		/**
		 * postavlja vrijednost zapisu
		 * @param value vrijednost
		 */
		public void setValue(V value){
			this.value=value;
		}
	}
	/**
	 * Upisuje novi zapis u slot
	 * @param key ključ, ne smije biti <code>null</code>
	 * @param value vrijednost
	 * @return staru vrijednost ukoliko je element sa predanim
	 * ključem postojao, inače <code>null</code>
	 * @throws NullPointerException
	 */
	public V put(K key, V value) {
		if(key==null)
			throw new NullPointerException();
		int slot=Math.abs(key.hashCode())%size;
		TableEntry<K,V> point=null;
		V ret=null;
		int i=0;
		for(i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			if(i==slot) {
				point=heads[i];
				break;
			}
		}
		if(point==null) {
			point=new TableEntry(key,value);
			heads[slot]=point;
			check();
			modificationCount++;
			return ret;
		}
		while(true) {
			if(point.getKey().equals(key)) {
				ret=point.value;
				point.setValue(value);
				break;
			}
			else {
				if(point.next==null) {
					point.next=new TableEntry(key,value);
					modificationCount++;
					break;
				}
				point=point.next;
			}
		}
		return ret;
	}
	/**
	 * Vraća vrijednost elementa sa zadanim ključem
	 * @param key ključ elementa
	 * @return vrijednost
	 */
	public V get(Object key) {
		int slot;
		if(key!=null)
			slot=Math.abs(key.hashCode())%size;
		else return null;
		TableEntry<K,V> point=null;
		V ret=null;
		int i;
		for(i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			if(i==slot) {
				point=heads[i];
				break;
			}
		}
		if(point==null)
			return ret;
		while(true) {
			if(point.getKey().equals(key)) {
				ret=point.value;
				break;
			}
			else {
				if(point.next==null)
					break;
				point=point.next;
			}
		}
		return ret;
	}
	/**
	 * Vraća ukupan broj svih zapisa 
	 * u svim slotovima
	 * @return broj zapisa
	 */
	public int size() {
		int b=0;
		TableEntry<K,V> point=null;
		for(int i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			point=heads[i];
			while(point!=null) {
				b++;
				point=point.next;
			}
		}
		return b;
	}
	/**
	 * Provjerava postoji li element
	 * sa danim ključem
	 * @param key ključ, ne smije biti <code>null</code>
	 * @return<code>true</code> ako je nađen zapis
	 * sa danim ključem,
	 * inače <code>false</code>
	 * @throws NullPointerException
	 */
	public boolean containsKey(Object key) {
		if(key==null)
			return false;
		int slot=Math.abs(key.hashCode())%size;
		TableEntry<K,V> point=null;
		int i;
		for(i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			if(i==slot) {
				point=heads[i];
				break;
			}
		}
		if(point==null)
			return false;
		while(true) {
			if(point.getKey().equals(key)) {
				return true;
			}
			else {
				if(point.next==null)
					break;
				point=point.next;
			}
		}
		return false;
		
	}
	/**
	 * Provjerava postoji li element
	 * sa danom vrijednošću
	 * @param value vrijednost
	 * @return <code>true</code> ako je nađen zapis
	 * sa danom vrijednošću,
	 * inače <code>false</code>
	 */
	public boolean containsValue(Object value) {
		TableEntry<K,V> point=null;
		int i;
		for(i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			point=heads[i];
			while(point!=null) {
				if(point.getValue()==null) {
					if (value==null)
						return true;
				}else if(point.getValue().equals(value))
					return true;
				point=point.next;
			}
		}
		return false;
	}
	/**
	 * Briše zapis pod danim ključem
	 * @param key ključ, može biti null
	 * @return Vrijednost obrisanog zapisa ukoliko
	 * je nađen, inače <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public V remove(Object key){
		int slot=0;
		if(key!=null) 
			slot=Math.abs(key.hashCode())%size;
		else return null;
		TableEntry<K,V> point=null;
		V ret=null;
		int i;
		for(i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			if(i==slot) {
				point=heads[i];
				if(point.getKey().equals(key)) {
					heads[i]=point.next;
					modificationCount++;
					return point.getValue();
				}
				break;
			}
		}
		if(point==null)
			return ret;
		while(true) {
			if(point.next!=null) {
				if(point.next.getKey().equals(key)) {
					ret=(V)point.next.getValue();
					point.next=point.next.next;
					modificationCount++;
					break;
				}
				else {
					if(point.next.next==null)
						break;
					point=point.next;
				}
			}else break;
		}
		return ret;
	}
	/**
	 * Provjerava postoje li zapisi
	 * @return <code>true</code> ukoliko nema zapisa, 
	 * inače <code>false</code>
	 */
	public boolean isEmpty() {
		return this.size()==0;
	}
	/**
	 * Vraća formatirani oblik za ispis
	 * @return <code>string</code>
	 */
	public String toString() {
		TableEntry<K,V> point=heads[0];
		boolean first=false;
		String s="[";
		for(int i=0;i<size;i++) {
			if(heads[i]==null) {
				if(i!=size-1)
					continue;
				else {
					s=s+"]";
					return s;
				}
			}else if (first)
				s=s+", ";
			point=heads[i];
			while(point!=null) {
				s=s+point.getKey()+"="+point.getValue();
				first=true;
				point=point.next;
				if(point!=null)
					s=s+", ";
			}
		}
		s=s+"]";
		return s;
	}
	/**
	 * Provjerava je li omjer ukupnih
	 * i popunjenih slotova tablice veća od ili
	 * jednaka 0.75
	 */
	private void check() {
		double slot=0;
		for(int i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			slot++;
		}
		if(slot/size>=0.75f) {
			expand();
		}
		return;
	}
	/**
	 * Vraća postojećih zapisa u tablici
	 * @return polje <code>TableEntry[]</code>
	 */
	@SuppressWarnings("unchecked")
	private TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] all=(TableEntry<K,V>[])new TableEntry[size()];
		TableEntry<K,V> point=null;
		int j=0;
		for(int i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			point=heads[i];
			while(point!=null) {
				all[j]=point;
				j++;
				point=point.next;
			}
		}
		return all;
		
	}
	/**
	 * Proširuje postojeću tablicu
	 * te u nju kopira sve postojeće zapise
	 */
	@SuppressWarnings("unchecked")
	private void expand() {
		System.out.println("Expandind from "+size+" to "+size*2);
		TableEntry<K,V>[] saved=toArray();
		clear();
		heads=(TableEntry<K,V>[])new TableEntry[size*2];
		for(TableEntry el: saved) {
			int slot=Math.abs(el.getKey().hashCode())%size;
			TableEntry<K,V> point=null;
			int i=0;
			for(i=0;i<size;i++) {
				if(heads[i]==null)
					continue;
				if(i==slot) {
					point=heads[i];
					break;
				}
			}
			if(point==null) {
				point=el;
				heads[slot]=point;
				continue;
			}
			while(true) {
				if(point.next==null) {
					point.next=el;
					break;
				}
				point=point.next;
				}
		}
	}
	/**
	 * Briše sve postojeće zapise
	 * u tablici
	 */
	public void clear() {
		TableEntry<K,V> point=null;
		TableEntry<K,V> del=null;
		for(int i=0;i<size;i++) {
			if(heads[i]==null)
				continue;
			point=heads[i];
			heads[i]=null;
			while(point!=null) {
				del=point;
				del.next=null;
				point=point.next;
			}
		}
	}
	/**
	 * Tvornička metoda
	 * stvara novi iterator nad pozivajućom tablicom
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		TableEntry<K,V> current;
		int number;
		boolean deleted=false;
		int savedModificationCount;
		
		/**
		 * Konstruktor stvara novi primjerak iteratora
		 */
		private IteratorImpl(){
			number=0;
			current=null;
			savedModificationCount=modificationCount;
		}
		/**
		 * Provjerava li postoj li još zapisa koje 
		 * je moguće vratiti
		 * @throws ConcurrentModificationCount
		 */
		public boolean hasNext() {
			if(savedModificationCount!=modificationCount)
				throw new  ConcurrentModificationException();
			if(number>=size())
				return false;
			return true;
		}
		/**
		 * Vraća idući zapis u tablici
		 * @throws ConcurrentModificationCount
		 * @throws NoSuchElementException
		 */
		public SimpleHashtable.TableEntry<K,V> next(){
			if(savedModificationCount!=modificationCount)
				throw new  ConcurrentModificationException();
			if(deleted==true) {
				deleted=false;
				return current;
			}
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			if(current==null) {
				int i=0;
				for(;i<size;i++) {
					if(heads[i]==null)
						continue;
					current=heads[i];
					number++;
					return current;
				}
			}
			if(current.next!=null)
				current=current.next;
			else {
				int slot=(Math.abs(current.getKey().hashCode())%size)+1;
				for(int i=slot;i<size;i++) {
					if(heads[i]==null)
						continue;
					current=heads[i];
					break;
				}
			}
			number++;
			return current;
		}
		/**
		 * Briše trenutni zapis iz tablice
		 * @throws ConcurrentModificationCount
		 */
		public void remove() {
			if(savedModificationCount!=modificationCount)
				throw new  ConcurrentModificationException();
			if(deleted==true)
				throw new IllegalStateException();
			TableEntry del=current;
			if(hasNext()) {
				next();
				deleted=true;
			}else
				current=null;
			SimpleHashtable.this.remove(del.getKey());
			number--;
			savedModificationCount++;
		}
	}


	public static void main(String[] args) {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(3);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        for(TableEntry<String, Integer> el: testTable) {
        	System.out.println(el.getKey()+" "+el.getValue());
        }
	}
}
