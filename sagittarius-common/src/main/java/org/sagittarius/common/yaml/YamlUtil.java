package org.sagittarius.common.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.yaml.snakeyaml.Yaml;

public class YamlUtil {

	public static Object load(String path) {
		try {
			return new Yaml().load(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Iterable<Object> loadAll(String path) {
		try {
			return new Yaml().loadAll(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T loadAs(String path, Class<T> clazz) {
		try {
			T result = new Yaml().loadAs(new FileInputStream(new File(path)), clazz);
			return result;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
