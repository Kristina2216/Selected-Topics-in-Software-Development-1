package hr.fer.oprpp1.custom.collections;
/**
 * Razred predstavlja implementaciju stoga,
 * a za internu pohranu koristi polje.
 */

public class ObjectStack {
	private ArrayIndexedCollection array;
	public ObjectStack() {
		array=new ArrayIndexedCollection();
	}
	/**
	 * Vraća broj elemenata na stogu
	 * 
	 * @return <code> true<code> ako je stog prazan 
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	/**
	 *  Vraća veličinu kolekcije.
	 *	
	 *	@return broj elemenata pohranjenih u listi elements[].
	 */
	public int size() {
		return array.size();
	}
	/**
	 * Dodaje element na vrh stoga
	 * 
	 * 
	 * @param value vrijednost koju dodaje
	 */
	public void push(Object value) {
		array.add(value);
	}
	/**
	 * Skida element sa vrha stoga
	 * 
	 * @return vraća <code>object<code> s vrha stoga
	 */
	public Object pop() {
		if(array.size()==0) {
			throw new  EmptyStackException();
		}
		Object r=array.toArray()[(array.size()-1)];
		array.remove(array.size()-1);
		return r;
	}
	/**
	 * Gleda element sa vrha stoga
	 * 
	 * @return vraća <code>object<code> s vrha stoga
	 */
	public Object peek() {
		if(array.size()==0) {
			throw new  EmptyStackException();
		}
		Object r=array.toArray()[(array.size()-1)];
		return r;
	}
	/**
	 * Skida sve elemente sa stoga
	 */
	public void clear() {
		array.clear();
	}
}
