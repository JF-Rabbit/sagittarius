package org.sagittarius.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MapUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

    public static <T> String map2JsonStr(Map<String, T> map) {
        return new Gson().toJson(map);
    }

    public static void debug(Map<?, ?> map) {
        map.forEach((k, v) -> logger.info("key:{}, value:{}", k, v));
    }

    /**
     * Map映射成对象， 不匹配的字段默认为null
     *
     * @param clazz 实例类(必须有无参构造器)
     * @param map   map
     * @return T 实例对象
     */
    public static <T> T map2Obj(Class<T> clazz, Map<String, Object> map) {
        T t = ReflectUnit.newInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            if (map.containsKey(field.getName())) {
                field.setAccessible(true);
                ReflectUnit.setField(field, t, map.get(field.getName()));
            }
        }
        return t;
    }

    public static <T, K> MapBuilder<T, K> builder() {
        return new MapBuilder<>();
    }

    public static <T, K> MapBuilder<T, K> builder(MapEnum mapEnum) {
        return new MapBuilder<>(mapEnum);
    }

    public enum MapEnum {
        HASH_MAP, TREE_MAP, LINKED_HASH_MAP
    }

    public static class MapBuilder<T, K> {

        private Map<T, K> map;

        private MapBuilder() {
            map = new HashMap<>();
        }

        private MapBuilder(MapEnum mapEnum) {
            switch (mapEnum) {
                case HASH_MAP:
                    map = new HashMap<>();
                    break;
                case TREE_MAP:
                    map = new TreeMap<>();
                    break;
                case LINKED_HASH_MAP:
                    map = new LinkedHashMap<>();
                    break;
            }
        }

        public MapBuilder<T, K> put(T t, K k) {
            this.map.put(t, k);
            return this;
        }

        public Map<T, K> build() {
            return map;
        }
    }
}
