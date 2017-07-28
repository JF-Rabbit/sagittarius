package org.sagittarius.interfacetest.caseEnum;

import java.util.HashMap;
import java.util.Map;

import org.sagittarius.common.http.HttpMethodEnum;
import org.sagittarius.common.http.HttpRequsetConfig;
import org.sagittarius.common.properties.PropertiesUtil;
import org.sagittarius.interfacetest.ConfigConstant;

public enum TestCaseEnum {

	GET_PROJECTS {
		public HttpRequsetConfig getConfig() {
			HttpRequsetConfig httpRequsetConfig = new HttpRequsetConfig();
			httpRequsetConfig.setHttpMethod(HttpMethodEnum.GET);
			httpRequsetConfig.setUrl(pasUrl + "/pas/services/projects");
			Map<String, String> param = new HashMap<String, String>();
			param.put("size", "1");
			param.put("page", "1");
			httpRequsetConfig.setParam(param);
			return httpRequsetConfig;
		}
	},
	
	GET_PROJECTS_SIZE_2_PAGE_1 {
		public HttpRequsetConfig getConfig() {
			HttpRequsetConfig httpRequsetConfig = new HttpRequsetConfig();
			httpRequsetConfig.setHttpMethod(HttpMethodEnum.GET);
			httpRequsetConfig.setUrl(pasUrl + "/pas/services/projects");
			Map<String, String> param = new HashMap<String, String>();
			param.put("size", "2");
			param.put("page", "1");
			httpRequsetConfig.setParam(param);
			return httpRequsetConfig;
		}
	},

	GET_ANALYSIS_TYPES {
		public HttpRequsetConfig getConfig() {
			HttpRequsetConfig httpRequsetConfig = new HttpRequsetConfig();
			httpRequsetConfig.setHttpMethod(HttpMethodEnum.GET);
			httpRequsetConfig.setUrl(pasUrl + "/pas/services/projects/script-alg/analysis-types");
			Map<String, String> param = new HashMap<String, String>();
			httpRequsetConfig.setParam(param);
			return httpRequsetConfig;
		}
	};

	private static final String pasUrl = PropertiesUtil.getSingleValue(ConfigConstant.CONFIG_FILE_PATH, ConfigConstant.MODUAL_PAS);

	public abstract HttpRequsetConfig getConfig();

	public Object[][] getAllEnum() {
		Object[][] objects = new Object[TestCaseEnum.values().length][1];
		for (int i = 0; i < objects.length; i++) {
			objects[i][0] = TestCaseEnum.values()[i].getConfig();
		}
		return objects;

	}

}
