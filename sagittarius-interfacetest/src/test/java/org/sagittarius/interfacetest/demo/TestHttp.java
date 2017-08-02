package org.sagittarius.interfacetest.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.sagittarius.common.gson.GsonUtil;
import org.sagittarius.common.http.HttpException;
import org.sagittarius.common.http.HttpMethodEnum;
import org.sagittarius.common.http.HttpRequestConfig;
import org.sagittarius.common.http.HttpResponseConfig;
import org.sagittarius.common.http.HttpUtil;
import org.sagittarius.common.jsoncompare.JsonCompareRecorder;
import org.sagittarius.common.jsoncompare.JsonDiff;
import org.sagittarius.common.jsoncompare.JsonDiffErrorCode;
import org.sagittarius.common.jsoncompare.RuleEnum;
import org.sagittarius.common.map.MapUtil;
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

	// @Test(dataProvider = "dataProvider1") // 测试发送所有枚举类中的数据
	public void test1(HttpRequestConfig httpRequestConfig) throws HttpException {
		logger.info("httpRequestConfig:\t{}", httpRequestConfig);
		HttpResponseConfig httpResponseConfig = HttpUtil.service(httpRequestConfig);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig);
	}

	@DataProvider
	public Object[][] dataProvider2(ITestContext context) {
		Object[][] objects = new Object[][] { new Object[] { TestCaseEnum.GET_ANALYSIS_TYPES.getConfig(),
				TestCaseEnum.GET_PROJECTS_SIZE_2_PAGE_1.getConfig() }, };

		return objects;
	}

	// @Test(dataProvider = "dataProvider2") // 测试获取JSessionId
	public void test2(HttpRequestConfig res1, HttpRequestConfig res2) throws HttpException {
		System.out.println(res1);
		BasicClientCookie cookie1 = new BasicClientCookie("aaa", UUID.randomUUID().toString());
		BasicClientCookie cookie2 = new BasicClientCookie("aaa", UUID.randomUUID().toString());
		List<BasicClientCookie> list = new ArrayList<>();
		list.add(cookie1);
		list.add(cookie2);

		res1.getContext().setCookieStore(HttpUtil.buildCookieStore(list));
		HttpResponseConfig httpResponseConfig1 = HttpUtil.service(res1);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig1);
		logger.info("JSESSIONID:\t{}", HttpUtil.getJSessionID(httpResponseConfig1));
	}

	// @Test // 测试过滤条件
	public void test3() throws HttpException {
		HttpResponseConfig httpResponseConfig1 = new HttpResponseConfig();
		JsonObject jsonObject1 = GsonUtil.getJsonObjFromJsonFile("/Users/jasonzhang/Desktop/a.json");
		httpResponseConfig1.setResponseStatusCode(100);
		httpResponseConfig1.setResponseContent(GsonUtil.jsonObjToStr(jsonObject1));
		logger.info("httpResponseConfig:\t{}", httpResponseConfig1);
		HttpResponseConfig httpResponseConfig2 = new HttpResponseConfig();
		httpResponseConfig2.setResponseStatusCode(200);
		JsonObject jsonObject2 = GsonUtil.getJsonObjFromJsonFile("/Users/jasonzhang/Desktop/b.json");
		httpResponseConfig2.setResponseContent(GsonUtil.jsonObjToStr(jsonObject2));
		logger.info("httpResponseConfig:\t{}", httpResponseConfig2);

		JsonCompareRecorder recorder = new JsonCompareRecorder();
		recorder.setOption(RuleEnum.IGNORE_RESPONSE_CODE);
		// recorder.setOption(JsonDiffErrorCode.JSON_ARRAY_SIZE_MISS_MATCH,
		// "root-obj[2]-array[result]");
		recorder.setOption("result", RuleEnum.CHECK_ARRAY_NO1);
		recorder.setOption("no", RuleEnum.IS_ANY_INTEGER);
		recorder.setOption(new JsonDiff(JsonDiffErrorCode.VALUE_MISS_MATCH, "root-obj[1][message]"));
		recorder.setOption(
				new JsonDiff(JsonDiffErrorCode.VALUE_MISS_MATCH, "root-obj[2]-array[result][0]-obj[2][status]"));

		recorder.compare(httpResponseConfig1, httpResponseConfig2);
		logger.info("recorder:{}" + recorder);
	}

	@DataProvider
	public Object[][] dataProvider3(ITestContext context) {
		Object[][] objects = new Object[][] { new Object[] { TestCaseEnum.POST_PROJECT_INFO.getConfig() } };

		return objects;
	}

	// @Test(dataProvider = "dataProvider3") // 测试Post Entity
	public void test4(HttpRequestConfig res2) throws HttpException {
		res2.getRequestHeaders().put("Cookie", "JSESSIONID=5B18673DDCF1A8F884A09613B7B9117C");

		logger.info("httpResponseConfig:\t{}", res2);

		HttpResponseConfig httpResponseConfig1 = HttpUtil.service(res2);

		logger.info("httpResponseConfig:\t{}", httpResponseConfig1);

		HttpResponseConfig expect = new HttpResponseConfig();
		expect.setResponseStatusCode(201);
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("message", "");
		map.put("result", "%IGNORE_VALUE%");
		expect.setResponseContent(MapUtil.mapToJsonStr(map));

		JsonCompareRecorder recorder = new JsonCompareRecorder();
		recorder.compare(expect, httpResponseConfig1);
		logger.info("recorder:{}" + recorder);
	}

	@Test
	public void test5() throws Exception {
		HttpRequestConfig request = new HttpRequestConfig();

		request.setRequestMethod(HttpMethodEnum.POST);
		request.setRequestUrl(
				"https://192.168.130.101:8443/cas/login?service=http://192.168.130.101:5004/cas/validate&sn="
						+ UUID.randomUUID());

		Map<String, String> param = new HashMap<String, String>();
		param.put("username", "k2data");
		param.put("password", "K2Data@k001");
		request.setHttpEntityJsonStr(MapUtil.mapToJsonStr(param));

		HttpResponseConfig httpResponseConfig = HttpUtil.service(request);
		logger.info("httpResponseConfig:\t{}", httpResponseConfig);

		logger.info("JSESSIONID:\t{}", HttpUtil.getJSessionID(httpResponseConfig));
	}
	
	 

}
