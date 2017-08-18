package org.sagittarius.common.map;

import java.lang.reflect.Field;
import java.util.Map;

import org.sagittarius.common.reflect.ReflectUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MapUtil {

	private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

	public static <T> String mapToJsonStr(Map<String, T> map) {
		return new Gson().toJson(map);
	}

	public static void debug(Map<?, ?> map) {
		map.forEach((k, v) -> {
			logger.info("key:{}, value:{}", k, v);
		});
	}

	/**
	 * Map映射成对象， 不匹配的字段默认为null
	 * 
	 * @param clazz
	 *            实例类(必须有无参构造器)
	 * @param map
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

}
