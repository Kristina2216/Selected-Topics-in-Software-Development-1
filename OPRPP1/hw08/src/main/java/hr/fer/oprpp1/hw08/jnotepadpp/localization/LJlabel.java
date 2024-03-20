package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.JLabel;

public class LJlabel extends JLabel{
	public long SerialVersionUID;
	String key;
	public ILocalizationProvider lp;
	public ILocalizationListener listener;
	
	public LJlabel(String key, ILocalizationProvider lp) {
		this.key=key;
		this.lp=lp;
		listener=new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				changeValue();
				
			}
			
		};
		lp.addLocalizationListener(listener);
		changeValue();
	}
	public void changeValue() {
		this.setText(lp.getString(key));
	}
}
