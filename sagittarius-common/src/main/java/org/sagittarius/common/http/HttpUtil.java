package org.sagittarius.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

// http://hc.apache.org/httpcomponents-client-ga/examples.html
public class HttpUtil {

    public static HttpResponseConfig service(HttpRequestConfig httpRequestConfig) throws HttpException {
        // TODO Authorization 预留
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpResponseConfig httpResponseConfig = new HttpResponseConfig();
        try {

            URIBuilder builder = new URIBuilder(httpRequestConfig.getRequestUrl());

            HttpRequestBase httpRequest = null;

            switch (httpRequestConfig.getRequestMethod()) {
                case GET:
                    builder = setParam(httpRequestConfig.getRequestParam(), builder);
                    httpRequest = new HttpGet(builder.build());
                    break;
                case POST:
                    HttpPost httpPost = new HttpPost(builder.build());
                    httpPost.setEntity(new StringEntity(httpRequestConfig.getHttpEntityJsonStr(), "UTF-8"));
                    httpRequest = httpPost;
                    break;
                case DELETE:
                    httpRequest = new HttpDelete(builder.build());
                    // TODO
                    break;
                case PUT:
                    httpRequest = new HttpPut(builder.build());
                    // TODO
                    break;
            }

            httpRequest.setHeaders(setHeaders(httpRequestConfig.getRequestHeaders()));

            long start = System.currentTimeMillis();
            HttpResponse httpResponse = closeableHttpClient.execute(httpRequest, httpRequestConfig.getContext());
            long end = System.currentTimeMillis();
            httpResponseConfig.setResponseTime(end - start);

            httpResponseConfig.setResponseStatusCode(httpResponse.getStatusLine().getStatusCode());

            httpResponseConfig.setResponseHeaders(setHeadersMap(httpResponse.getAllHeaders()));

            if (httpResponse.getEntity() != null) {
                httpResponseConfig.setResponseContent(getContent(httpResponse.getEntity().getContent()));
            }

        } catch (Exception e) {
            throw new HttpException(e);
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpResponseConfig;
    }

    // http://blog.csdn.net/qq32933432/article/details/51813256
    // http://www.cnblogs.com/yaowen/p/3757571.html

    public static String JSESSIONID = "JSESSIONID";

    public static BasicClientCookie getJSessionID(HttpResponseConfig httpResponseConfig) {
        Map<String, String> responseHeaders = httpResponseConfig.getResponseHeaders();
        String setCookie = responseHeaders.get("Set-Cookie");
        String jSessionId = setCookie.substring((JSESSIONID + "=").length(), setCookie.indexOf(";"));
        BasicClientCookie cookie = new BasicClientCookie(JSESSIONID, jSessionId);
        // XXX set cookie properties
        return cookie;
    }

    public static BasicCookieStore buildCookieStore(BasicClientCookie cookie) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    public static BasicCookieStore buildCookieStore(List<BasicClientCookie> cookies) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        cookies.forEach(arg -> {
            cookieStore.addCookie(arg);
        });
        return cookieStore;
    }

    private static URIBuilder setParam(Map<String, String> param, URIBuilder builder) {
        param.forEach((k, v) -> {
            builder.addParameter(k, v);
        });
        return builder;
    }

    private static Header[] setHeaders(Map<String, String> headersMap) {
        List<BasicHeader> headerList = new ArrayList<>();
        headersMap.forEach((k, v) -> {
            headerList.add(new BasicHeader(k, v));
        });

        Header[] headers = new Header[headerList.size()];
        for (int i = 0; i < headerList.size(); i++) {
            headers[i] = headerList.get(i);
        }
        return headers;
    }

    private static Map<String, String> setHeadersMap(Header[] headersArray) {
        Map<String, String> map = new HashMap<>();
        for (Header header : headersArray) {
            map.put(header.getName(), header.getValue());
        }
        return map;
    }

    private static String getContent(InputStream stream) throws IOException {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

}
