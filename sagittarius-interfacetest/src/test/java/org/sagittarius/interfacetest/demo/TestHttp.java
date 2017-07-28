package org.sagittarius.interfacetest.demo;

import java.util.HashMap;
import java.util.Map;

import org.sagittarius.common.gson.GsonUtil;
import org.sagittarius.common.http.HttpException;
import org.sagittarius.common.http.HttpRequsetConfig;
import org.sagittarius.common.http.HttpResponseConfig;
import org.sagittarius.common.http.HttpUtil;
import org.sagittarius.common.jsoncompare.IgnoreRuleEnum;
import org.sagittarius.common.jsoncompare.JsonCompareRecorder;
import org.sagittarius.interfacetest.caseEnum.TestCaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

@ContextConfiguration(locations = "classpath:spring.xml")
public class TestHttp extends AbstractTestNGSpringContextTests {

	private static final Logger logger = LoggerFactory.getLogger(TestHttp.class);

	@DataProvider
	public Object[][] dataProvider1(ITestContext context) {
		return TestCaseEnum.GET_PROJECTS.getAllEnum();
	}

	// @Test(dataProvider = "dataProvider1")
	public void test1(HttpRequsetConfig httpRequsetConfig) throws HttpException {
		logger.info("httpRequsetConfig:\t{}", httpRequsetConfig);
		HttpResponseConfig httpResponseConfig = HttpUtil.service(httpRequsetConfig);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig);
	}

	@DataProvider
	public Object[][] dataProvider2(ITestContext context) {
		Object[][] objects = new Object[][] { new Object[] { 
				TestCaseEnum.GET_ANALYSIS_TYPES.getConfig(),
				TestCaseEnum.GET_PROJECTS_SIZE_2_PAGE_1.getConfig() }, };

		return objects;
	}

	@Test(dataProvider = "dataProvider2")
	public void test2(HttpRequsetConfig res1, HttpRequsetConfig res2) throws HttpException {
		HttpResponseConfig httpResponseConfig1 = HttpUtil.service(res1);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig1);

		HttpResponseConfig httpResponseConfig2 = new HttpResponseConfig();
		httpResponseConfig2.setStatusCode(200);
		JsonObject jsonObject = GsonUtil.getJsonObjFromJsonFile("C:/Users/kzdatd/Desktop/a.json");
		httpResponseConfig2.setContent(GsonUtil.jsonObjToStr(jsonObject));
		logger.info("httpResponseConfig:\t{}", httpResponseConfig2);

		JsonCompareRecorder recorder = new JsonCompareRecorder();
		Map<String, IgnoreRuleEnum> map = new HashMap<>();
		recorder.compare(httpResponseConfig2, httpResponseConfig1, map);
		System.out.println(recorder);
	}

}
