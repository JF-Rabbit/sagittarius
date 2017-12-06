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
                e.printStackTrace();
            }
        }
    }

    public static String getString(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            throw new RuntimeException("Can't load properties key: " + key + ", null or trim length is 0!");
        }
        return value;
    }

    public static double getDouble(Properties properties, String key) {
        return Double.valueOf(getString(properties, key));
    }

    public static int getInt(Properties properties, String key) {
        return Integer.valueOf(getString(properties, key));
    }

    public static boolean getBoolean(Properties properties, String key) {
        return Boolean.valueOf(getString(properties, key));
    }

    public enum ResultTypeEnum {
        STRING, INT, BOOLEAN, DOUBLE
    }

    public static Object getValue(Properties properties, String key, ResultTypeEnum type) {
        switch (type) {
            case STRING:
                return getString(properties, key);
            case INT:
                return getInt(properties, key);
            case BOOLEAN:
                return getBoolean(properties, key);
            case DOUBLE:
                return getDouble(properties, key);
            default:
                return getString(properties, key);
        }

    }

}
