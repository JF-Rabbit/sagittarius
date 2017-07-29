package org.sagittarius.interfacetest.demo;

import org.apache.commons.lang.StringUtils;
import org.sagittarius.common.gson.GsonUtil;
import org.sagittarius.common.http.HttpException;
import org.sagittarius.common.http.HttpRequsetConfig;
import org.sagittarius.common.http.HttpResponseConfig;
import org.sagittarius.common.http.HttpUtil;
import org.sagittarius.common.jsoncompare.JsonCompareRecorder;
import org.sagittarius.common.jsoncompare.JsonDiff;
import org.sagittarius.common.jsoncompare.JsonDiffErrorCode;
import org.sagittarius.common.jsoncompare.RuleEnum;
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
		Object[][] objects = new Object[][] {
				new Object[] { TestCaseEnum.GET_ANALYSIS_TYPES.getConfig(), TestCaseEnum.GET_PROJECTS_SIZE_2_PAGE_1.getConfig() }, };

		return objects;
	}

	// @Test(dataProvider = "dataProvider2")
	public void test2(HttpRequsetConfig res1, HttpRequsetConfig res2) throws HttpException {
		HttpResponseConfig httpResponseConfig1 = HttpUtil.service(res1);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig1);

		HttpResponseConfig httpResponseConfig2 = new HttpResponseConfig();
		httpResponseConfig2.setStatusCode(200);
		JsonObject jsonObject = GsonUtil.getJsonObjFromJsonFile("C:/Users/kzdatd/Desktop/a.json");
		httpResponseConfig2.setContent(GsonUtil.jsonObjToStr(jsonObject));
		logger.info("httpResponseConfig:\t{}", httpResponseConfig2);

		JsonCompareRecorder recorder = new JsonCompareRecorder();
		
		recorder.compare(httpResponseConfig2, httpResponseConfig1);
		System.out.println(recorder);
	}

	@Test
	public void test3() throws HttpException {
		HttpResponseConfig httpResponseConfig1 = new HttpResponseConfig();
		JsonObject jsonObject1 = GsonUtil.getJsonObjFromJsonFile("/Users/jasonzhang/Desktop/a.json");
		httpResponseConfig1.setStatusCode(100);
		httpResponseConfig1.setContent(GsonUtil.jsonObjToStr(jsonObject1));
		logger.info("httpResponseConfig:\t{}", httpResponseConfig1);
		HttpResponseConfig httpResponseConfig2 = new HttpResponseConfig();
		httpResponseConfig2.setStatusCode(200);
		JsonObject jsonObject2 = GsonUtil.getJsonObjFromJsonFile("/Users/jasonzhang/Desktop/b.json");
		httpResponseConfig2.setContent(GsonUtil.jsonObjToStr(jsonObject2));
		logger.info("httpResponseConfig:\t{}", httpResponseConfig2);

		JsonCompareRecorder recorder = new JsonCompareRecorder();
		recorder.setOption(RuleEnum.IGNORE_RESPONSE_CODE);
		//recorder.setOption(JsonDiffErrorCode.JSON_ARRAY_SIZE_MISS_MATCH, "root-obj[2]-array[result]");
		recorder.setOption("result", RuleEnum.CHECK_ARRAY_NO1);
		recorder.setOption("no", RuleEnum.IS_ANY_INTEGER);
		recorder.setOption(new JsonDiff(JsonDiffErrorCode.VALUE_MISS_MATCH, "root-obj[1][message]"));
		recorder.setOption(new JsonDiff(JsonDiffErrorCode.VALUE_MISS_MATCH, "root-obj[2]-array[result][0]-obj[2][status]"));
		
		recorder.compare(httpResponseConfig1, httpResponseConfig2);
		System.out.println(recorder);
	}
	
	//@Test
	public void test4() {
		System.out.println(StringUtils.isNumeric("109.8098"));
		System.out.println(StringUtils.isNumericSpace("1098.098"));
	}
	

}
