package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja implementaciju
 * mape<K,V>
 *
 * @param <K> ključ
 * @param <V> vrijednost
 */
public class Dictionary<K,V> {
	private ArrayIndexedCollection<Pair> array;
	
	public Dictionary(){
		array=new ArrayIndexedCollection<Pair>();
	}
	
	private class Pair{
		private K key;
		private V value;
		
		private Pair(K key, V value) {
			if(key==null)
				throw new NullPointerException();
			this.key=key;
			this.value=value;
		}
	}
	/**
	 * Provjerava postoje li zapisi
	 * @return <code>true</code> ukoliko nema zapisa, 
	 * inače <code>false</code>
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	/**
	 * Vraća ukupan broj parova
	 * @return broj parova
	 */
	public int size() {
		return array.size();
	}
	/**
	 * Briše sve postojeće zapise
	 * u rječniku
	 */
	public void clear() {
		array.clear();
	}
	/**
	 * Upisuje novi par u rječnik
	 * @param key ključ, ne smije biti <code>null</code>
	 * @param value vrijednost
	 * @return staru vrijednost ukoliko je par sa predanim
	 * ključem postojao, inače <code>null</code>
	 * @throws NullPointerException
	 */
	public V put(K key, V value) {
		if(key==null)
			throw new NullPointerException();
		int i;
		V ret=null;
		for(i=0;i<this.size();i++) {
			if ((array.get(i).key).equals(key)) {
				ret=array.get(i).value;
				array.get(i).value=value;
			}
		}
		if(ret==null) {
			Pair par=new Pair(key, value);
			array.add(par);
		}
		return ret;
	}
	/**
	 * Vraća vrijednost para sa zadanim ključem
	 * @param key ključ para, smije biti <code>null</code>
	 * @return vrijednost
	 */
	public V get(Object key) {
		int i;
		V ret=null;
		for(i=0;i<this.size();i++) {
			if ((array.get(i).key).equals(key)) 
				ret=array.get(i).value;
		}
		return ret;
	}
	/**
	 * Briše par pod danim ključem
	 * @param key ključ, može biti null
	 * @return Vrijednost obrisanog para ukoliko
	 * je nađen, inače <code>null</code>
	 */
	public V remove(K key) {
		int i;
		V ret=null;
		for(i=0;i<this.size();i++) {
			if ((array.get(i).key).equals(key)) { 
				ret=array.get(i).value;
				array.remove(i);
				break;
			}
		}
		return ret;
	}
	public static void main(String[] args) {
		Dictionary<Integer, String> dic=new Dictionary<Integer, String>();
		dic.put(6, "Majka");
		dic.put(3, "value");
		System.out.println(dic.size());
		dic.remove(2);
		dic.remove(3);
		System.out.println(dic.isEmpty());
		dic.clear();
		System.out.println(dic.size());
		System.out.println(dic.isEmpty());
	}

}
