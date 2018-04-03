package org.sagittarius.interfacetest.demo;

import org.sagittarius.common.MapUtil;
import org.sagittarius.common.http.*;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author JasonZhang 2018 - 04 - 03 - 14:46
 */
public class CaseDemo {

    private static final String URL = "http://192.169.2.19:8280/services?cCate=%E5%B9%B3%E5%8F%B0%E6%9C%8D%E5%8A%A1";

    private Map do_something_param() {
        return MapUtil.builder().put("cookie", "abc123").build();
    }

    @HttpRequest(method = HttpMethodEnum.GET, url = URL, param = "do_something_param")
    public void do_something() {
    }

    @Test
    public void test() throws HttpException {
        HttpResponseConfig httpResponseConfig = HttpRequestResolver.resolve(this, "do_something");
        System.out.println(httpResponseConfig);
    }
}
