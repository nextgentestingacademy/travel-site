package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties prop;
	
	public static void loadProperties() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
			prop.load(fis);

			String env = prop.getProperty("env").trim().toLowerCase();
			FileInputStream envFis = new FileInputStream("src/test/resources/"+env+".properties");
			prop.load(envFis);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if(prop == null) {
			loadProperties();
		}
		return prop.getProperty(key);
	}
}