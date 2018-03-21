package org.sagittarius.common.unicode;

import org.apache.commons.lang3.StringUtils;

public class UnicodeUtil {

    public static String unicode2String(String unicode) {
        if (StringUtils.isBlank(unicode)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
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

        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            unicode.append("\\u" + Integer.toHexString(string.charAt(i)));
        }

        return unicode.toString();
    }

}
