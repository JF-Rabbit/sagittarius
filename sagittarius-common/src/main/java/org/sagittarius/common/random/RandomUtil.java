package org.sagittarius.common.random;

import java.util.UUID;

public class RandomUtil {

	private static final int MAX_UUID_LENGTH = 8;

	public static String randomUUID() {
		return UUID.randomUUID().toString().substring(0, MAX_UUID_LENGTH);
	}

}
