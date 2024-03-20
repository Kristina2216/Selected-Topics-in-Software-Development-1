package hr.fer.oprpp1.custom.collections;
/**
 * Implementacija sučelja tester
 * 
 */

class EvenIntegerTester<T> implements Tester<T> {
		/**
		 * Provjerava je li predani parametar paran broj
		 * @param obj testirani element
		 */
		 public boolean test(T obj) {
			 if(!(obj instanceof Integer)) return false;
			 Integer i = (Integer)obj;
			 return i % 2 == 0;
		 }
		/* public static void main(String[] args) {
				LinkedListIndexedCollection<Integer> c1=new LinkedListIndexedCollection<Integer>();
				c1.add(3);
				c1.add(6);
				c1.add(9);
				c1.add(10);
				LinkedListIndexedCollection<Number> c2=new LinkedListIndexedCollection<Number>();
				c2.addAllSatisfying(c1, new EvenIntegerTester<Number>());
				c2.forEach(System.out::println);
			}*/
}
