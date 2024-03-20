package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

public interface ElementsGetter {
	public abstract boolean hasNextElement();
	public abstract Object getNextElement();
	public default void processRemaining(Processor p) {
		Object o;
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
