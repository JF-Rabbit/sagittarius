package org.sagittarius.common.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties load(String path) {

		InputStream inStream = null;
		Properties properties = null;
		try {
			inStream = new FileInputStream(path);
			properties = new Properties();
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (properties != null) {
					properties.load(inStream);
				}
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

}
