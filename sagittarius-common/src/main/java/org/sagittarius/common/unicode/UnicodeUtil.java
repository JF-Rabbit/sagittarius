package org.sagittarius.common.unicode;

import org.apache.commons.lang3.StringUtils;

public class UnicodeUtil {

	public static String unicode2String(String unicode) {
		if (StringUtils.isBlank(unicode)) {
			return null;
		}

		StringBuffer builder = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);
			builder.append((char) data);
		}

		return builder.toString();
	}

	public static String string2Unicode(String string) {
		if (StringUtils.isBlank(string)) {
			return null;
		}

		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			unicode.append("\\u" + Integer.toHexString(string.charAt(i)));
		}

		return unicode.toString();
	}

}
