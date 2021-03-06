package org.sagittarius.common;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    private static final int MAX_UUID_LENGTH = 8;
    private static final Random random = new Random();

    public static String randomUUID() {
        return UUID.randomUUID().toString().substring(0, MAX_UUID_LENGTH);
    }

    public static final String NUM = "1234567890";
    public static final String LOWER_CASE = "qwertyuiopasdfghjklzxcvbnm";
    public static final String UPPER_CASE = "QWERTYUIOPASDFGHJKLZXCVBNM";
    public static final String NUM_LETTER = NUM + LOWER_CASE + UPPER_CASE;

    /**
     * create random string from library
     */
    public static String randomString(int strLength, String library) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            builder.append(library.charAt(random.nextInt(library.length())));
        }
        return builder.toString();
    }

    private static final int HANZI_UNICODE_STSRT = 19968;
    private static final int HANZI_UNICODE_MAX_NUM = 20902;

    public static String randomHanzi_ALL(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String randomHex = Integer.toHexString(HANZI_UNICODE_STSRT + random.nextInt(HANZI_UNICODE_MAX_NUM));
            builder.append((char) (Integer.parseInt(randomHex, 16)));
        }
        return builder.toString();
    }

    private static String HANZI_3500_PATH = RandomUtil.class.getClassLoader().getResource("hanzi3500.txt").getFile();

    public static String randomHanzi_3500(int length) {
        String resource = IOUtil.readFirstLine(HANZI_3500_PATH);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(resource.charAt((random.nextInt(resource.length()))));
        }
        return builder.toString();
    }

    private static final String COLOR_CODE = "1234567890abcdef";

    public static String randomColor() {
        StringBuilder builder = new StringBuilder("#");

        for (int i = 0; i < 6; i++) {
            builder.append(COLOR_CODE.toCharArray()[random.nextInt(COLOR_CODE.length() - 1)]);
        }

        return builder.toString();
    }

}
