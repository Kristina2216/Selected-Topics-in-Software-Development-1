package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	private static LocalizationProvider instance=new LocalizationProvider();
	private List<ILocalizationListener> listeners=new ArrayList<ILocalizationListener>();
	private String language;
	private ResourceBundle bundle;
	
	private LocalizationProvider() {
		language="en";
		Locale locale = Locale.forLanguageTag(language);
		bundle=ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", locale);
		
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public void fire() {
		for(ILocalizationListener listener:listeners)
			listener.localizationChanged();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
		
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	public void setLanguage(String language) {
		this.language=language;
		Locale locale = Locale.forLanguageTag(language);
		bundle=ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", locale);
		fire();
	}

}
