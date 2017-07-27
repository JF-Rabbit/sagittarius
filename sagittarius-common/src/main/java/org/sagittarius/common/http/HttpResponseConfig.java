package org.sagittarius.common.http;

import org.sagittarius.common.gson.GsonUtil;

public class HttpResponseConfig {

	private int statusCode;
	private String content;
	private long responseTime;

	public HttpResponseConfig() {
		super();
	}

	public HttpResponseConfig(int statusCode, String content, long responseTime) {
		super();
		this.statusCode = statusCode;
		this.content = content;
		this.responseTime = responseTime;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "HttpResponseConfig [statusCode=" + statusCode + ", content=" + GsonUtil.jsonStrFormat(content) + ", responseTime=" + responseTime + "]";
	}

}
