package org.sagittarius.common.test;

import org.sagittarius.common.http.URLUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class TestURLUtil {

    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        String url = URLUtil.builder("http://www.baidu.com/model")
                .setParam("id", 10001)
                .setParam("type", "fieldGroup")
                .setParam("id", "哈哈")
                .build();
        System.out.println(url);
    }
}
