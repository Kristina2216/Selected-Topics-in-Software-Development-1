package hr.fer.oprpp1.custom.collections;
/**
 * Sučelje procesor
 * definira jednu apstraktnu metodu process
 */
public interface Processor<T> {
	public abstract void process(T value);
}
