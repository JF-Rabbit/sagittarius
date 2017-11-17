package org.sagittarius.common.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties load(String path) {

		InputStream inStream = null;
		try {
			inStream = new FileInputStream(path);
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String getSingleValue(String path, String key) {
		return load(path).getProperty(key);
	}
	
	public static int getInt(Properties properties, String key){
		return Integer.valueOf(properties.getProperty(key));
	}

}
