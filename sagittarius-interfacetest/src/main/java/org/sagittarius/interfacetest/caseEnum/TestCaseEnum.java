package org.sagittarius.interfacetest.caseEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.sagittarius.common.http.HttpMethodEnum;
import org.sagittarius.common.http.HttpRequestConfig;
import org.sagittarius.common.map.MapUtil;
import org.sagittarius.common.properties.PropertiesUtil;
import org.sagittarius.interfacetest.ConfigConstant;

public enum TestCaseEnum {

	GET_PROJECTS {
		public HttpRequestConfig getConfig() {
			HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
			httpRequestConfig.setRequestMethod(HttpMethodEnum.GET);
			httpRequestConfig.setRequestUrl(pasUrl + "/pas/services/projects");
			Map<String, String> param = new HashMap<String, String>();
			param.put("size", "1");
			param.put("page", "1");
			httpRequestConfig.setRequestParam(param);
			return httpRequestConfig;
		}
	},

	GET_PROJECTS_SIZE_2_PAGE_1 {
		public HttpRequestConfig getConfig() {
			HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
			httpRequestConfig.setRequestMethod(HttpMethodEnum.GET);
			httpRequestConfig.setRequestUrl(pasUrl + "/pas/services/projects");
			Map<String, String> param = new HashMap<String, String>();
			param.put("size", "2");
			param.put("page", "1");
			httpRequestConfig.setRequestParam(param);
			return httpRequestConfig;
		}
	},

	GET_ANALYSIS_TYPES {
		public HttpRequestConfig getConfig() {
			HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
			httpRequestConfig.setRequestMethod(HttpMethodEnum.GET);
			httpRequestConfig.setRequestUrl(pasUrl + "/pas/services/projects/script-alg/analysis-types");
			Map<String, String> param = new HashMap<String, String>();
			httpRequestConfig.setRequestParam(param);
			return httpRequestConfig;
		}
	},

	POST_PROJECT_INFO {
		public HttpRequestConfig getConfig() {
			HttpRequestConfig httpRequestConfig = new HttpRequestConfig();
			httpRequestConfig.setRequestMethod(HttpMethodEnum.POST);
			httpRequestConfig.setRequestUrl(pasUrl + "/pas/services/projects");
			Map<String, String> param = new HashMap<String, String>();
			param.put("projName", "testqwert");
			param.put("owner", "k2data");
			param.put("creator", "k2data");
			param.put("description", "lijo");
			param.put("iconUrl", "/pas/imgs/projectIcons/sales_forecast.png");
			httpRequestConfig.setHttpEntityJsonStr(MapUtil.mapToJsonStr(param));

			return httpRequestConfig;
		}
	};

	private static final String pasUrl = PropertiesUtil.getSingleValue(ConfigConstant.CONFIG_FILE_PATH,
			ConfigConstant.MODUAL_PAS);

	public abstract HttpRequestConfig getConfig();

	public Object[][] getAllEnum() {
		Object[][] objects = new Object[TestCaseEnum.values().length][1];
		for (int i = 0; i < objects.length; i++) {
			objects[i][0] = TestCaseEnum.values()[i].getConfig();
		}
		return objects;

	}

	public Object[][] getRandomOne() {
		return new Object[][] {
				new Object[] { TestCaseEnum.values()[new Random().nextInt(TestCaseEnum.values().length - 1)] } };
	}

}
