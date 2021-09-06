package rozaryonov.delivery.services;

import java.util.ResourceBundle;
import java.util.Locale;

public enum MessageManager {
	EN(ResourceBundle.getBundle("resources.msg", new Locale("en", "US"))),
	RU(ResourceBundle.getBundle("resources.msg", new Locale("ru", "RU")));
	private ResourceBundle bundle;
	
	MessageManager(ResourceBundle bundle) {
		this.bundle=bundle;
	}
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	public ResourceBundle getBundle() {
		return this.bundle;
	}

}
