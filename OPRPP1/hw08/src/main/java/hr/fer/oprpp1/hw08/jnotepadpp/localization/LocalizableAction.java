package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	public long SerialVersionUID;
	public String key;
	public String description;
	public ILocalizationProvider lp;
	public ILocalizationListener listener;
	
	public LocalizableAction(String key, String description, ILocalizationProvider lp) {
		this.key=key;
		this.lp=lp;
		this.description=description;
		listener=new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				changeValue();
				
			}
			
		};
		lp.addLocalizationListener(listener);
		changeValue();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public void changeValue() {
		LocalizableAction.this.putValue(NAME, lp.getString(key));
		if(description!=null)
			LocalizableAction.this.putValue(SHORT_DESCRIPTION, lp.getString(description));
	}
}
