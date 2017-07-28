package org.sagittarius.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtil {

	public static HttpResponseConfig service(HttpRequsetConfig httpRequsetConfig) throws HttpException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpResponseConfig httpResponseConfig = new HttpResponseConfig();
		try {

			URIBuilder builder = setParam(httpRequsetConfig.getParam(), new URIBuilder(httpRequsetConfig.getUrl()));

			HttpRequestBase httpRequest = null;

			switch (httpRequsetConfig.getHttpMethod()) {
			case GET:
				httpRequest = new HttpGet(builder.build());
				break;
			case POST:
				httpRequest = new HttpPost(builder.build());
				break;
			case DELETE:
				httpRequest = new HttpDelete(builder.build());
				break;
			case PUT:
				httpRequest = new HttpPut(builder.build());
				break;
			}

			long start = System.currentTimeMillis();
			HttpResponse httpResponse = closeableHttpClient.execute(httpRequest);
			long end = System.currentTimeMillis();
			httpResponseConfig.setResponseTime(end - start);
			
			httpResponseConfig.setStatusCode(httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getEntity() != null) {
				httpResponseConfig.setContent(getContent(httpResponse.getEntity().getContent()));
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

	private static URIBuilder setParam(Map<String, String> param, URIBuilder builder) {
		param.forEach((k, v) -> {
			builder.setParameter(k, v);
		});
		return builder;
	}

	private static String getContent(InputStream stream) throws IOException {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			throw e;
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
