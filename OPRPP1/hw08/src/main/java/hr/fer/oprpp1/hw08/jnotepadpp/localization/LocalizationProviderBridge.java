package hr.fer.oprpp1.hw08.jnotepadpp.localization;



public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	boolean connected;
	private ILocalizationProvider provider;
	private ILocalizationListener listener;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider=provider;
		listener=new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
				
			}
			
		};
		connect();
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	
	public void connect() {
		provider.addLocalizationListener(listener);
		connected=true;
		fire();
	}
	
	public void disconnect() {
		provider.removeLocalizationListener(listener);
		connected=false;
		fire();
	}
}
