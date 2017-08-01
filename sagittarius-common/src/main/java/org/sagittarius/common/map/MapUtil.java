package org.sagittarius.common.map;

import java.util.Map;

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
	
}
