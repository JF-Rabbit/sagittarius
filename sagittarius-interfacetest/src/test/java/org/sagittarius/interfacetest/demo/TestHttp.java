package org.sagittarius.interfacetest.demo;

import org.sagittarius.common.http.HttpException;
import org.sagittarius.common.http.HttpRequsetConfig;
import org.sagittarius.common.http.HttpResponseConfig;
import org.sagittarius.common.http.HttpUtil;
import org.sagittarius.interfacetest.caseEnum.TestCaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ContextConfiguration(locations = "classpath:spring.xml")
public class TestHttp extends AbstractTestNGSpringContextTests {

	private static final Logger logger = LoggerFactory.getLogger(TestHttp.class);

	@DataProvider
	public Object[][] dataProvider1(ITestContext context) {
		return TestCaseEnum.GET_PROJECTS.getAllEnum();
	}
	
	@Test(dataProvider = "dataProvider1")
	public void test(HttpRequsetConfig httpRequsetConfig) throws HttpException {
		//HttpRequsetConfig httpRequsetConfig = TestCaseEnum.GET_ANALYSIS_TYPES.getConfig();
		logger.info("httpRequsetConfig:\t{}", httpRequsetConfig);
		HttpResponseConfig httpResponseConfig = HttpUtil.service(httpRequsetConfig);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig);
	}
	


}
