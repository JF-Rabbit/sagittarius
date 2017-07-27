package org.sagittarius.interfacetest.demo;

import java.util.HashMap;
import java.util.Map;

import org.sagittarius.common.http.HttpException;
import org.sagittarius.common.http.HttpMethodEnum;
import org.sagittarius.common.http.HttpRequsetConfig;
import org.sagittarius.common.http.HttpResponseConfig;
import org.sagittarius.common.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = "classpath:spring.xml")
public class TestHttp extends AbstractTestNGSpringContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(TestHttp.class);
	
	@Test
	public void test() throws HttpException {
		HttpRequsetConfig httpRequsetConfig = new HttpRequsetConfig();
		httpRequsetConfig.setHttpMethod(HttpMethodEnum.GET);
		httpRequsetConfig.setUrl("http://192.168.130.83:28085/pas/services/projects/script-alg/analysis-types");
		Map<String, String> param = new HashMap<String, String>();
		// param.put("key1", "1");
		// param.put("key2", "qwe");
		httpRequsetConfig.setParam(param);
		logger.info("httpRequsetConfig:\t{}", httpRequsetConfig);
		HttpResponseConfig httpResponseConfig = HttpUtil.service(httpRequsetConfig);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig);
	}

}
