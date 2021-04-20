package org.encheres.dal;

import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static Properties properties;

	static {
		try {
			properties = new Properties();
			properties.load(Settings.class.getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperties(String key) {
		return properties.getProperty(key, null);
	}

}
