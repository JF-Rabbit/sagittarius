package org.sagittarius.common.yaml;

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
        public MapChain(Object object) {
            if (object == null) {
                throw new RuntimeException("Can't instance MapChain by param: null");
            }
            try {
                this.chain = (Map<String, Object>) object;
            } catch (ClassCastException e) {
                throw new RuntimeException("Object: [" + object + "] cannot be cast to java.util.Map", e);
            }
        }

        public Object flush(String key) {
            return this.chain.get(key);
        }

        @SuppressWarnings("unchecked")
        public MapChain link(String key) {
            if (this.chain.get(key) == null) {
                throw new RuntimeException("Can't find key: " + key);
            }
            try {
                this.chain = (Map<String, Object>) this.chain.get(key);
            } catch (ClassCastException e) {
                throw new RuntimeException("Object: [" + this.chain.get(key) + "] cannot be cast to java.util.Map", e);
            }
            return this;
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
            T result = new Yaml().loadAs(new FileInputStream(new File(path)), clazz);
            return result;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
