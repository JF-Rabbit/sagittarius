package org.sagittarius.common.http;

import org.sagittarius.common.ReflectUnit;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author JasonZhang 2018 - 04 - 03 - 14:52
 */
public class HttpRequestResolver {

    public static HttpResponseConfig resolve(Object classInstance, String methodName, Class<?>... parameterTypes) throws HttpException {
        Method method = ReflectUnit.getMethod(classInstance.getClass(), methodName, parameterTypes);
        if (method.isAnnotationPresent(HttpRequest.class)) {
            HttpRequestConfig requestConfig = new HttpRequestConfig();

            HttpRequest annotation = method.getAnnotation(HttpRequest.class);

            requestConfig.setRequestMethod(annotation.method());
            if (!annotation.url().equals("")) {
                requestConfig.setRequestUrl(annotation.url());
            }
            if (!annotation.param().equals("")) {
                Object obj = ReflectUnit.reflectCall(classInstance, annotation.param());
                requestConfig.setRequestHeaders((Map<String, String>) obj);
            }

            return HttpUtil.service(requestConfig);
        } else {
            throw new RuntimeException("");
        }
    }
}
