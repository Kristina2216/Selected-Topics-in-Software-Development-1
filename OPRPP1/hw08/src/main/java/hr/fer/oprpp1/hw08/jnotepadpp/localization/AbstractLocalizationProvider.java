package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements  ILocalizationProvider {
		private List<ILocalizationListener> listeners=new ArrayList<ILocalizationListener>();
	
		public void addLocalizationListener(ILocalizationListener l) {
			listeners.add(l);
		}
		public void removeLocalizationListener(ILocalizationListener l) {
			listeners.remove(l);
		}
		public void fire() {
			for(ILocalizationListener l:listeners) {
				l.localizationChanged();
			}
		}
}
