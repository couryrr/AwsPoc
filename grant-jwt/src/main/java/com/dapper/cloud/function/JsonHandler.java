package com.dapper.cloud.function;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static JsonNode toJson(String body) {
		JsonNode n;
		try {
			n = mapper.readTree(body);
		} catch (IOException e) {
			n = mapper.createObjectNode().put("error", "Malformed body");
		}
		return n;
	}

	public static String toJson(Map<String, Object> payload) {
		String n = null;
		try {
			n = mapper.writeValueAsString(payload);
		} catch (IOException e) {
			// n = mapper.createObjectNode().put("error", "Malformed body");
		}
		return n;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String body) {
		JsonNode n = toJson(body);
		Map<String, Object> m = mapper.convertValue(n, Map.class);
		return m;
	}

}
