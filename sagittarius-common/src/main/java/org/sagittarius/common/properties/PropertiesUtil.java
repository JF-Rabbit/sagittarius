package org.sagittarius.common.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties load(String path) throws IOException {
		InputStream inStream = new FileInputStream(path);
		Properties properties = new Properties();
		properties.load(inStream);
		inStream.close();
		return properties;
	}

}
