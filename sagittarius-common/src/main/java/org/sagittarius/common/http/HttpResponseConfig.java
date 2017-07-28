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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + statusCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpResponseConfig other = (HttpResponseConfig) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (statusCode != other.statusCode)
			return false;
		return true;
	}
	
	

}
