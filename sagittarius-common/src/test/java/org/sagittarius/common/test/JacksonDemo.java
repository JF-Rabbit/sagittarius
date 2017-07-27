package org.sagittarius.common.test;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDemo {

	@Test
	public void test02() throws JsonProcessingException {
		JsonNode jsonNode = getJsonFile(localJsonFilePath + jsonFileName);
		System.out.println(jsonNode);

		System.out.println(jsonNode.findValue("result").get(0));
		
		
	}

	static final String localJsonFilePath = ClassLoader.getSystemResource("").getPath();

	String jsonFileName = "a.json";
	
	public String jsonFommat(JsonNode jsonNode) throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
	}

	public static JsonNode getJsonFile(String jsonFilePath) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = mapper.readTree(new File(jsonFilePath));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rootNode;
	}
}
