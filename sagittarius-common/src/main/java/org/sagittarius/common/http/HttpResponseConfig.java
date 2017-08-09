package org.sagittarius.common.http;

import java.util.Map;

import org.sagittarius.common.gson.GsonUtil;

public class HttpResponseConfig {

	private int responseStatusCode;
	private String responseContent;
	private long responseTime;
	private Map<String, String> responseHeaders;

	public HttpResponseConfig() {
		super();
	}

	public int getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(int responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String format() {
		return "HttpResponseConfig [responseStatusCode=" + responseStatusCode + ", responseContent="
				+ GsonUtil.jsonStrFormat(responseContent) + ", responseTime=" + responseTime + ", responseHeaders="
				+ responseHeaders + "]";
	}

	@Override
	public String toString() {
		return "HttpResponseConfig [responseStatusCode=" + responseStatusCode + ", responseContent=" + responseContent
				+ ", responseTime=" + responseTime + ", responseHeaders=" + responseHeaders + "]";
	}

}
