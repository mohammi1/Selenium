package chat.automation.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import chat.automation.framework.exceptions.FrameworkExceptions;
public class Environment {
	private static Properties properties = null;
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("Environment.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String property) {
		try {
			return properties.getProperty(property).trim();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
			return null;
		}

	}
	
	public static void setProperty(String key, String value) {
		try {
			properties.setProperty(key, value);
	        saveProperties();
		} catch (Exception exception) {
			new FrameworkExceptions(exception);
			
		}
	}
	private static void saveProperties() {
        try {
        	FileOutputStream outputStream = new FileOutputStream("Environment.properties");
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
