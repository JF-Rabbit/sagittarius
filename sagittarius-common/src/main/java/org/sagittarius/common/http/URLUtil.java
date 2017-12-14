package org.sagittarius.common.http;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

/**
 * 将对象或Map设置到Get请求的URL当中
 *
 * @author JasonZhang
 */
public class URLUtil {

    public static String setURL(String url, Object paramEntiy) throws Exception {
        URIBuilder builder = new URIBuilder(url);
        Field[] fields = paramEntiy.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(paramEntiy) != null) {
                builder.addParameter(field.getName(), String.valueOf(field.get(paramEntiy)));
            }
        }
        return builder.build().toURL().toString();
    }

    public static String setURL(String url, Map<String, Object> paramEntiy) throws Exception {
        URIBuilder builder = new URIBuilder(url);

        paramEntiy.forEach((k, v) -> builder.addParameter(k, String.valueOf(v)));

        return builder.build().toURL().toString();
    }

}
