package org.sagittarius.common.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.sagittarius.common.map.MapUtil;

public class HttpRequestConfig {

	private HttpMethodEnum requestMethod;
	private String requestUrl;
	private Map<String, String> requestParam;
	private Map<String, String> requestHeaders;
	private String httpEntityJsonStr;
	private HttpClientContext context;

	public HttpRequestConfig() {
		super();
		this.requestParam = new HashMap<>();
		this.requestHeaders = new HashMap<>();
		requestHeaders.put("Content-Type", "application/json;charset=UTF-8");
		this.context = HttpClientContext.create();
		Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider>create()
				.register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider())
				.register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider()).build();
		context.setCookieSpecRegistry(registry);
	}

	public HttpMethodEnum getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(HttpMethodEnum requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Map<String, String> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map<String, String> requestParam) {
		this.requestParam = requestParam;
	}

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public String getHttpEntityJsonStr() {
		return httpEntityJsonStr;
	}

	public void setHttpEntityJsonStr(String httpEntityJsonStr) {
		this.httpEntityJsonStr = httpEntityJsonStr;
	}

	public HttpClientContext getContext() {
		return context;
	}

	public void setContext(HttpClientContext context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "HttpRequestConfig [requestMethod=" + requestMethod + ", requestUrl=" + requestUrl + ", requestParam="
				+ MapUtil.mapToJsonStr(requestParam) + ", requestHeaders=" + MapUtil.mapToJsonStr(requestHeaders)
				+ ", httpEntityJsonStr=" + httpEntityJsonStr + ", context=" + context + "]";
	}

}
