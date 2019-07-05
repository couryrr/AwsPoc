package com.dapper.cloud.function;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dapper.cloud.function.JsonHandler;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonHandlerTest {
	
	@Test
	public void toJsonFromStringHappyPathTest(){
		JsonNode data = JsonHandler.toJson("{\"Test\": true}");
		assertEquals("There should be a test key", true, data.get("Test").asBoolean());
		
	}
	
	@Test
	public void toJsonFromStringBadBodyTest(){
		JsonNode data = JsonHandler.toJson("{'Test': true}");
		assertEquals("There should be a test key", "Malformed body", data.get("error").asText());
		
	}
	
	@Test
	public void toJsonFromMapHappyPathTest() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("Test", true);
		String json = JsonHandler.toJson(data);
		System.out.println(json);
		assertEquals("There should be a test key", "{\"Test\":true}", json);
	}
	
	@Test
	public void toMapHappyPathTest() {
		Map<String, Object> data = JsonHandler.toMap("{\"Test\": true}");
		assertEquals("There should be a test key", true, data.get("Test"));
	}

}
