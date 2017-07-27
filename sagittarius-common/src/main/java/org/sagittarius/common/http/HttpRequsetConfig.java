package org.sagittarius.common.http;

import java.util.Map;

import org.sagittarius.common.map.MapUtil;

public class HttpRequsetConfig {

	private HttpMethodEnum httpMethod;
	private String url;
	private Map<String, String> param;

	public HttpRequsetConfig() {
		super();
	}

	public HttpRequsetConfig(HttpMethodEnum httpMethod, String url, Map<String, String> param) {
		super();
		this.httpMethod = httpMethod;
		this.url = url;
		this.param = param;
	}

	public HttpMethodEnum getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethodEnum httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "HttpResponseConfig [httpMethod=" + httpMethod + ", url=" + url + ", param=" + MapUtil.mapToJson(param) + "]";
	}
}
