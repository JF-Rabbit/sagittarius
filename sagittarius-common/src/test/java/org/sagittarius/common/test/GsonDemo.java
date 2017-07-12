package org.sagittarius.common.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;

public class GsonDemo {

	static final String localJsonFilePath = ClassLoader.getSystemResource("").getPath();

	String jsonFileName1 = "a.json";
	String jsonFileName2 = "b.json";

	@Test
	public void test01() {
		JsonObject object1 = getJsonObjFromJsonFile(localJsonFilePath + jsonFileName1);
		JsonObject object2 = getJsonObjFromJsonFile(localJsonFilePath + jsonFileName2);
		try {
			JsonAssert.setOptions(Option.TREATING_NULL_AS_ABSENT);
			JsonAssert.assertJsonEquals(object1, object2);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}
	
	public String jsonFommat(JsonObject jsonObject) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
	}

	public JsonObject getJsonObjFromJsonFile(String jsonFilePath) {
		BufferedReader reader = null;
		JsonObject jsonObj = null;
		try {
			reader = new BufferedReader(new FileReader(new File(jsonFilePath)));
			jsonObj = new Gson().fromJson(reader, JsonObject.class);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonObj;
	}

}
