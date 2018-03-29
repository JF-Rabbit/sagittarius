package org.sagittarius.common;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class YamlUtil {

    public static MapChain chain(Object object) {
        return new MapChain(object);
    }

    public static class MapChain {

        private Map<String, Object> chain;

        @SuppressWarnings("unchecked")
        private MapChain(Object object) {
            if (object == null) {
                throw new NullPointerException("Can't instance MapChain by param: null");
            }
            if (object instanceof Map) {
                this.chain = (Map<String, Object>) object;
            } else {
                throw new ClassCastException("Object: [" + object.getClass() + "] cannot be cast to java.util.Map");
            }
        }

        public Object value(String key) {
            return this.chain.get(key);
        }

        public MapChain asMap(String key) {
            return new MapChain(this.chain.get(key));
        }
    }

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
            return new Yaml().loadAs(new FileInputStream(new File(path)), clazz);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
