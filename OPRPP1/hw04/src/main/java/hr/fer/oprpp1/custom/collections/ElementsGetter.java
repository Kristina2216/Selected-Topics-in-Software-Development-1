package hr.fer.oprpp1.custom.collections;
/**
 * Sučelje ElementsGetter iterira kroz
 * kolekciju te vraća elemente jedan po jedan
 */
import java.util.NoSuchElementException;

public interface ElementsGetter<T> {
	/**
	 * Provjerava postoji li u kolekciji još elemenata
	 * za vraćanje
	 * @return <code>true</code> ako ih ima još,
	 * inače <code>false</code>
	 */
	public abstract boolean hasNextElement();
	/**
	 * Dohvaća sljedeći element kolekcije
	 * @return sljedeći element
	 * @throws NoSuchElementException ako ih nema više
	 */
	public abstract T getNextElement();
	/**
	 * Za svaki preostali element u kolekciji
	 * poziva metodu <code>processor.process()</code>
	 * @param p procesor nad kolekcijom čije
	 * elemente se obilazi
	 */
	public default void processRemaining(Processor<T> p) {
		T o;
		while(true) {
			try {
				o= this.getNextElement();
				p.process(o);
			}catch(NoSuchElementException e) {
				break;
			}
		}
	}
}
