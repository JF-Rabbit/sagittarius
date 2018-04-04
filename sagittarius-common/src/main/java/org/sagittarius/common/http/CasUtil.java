package org.sagittarius.common.http;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author JasonZhang 2018 - 04 - 04 - 16:34
 */
public class CasUtil {

    public static String getAuth(String user, String pwd) throws UnsupportedEncodingException {
        return String.format("Basic %s", new String(Base64.encodeBase64((user + ":" + pwd).getBytes("utf-8"))));
    }
}
