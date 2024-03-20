package hr.fer.oprpp1.custom.collections;
/**
 * Sučelje list
 * nasljeđuje sučelje kolekcija i
 * definira 4 nove metode
 * @param <T>
 */
public interface List<T> extends Collection<T> {
	T get(int index);
	void insert(T value, int position);
	int indexOf(Object value);
	void remove(int index);
}
